package de.undertrox.oridraw.ui.component.tab;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.MainApp;
import de.undertrox.oridraw.ui.handler.KeyboardHandler;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.io.export.Exporter;
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


    /**
     * Constructor. Binds the width of the Canvas to that of the tabPane
     *
     * @param title   Title of the tab
     * @param canvas  Canvas to be rendered inside the tab
     * @param tabPane parent tabPane of the Tab
     */
    public CreasePatternTab(String title, Canvas canvas, TabPane tabPane) {
        this(new Document(title, new Vector(Constants.DEFAULT_PAPER_SIZE), Constants.DEFAULT_GRID_DIVISIONS), canvas, tabPane);
        getDoc().getCp().createSquare(Vector.ORIGIN, getDoc().getPaperSize().getX());
    }

    public CreasePatternTab(Document doc, Canvas canvas, TabPane tabPane) {
        super(doc.getTitle(), canvas);

        logger.debug("Initializing Crease Pattern");
        this.doc = doc;
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LocalizationHelper.getString("oridraw.actions.close.alert.desc"),
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
        chooser.setTitle(LocalizationHelper.getString("oridraw.actions.save.filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                LocalizationHelper.getString("oridraw.actions.export_cp.filedialog.description"), "*.cp");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return false;
        }
        IOHelper.saveToFile(file.getAbsolutePath(), getDoc(), Registries.DOCUMENT_EXPORTER_REGISTRY.getItem(Constants.REGISTRY_DOMAIN, "cp"));
        getDoc().setTitle(file.getName());
        getDoc().setHasUnsavedChanges(false);
        return true;
    }

    public boolean exportDocument(Exporter<Document> exporter) {
        FileChooser chooser = new FileChooser();
        String[] extensions = exporter.getFilterExtensions();
        chooser.setTitle(LocalizationHelper.getString(
                Constants.REGISTRY_DOMAIN + ".actions.export_" + exporter.getRegistryKey().getId() + ".filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                LocalizationHelper.getString(
                        Constants.REGISTRY_DOMAIN + ".actions.export_" + exporter.getRegistryKey().getId() + ".filedialog.description"), extensions);
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return false;
        }
        IOHelper.saveToFile(file.getAbsolutePath(), getDoc(), exporter);
        if (exporter.isLossLess()) {
            getDoc().setTitle(file.getName());
            getDoc().setHasUnsavedChanges(false);
        }
        return true;
    }
}
