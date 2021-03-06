package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.TypedCreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.component.NumberTextField;
import de.undertrox.oridraw.ui.component.ToolButton;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.component.tab.CanvasTab;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import de.undertrox.oridraw.util.registry.RegistryKey;
import de.undertrox.oridraw.util.setting.Settings;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static de.undertrox.oridraw.util.io.IOHelper.loadResource;

public class MainWindowController implements Initializable {

    private static final int CANVAS_CORRECTION = 28;
    public static MainWindowController instance;
    public GridPane toolGridPane;
    public ToggleGroup type;
    public NumberTextField gridSize;
    public CheckBox showGrid;
    public WebView documentation;
    public GridPane toolSettingsGridPane;
    public Label toolNameLabel;
    public Label statusLabel;
    public GridPane creasetypeGridpane;
    public ToggleButton btnMountain;
    public ToggleButton btnValley;
    public ToggleButton btnEdge;
    public ToggleButton btnAux;
    public TextFlow statusText;
    public VBox vBoxLeft;
    public VBox vBoxRight;
    public TabPane mainTabPane;
    public ToggleGroup toolToggleGroup;
    public ToolBar toolBar;
    public Button btnSave;
    public Button btnNew;
    public Button btnOpen;
    public Button btnSettings;
    public MenuBar menuBar;
    public Menu exportMenu;
    public Menu importMenu;
    public Menu toolMenu;
    public Menu editMenu;
    public Menu fileMenu;
    public VBox mainbox;
    public AnchorPane mainAnchorPane;
    public Pane mainPane;
    public AnchorPane editorAnchorPane;
    private Logger logger = LogManager.getLogger(MainWindowController.class);
    private List<ToolButton> toolButtons;

    private Settings settings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        settings = MainApp.getSettings();
        logger.debug("Initializing MainWindowController");
        toolButtons = new ArrayList<>();
        toolToggleGroup = new ToggleGroup();
        updateText();
        createNewFileTab(null);
        mainbox.setFillWidth(true);
        mainAnchorPane.prefWidthProperty().bind(mainPane.widthProperty());
        editorAnchorPane.prefHeightProperty().bind(mainPane.heightProperty());
        gridSize.textProperty().addListener(e -> {
            if (!gridSize.isFocused()) {
                setGridSize();
            }
        } );

