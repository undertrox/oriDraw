package de.undertrox.oridraw.ui.tab;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.MainApp;
import de.undertrox.oridraw.ui.handler.KeyboardHandler;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.util.actions.CareTaker;
import de.undertrox.oridraw.util.actions.Originator;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.BackgroundRenderer;
import de.undertrox.oridraw.ui.render.DocumentRenderer;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is used to display and edit a Crease Pattern inside a tab
 */
public class CreasePatternTab extends CanvasTab {
    private Logger logger = LogManager.getLogger(CreasePatternTab.class);
    private List<CreasePatternTool> tools;
    private CreasePatternTool activeTool;
    private Transform docTransform;
    private Transform bgTransform;
    private Document doc;

    /*
        These three objects will take care of the undo and redo for each document.
       */
    private CareTaker careTaker; // Retrieve and store states that was saved previously.
    private Originator originator; // Sets and gets value from the currently targeted memento. Mementos is the object whose state would be stored.

    private int currMementoStateIndex = 0 ;
    private int noOfSavedStates = 0;


    /**
     * Constructor. Binds the width of the Canvas to that of the tabPane
     *
     * @param title   Title of the tab
     * @param canvas  Canvas to be rendered inside the tab
     * @param tabPane parent tabPane of the Tab
     */
    public CreasePatternTab(String title, Canvas canvas, TabPane tabPane, ResourceBundle bundle) {
        this(new Document(title, Constants.DEFAULT_PAPER_SIZE, Constants.DEFAULT_GRID_DIVISIONS), canvas, tabPane, bundle);
    }

    public CreasePatternTab(Document doc, Canvas canvas, TabPane tabPane, ResourceBundle bundle) {
        super(doc.getTitle(), canvas, bundle);

        this.careTaker = new CareTaker();
        this.originator = new Originator();


        logger.debug("Initializing Crease Pattern");
        this.doc = doc;
        doc.getCp().createSquare(Vector.ORIGIN, Constants.DEFAULT_PAPER_SIZE);
        docTransform = new Transform(new Vector(300, 250), 1, 0);
        bgTransform = new Transform(new Vector(0, 0), 1, 0);

        logger.debug("Initializing Tools");
        setMouseHandler(new MouseHandler(doc, docTransform));
        setKeyboardHandler(new KeyboardHandler(doc));
        tools = new ArrayList<>();
        for (RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> toolFactory :
                Registries.TOOL_FACTORY_REGISTRY.getEntries()) {
            tools.add(toolFactory.getValue().create(this));
        }
        setActiveTool(tools.get(0));

        logger.debug("Initializing Renderers");
        getRenderers().add(new BackgroundRenderer(bgTransform));
        getRenderers().add(new DocumentRenderer(docTransform, doc));
        tools.forEach(tool -> getRenderers().add(tool.getRenderer()));

        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());

        this.setOnCloseRequest(this::onCloseRequest);
    }

    public List<CreasePatternTool> getTools() {
        return tools;
    }

    /**
     * Sets the CreasePatternTool that is currently being used on this Tab
     * @param activeTool: Tool to use
     */
    public void setActiveTool(CreasePatternTool activeTool) {
        if (!tools.contains(activeTool)) {
            throw new IllegalArgumentException("Cant activate a Tool that doesnt belong to this tab");
        }
        tools.forEach(t -> t.setEnabled(false));
        this.activeTool = activeTool;
        activeTool.setEnabled(true);
        ((MouseHandler) getMouseHandler()).setActiveTool(activeTool);
        ((KeyboardHandler) getKeyboardHandler()).setActiveTool(activeTool);
    }

    /**
     * @return the CreasePatternTool that is currently being used on this Tab
     */
    public CreasePatternTool getActiveTool() {
        return activeTool;
    }

    public void setActiveTool(RegistryKey registryKey) {
        for (CreasePatternTool tool : tools) {
            if (tool.getFactory().getRegistryKey().equals(registryKey)) {
                setActiveTool(tool);
                return;
            }
        }
        throw new IllegalArgumentException("Tried to activate a tool that does not exist, registry name: " + registryKey);
    }

    /**
     *
     * @return the Transform that is being applied to the Document
     */
    public Transform getDocTransform() {
        return docTransform;
    }

    /**
     *
     * @return the Document that is being shown in this tab
     */
    public Document getDoc() {
        return doc;
    }

    // Show dialog if the Document has unsaved changes
    @Override
    public void onCloseRequest(Event e) {
        if (getDoc().hasUnsavedChanges() && !e.isConsumed()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("oridraw.action.close.alert.desc"),
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // if the chosen File dialog is closed without selecting a file, the Tab stays open
                boolean fileChosen = saveDocument();
                if (!fileChosen) {
                    e.consume();
                    return;
                }
            } else if (alert.getResult() == ButtonType.CANCEL) {
                e.consume();
                return;
            }
        }
        this.getTabPane().getTabs().remove(this);
    }

    /**
     * Shows a file Dialog to save the current Document and saves the Document to the selected file.
     * @return false if no file is selected, true otherwise
     */
    public boolean saveDocument() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(bundle.getString("oridraw.action.save.filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                bundle.getString("oridraw.action.save.filedialog.description.cp"), "*.cp");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return false;
        }
        IOHelper.saveToFile(file.getAbsolutePath(), getDoc());
        getDoc().setTitle(file.getName());
        getDoc().setHasUnsavedChanges(false);
        return true;
    }


    public Originator getOriginator() {
        return this.originator;
    }

    public CareTaker getCareTaker() {
        return this.careTaker;
    }

    public int getNoOfSavedStates(){
        return this.careTaker.getSavedDocument().size();
    }



    public void setNoOfSavedStates(int index){
        if(this.noOfSavedStates < 0){
            logger.error("[setNoOfSavedStates] : The current memento state index is less than zero.");
            return;
        }

        // TODO : Please check this condition. something might be wrong with this.
        if(this.noOfSavedStates > this.careTaker.getSavedDocument().size()){
            logger.error("[setNoOfSavedStates] : Potential index out of bound write!. Please check the debugger to make sure that the " +
                    "current index should not be larger than the size of the " +
                    "arraylist containing the mementos");
            return;
        }
        this.noOfSavedStates  = index;
    }

    public int getCurrMementoStateIndex(){
        //  This is to get the current state of the memento.
        return this.currMementoStateIndex;
    }

    public void setCurrMementoStateIndex(int index){
        this.currMementoStateIndex = index;
    }


    public void setDocument(Document document){
        // Make sure to deep copy
        getRenderers().clear();
        this.doc = document;

        // Rerender after the new documents have been set to update screen
        getRenderers().add(new BackgroundRenderer(bgTransform));
        getRenderers().add(new DocumentRenderer(docTransform, doc));
        tools.forEach(tool -> getRenderers().add(tool.getRenderer()));
    }


}
