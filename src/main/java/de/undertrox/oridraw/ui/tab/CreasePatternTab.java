package de.undertrox.oridraw.ui.tab;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.MainApp;
import de.undertrox.oridraw.ui.handler.KeyboardHandler;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.BackgroundRenderer;
import de.undertrox.oridraw.ui.render.renderer.DocumentRenderer;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryItem;
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
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends CanvasTab {
    private Logger logger = LogManager.getLogger(CreasePatternTab.class);
    private List<CreasePatternTool> tools;
    private CreasePatternTool activeTool;
    private Transform cpTransform;
    private Transform bgTransform;
    private Document doc;


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
        logger.debug("Initializing Crease Pattern");
        this.doc = doc;
        doc.getCp().createSquare(Vector.ORIGIN, Constants.DEFAULT_PAPER_SIZE);
        cpTransform = new Transform(new Vector(300, 250), 1, 0);
        bgTransform = new Transform(new Vector(0, 0), 1, 0);
        logger.debug("Initializing Renderers");
        logger.debug("Initializing BackgroundRenderer");
        getRenderers().add(new BackgroundRenderer(bgTransform));
        logger.debug("Initializing Document Renderer");
        getRenderers().add(new DocumentRenderer(cpTransform, doc));
        setMouseHandler(new MouseHandler(doc, cpTransform));
        setKeyboardHandler(new KeyboardHandler(doc));

        logger.debug("Initializing Tools");
        tools = new ArrayList<>();
        for (RegistryItem<CreasePatternToolFactory<? extends CreasePatternTool>> toolFactory :
                Registries.TOOL_FACTORY_REGISTRY.getItems()) {
            tools.add(toolFactory.getValue().create(this));
        }

        setActiveTool(tools.get(0));

        logger.debug("Initializing Tool Renderers");
        tools.forEach((tool) -> getRenderers().add(tool.getRenderer()));

        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());

        this.setOnCloseRequest(this::onCloseRequest);
    }

    public List<CreasePatternTool> getTools() {
        return tools;
    }

    public void setActiveTool(CreasePatternTool activeTool) {
        tools.forEach(t -> t.setEnabled(false));
        this.activeTool = activeTool;
        ((MouseHandler) getMouseHandler()).setActiveTool(activeTool);
        ((KeyboardHandler) getKeyboardHandler()).setActiveTool(activeTool);
        activeTool.setEnabled(true);
    }

    public CreasePatternTool getActiveTool() {
        return activeTool;
    }

    public Transform getCpTransform() {
        return cpTransform;
    }

    public Document getDoc() {
        return doc;
    }

    public void onCloseRequest(Event e) {
        if (getDoc().hasUnsavedChanges() && !e.isConsumed()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("oridraw.action.close.alert.desc"),
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                if (!saveDocument()) {
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

    public boolean saveDocument() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle(bundle.getString("oridraw.action.save.filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                bundle.getString("oridraw.action.save.filedialog.description.cp"), "*.cp");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(MainApp.primaryStage);
        if (file == null) {
            return false;
        }
        IOHelper.saveToFile(file.getAbsolutePath(), getDoc());
        getDoc().setTitle(file.getName());
        getDoc().setHasUnsavedChanges(false);
        return true;
    }
}