        // Make sure the right stylesheet is loaded
        WebEngine webEngine = documentation.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                org.w3c.dom.Document doc = webEngine.getDocument();
                Element styleNode = doc.createElement("style");
                // TODO: use theme manager for this
                Text styleContent = doc.createTextNode(loadResource("ui/theme/default/tips.css"));
                styleNode.appendChild(styleContent);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);
            }
        });

        // This timer will render the current Tab every tick
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                CanvasTab tab = getSelectedTab();
                if (tab == null) {
                    MainApp.getPrimaryStage().close();
                    Platform.exit();
                    return;
                }
                updateCreaseType();
                tab.render();
            }
        };
        createToolButtons();
        // Loads the properties of the tab (Name, Grid size, active tool) into the UI
        updateTab();

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> updateTab());

        timer.start();
        mainTabPane.requestFocus();
        toolButtons.get(0).fire();
        MainApp.getPrimaryStage().setOnCloseRequest(this::onCloseRequest);
    }

    private void createToolButtons() {
        int col = 0;
        int row = 0;
        int maxCol = 3;
        for (RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> item :
                Registries.TOOL_FACTORY_REGISTRY.getEntries()) {
            ToolButton btn = new ToolButton(item.getKey(), this);
            toolGridPane.add(btn, col, row);
            toolButtons.add(btn);
            col++;
            if (col >= maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public ToolButton getToolButtonForRegistryKey(RegistryKey key) {
        for (ToolButton toolButton : toolButtons) {
            if (toolButton.getToolKey().equals(key)){
                return toolButton;
            }
        }
        return null;
    }

    private void updateGridControls() {
        CreasePatternTab tab = getSelectedCpTab();
        if (tab != null && !gridSize.isFocused()) {
            gridSize.setText(String.valueOf(tab.getDoc().getGrid().getDivisions()));
            showGrid.setSelected(tab.getDoc().showGrid());
        }
    }

    private void updateTab() {
        CanvasTab tab = getSelectedTab();
        if (tab instanceof CreasePatternTab) {
            CreasePatternTab cpTab = (CreasePatternTab) tab;
            cpTab.setText(cpTab.getDoc().getTitle());
        }
        updateActiveTool();
        updateGridControls();
    }

    private void updateActiveTool() {
        if (getSelectedCpTab() != null) {
            for (ToolButton btn : toolButtons) {
                if (btn.isSelected()) {
                    btn.fire();
                }
            }
        }
    }

    CanvasTab getSelectedTab() {
        Tab selected = mainTabPane.getSelectionModel().getSelectedItem();
        if (selected instanceof CreasePatternTab) {
            return (CreasePatternTab) selected;
        }
        if (selected != null) {
            logger.error("Selected Tab is not a CreasePatternTab");
        }
        return null;
    }

    /**
     * reloads all localization
     */
    private void updateText() {
        logger.debug("Loading Localization");
        btnSave.setText(LocalizationHelper.getString("oridraw.toolbar.button.save"));
        btnNew.setText(LocalizationHelper.getString("oridraw.toolbar.button.new"));
        btnOpen.setText(LocalizationHelper.getString("oridraw.toolbar.button.open"));
    }

    /**
     * Creates a new Tab for editing a new Crease Pattern
     */
    public void createNewFileTab(Document doc) {
        logger.debug("Creating new File Tab");
        Canvas c = new Canvas();
        CreasePatternTab tab;
        if (doc == null) {
            tab = new CreasePatternTab(LocalizationHelper.getString("oridraw.file.new"), c, mainTabPane);
        } else {
            tab = new CreasePatternTab(doc, c, mainTabPane);
        }
        mainTabPane.getTabs().add(tab);
    }

    public void btnSaveClick() {
        logger.debug("Save Button clicked");
        Registries.ACTION_REGISTRY.getItem("save_document").action();
    }

    public void btnNewClick() {
        logger.debug("New Button clicked");
        Registries.ACTION_REGISTRY.getItem("new_document").action();

    }

    public void onCloseRequest(Event e) {
        while (getSelectedTab() != null && !e.isConsumed()) {
            getSelectedTab().onCloseRequest(e);
            logger.info("Closed Tab ");
        }
        if (!e.isConsumed()) {
            System.exit(0);
        }
    }

    public void btnOpenClick() {
        logger.debug("Open Button clicked");
        Registries.ACTION_REGISTRY.getItem("open_document").action();
    }

    public void openFile(Loader<Document> loader) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(LocalizationHelper.getString(Constants.REGISTRY_DOMAIN + ".actions.import_" + loader.getRegistryKey().getId() + ".filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                LocalizationHelper.getString(Constants.REGISTRY_DOMAIN + ".actions.import_" + loader.getRegistryKey().getId() + ".filedialog.description"),
                loader.getFilterExtensions());
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return;
        }
        Document doc = IOHelper.readFromFile(file.getAbsolutePath(), loader);
        if (doc == null) {
            Alert info = new Alert(Alert.AlertType.ERROR, LocalizationHelper.getString("oridraw.action.open.error"));
            info.showAndWait();
            return;
        }
        createNewFileTab(doc);
        mainTabPane.getSelectionModel().selectLast();
    }

    public void onMouseMoved(MouseEvent e) {
        if (getSelectedCpTab() != null) {
            CreasePatternTab cpTab = getSelectedCpTab();
            statusLabel.setText("Mouse Position: " + MouseHandler
                    .normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTab.getDocTransform()));
        }
        if (getSelectedTab() != null) {
            getSelectedTab().getMouseHandler().onMove(e);
        }
    }

    public void onMouseClicked(MouseEvent e) {
        if (e.getY() > CANVAS_CORRECTION && getSelectedCpTab() != null) {
            getSelectedTab().getMouseHandler().onClick(e);

        }
    }

    public void onScroll(ScrollEvent e) {
        if (getSelectedTab() != null) {
            getSelectedTab().getMouseHandler().onScroll(e);
        }
    }

    public void onMouseDragged(MouseEvent e) {
        if (getSelectedTab() != null) {
            getSelectedTab().getMouseHandler().onDrag(e);
        }
    }

    public void onKeyPressed(KeyEvent e) {
        if (getSelectedTab() != null) {
            getSelectedTab().getKeyboardHandler().onKeyPressed(e);
        }
    }

    public void onKeyDown(KeyEvent e) {
        if (getSelectedTab() != null) {
            getSelectedTab().getKeyboardHandler().onKeyDown(e);
        }
    }

    public void onKeyUp(KeyEvent e) {
        if (getSelectedTab() != null) {
            getSelectedTab().getKeyboardHandler().onKeyUp(e);
        }
    }

    public void setTypeAux() {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.AUX);
        }
    }

    public void setTypeEdge() {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.EDGE);
        }
    }

    public void setTypeValley() {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.VALLEY);
        }
    }

    public void setTypeMountain() {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.MOUNTAIN);
        }
    }

    public TypedCreasePatternTool getActiveTypedCpTool() {
        CreasePatternTab tab = getSelectedCpTab();
        if (tab != null) {
            CreasePatternTool tool = tab.getActiveTool();
            if (tool instanceof TypedCreasePatternTool) {
                return (TypedCreasePatternTool) tool;
            }
        }
        return null;
    }

    public void updateCreaseType() {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        btnMountain.setBorder(Border.EMPTY);
        btnEdge.setBorder(Border.EMPTY);
        btnValley.setBorder(Border.EMPTY);
        btnAux.setBorder(Border.EMPTY);
        if (tool == null) {
            btnMountain.setDisable(true);
            btnValley.setDisable(true);
            btnEdge.setDisable(true);
            btnAux.setDisable(true);
            return;
        }
        btnMountain.setDisable(false);
        btnValley.setDisable(false);
        btnEdge.setDisable(false);
        btnAux.setDisable(false);
        OriLine.Type toolType = tool.getType();
        switch (toolType) {
            case MOUNTAIN:
                setBorderColor(btnMountain,
                        RenderSettings.getColorManager().getLineStyleForCreaseType(OriLine.Type.MOUNTAIN).getPaint());
                btnMountain.setSelected(true);
                break;
            case VALLEY:
                setBorderColor(btnValley,
                        RenderSettings.getColorManager().getLineStyleForCreaseType(OriLine.Type.VALLEY).getPaint());
                btnValley.setSelected(true);
                break;
            case EDGE:
                setBorderColor(btnEdge,
                        RenderSettings.getColorManager().getLineStyleForCreaseType(OriLine.Type.EDGE).getPaint());
                btnEdge.setSelected(true);
                break;
            case AUX:
                setBorderColor(btnAux,
                        RenderSettings.getColorManager().getLineStyleForCreaseType(OriLine.Type.AUX).getPaint());
                btnAux.setSelected(true);
                break;
            default:
                break;
        }
    }

    public void setBorderColor(Region region, Paint color) {
        region.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2))));
    }

    public void onMouseUp(MouseEvent event) {

        if (getSelectedTab() != null) {
            getSelectedTab().getMouseHandler().onMouseUp(event);
        }
    }

    public void onMouseDown(MouseEvent event) {
        if (getSelectedTab() != null) {
            getSelectedTab().getMouseHandler().onMouseDown(event);
        }
    }

    public void setGridSize() {
        if (gridSize.getText().isBlank() || Integer.parseInt(gridSize.getText()) < 1) {
            gridSize.setText("1");
        }
        int grid = Integer.parseInt(gridSize.getText());
        CreasePatternTab tab = getSelectedCpTab();
        if (tab != null) {
            tab.getDoc().getGrid().setDivisions(grid);
        }
    }

    public CreasePatternTab getSelectedCpTab() {
        CanvasTab tab = getSelectedTab();
        if (tab instanceof CreasePatternTab) {
            return (CreasePatternTab) tab;
        }
        return null;
    }

    public void updateShowGrid() {
        CreasePatternTab tab = getSelectedCpTab();
        if (tab != null) {
            tab.getDoc().setShowGrid(showGrid.isSelected());
        }
    }

    public void openSettings() {
        logger.debug("Opening Settings");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/settings/settings.fxml"));
        try {
            Parent settingsFxml = loader.load();
            Scene scene = new Scene(settingsFxml);
            Stage s = new Stage();
            s.setScene(scene);
            s.setTitle(LocalizationHelper.getString("oridraw.settings.window_title"));
            s.initModality(Modality.APPLICATION_MODAL);
            s.setMinHeight(400);
            s.setMinWidth(600);
            s.show();
        } catch (IOException e) {
            logger.error(e);

        }
    }
}
