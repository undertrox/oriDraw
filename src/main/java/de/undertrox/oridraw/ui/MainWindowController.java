package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.TypedCreasePatternTool;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.button.ToolButton;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.tab.CanvasTab;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public static MainWindowController instance;

    public GridPane toolGridPane;
    public ToggleGroup type;
    public NumberTextField gridSize;
    public CheckBox showGrid;
    public WebView documentation;
    public GridPane toolSettingsGridPane;
    public Label toolNameLabel;
    private Logger logger = LogManager.getLogger(MainWindowController.class);

    private List<ToolButton> toolButtons;

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

    private static final int CANVAS_CORRECTION = 28;


    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        logger.debug("Initializing MainWindowController");
        bundle = resources;
        toolButtons = new ArrayList<>();
        toolToggleGroup = new ToggleGroup();
        ChangeListener<Number> sizeChangeListener = (obs, oldVal, newVal) -> getSelectedTab().render();
        mainTabPane.widthProperty().addListener(sizeChangeListener);
        mainTabPane.heightProperty().addListener(sizeChangeListener);
        updateText();
        createNewFileTab(null);

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
        toolButtons.get(0).getOnAction().handle(new ActionEvent());
        MainApp.getPrimaryStage().setOnCloseRequest(this::onCloseRequest);
    }

    private void createToolButtons() {
        int col = 0;
        int row = 0;
        int maxCol = 4;
        for (RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> item :
                Registries.TOOL_FACTORY_REGISTRY.getEntries()) {
            ToolButton btn = new ToolButton(item.getKey(), this, bundle);
            toolGridPane.add(btn, col, row);
            toolButtons.add(btn);
            col++;
            if (col >= maxCol) {
                col = 0;
                row++;
            }
        }
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
        CreasePatternTab tab;
        if (getSelectedTab() instanceof CreasePatternTab) {
            tab = (CreasePatternTab) getSelectedTab();
            CreasePatternTool activeTool = tab.getActiveTool();
            RegistryKey key = activeTool.getFactory().getRegistryKey();
            for (ToolButton toolButton : toolButtons) {
                if (toolButton.getToolKey().equals(key)) {
                    toolButton.setSelected(true);
                    break;
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
        btnSave.setText(bundle.getString("oridraw.toolbar.button.save"));
        btnNew.setText(bundle.getString("oridraw.toolbar.button.new"));
        btnOpen.setText(bundle.getString("oridraw.toolbar.button.open"));
    }

    /**
     * Creates a new Tab for editing a new Crease Pattern
     */
    public void createNewFileTab(Document doc) {
        logger.debug("Creating new File Tab");
        Canvas c = new Canvas();
        CreasePatternTab tab;
        if (doc == null) {
            tab = new CreasePatternTab(bundle.getString("oridraw.file.new"), c, mainTabPane, bundle);
        } else {
            tab = new CreasePatternTab(doc, c, mainTabPane, bundle);
        }
        mainTabPane.getTabs().add(tab);
        tab.render();
    }

    public void btnSaveClick() {
        logger.debug("Save Button clicked");
        CreasePatternTab tab;
        if (getSelectedTab() instanceof CreasePatternTab) {
            tab = (CreasePatternTab) getSelectedTab();
            tab.saveDocument();
        }
    }

    public void btnNewClick() {
        logger.debug("New Button clicked");
        createNewFileTab(null);
        mainTabPane.getSelectionModel().selectLast();

    }

    public void onCloseRequest(Event e) {
        while (getSelectedTab() != null && !e.isConsumed()) {
            getSelectedTab().onCloseRequest(e);
            logger.info("Closed Tab ");
        }
    }

    public void btnOpenClick() {
        logger.debug("Open Button clicked");
        FileChooser chooser = new FileChooser();
        chooser.setTitle(bundle.getString("oridraw.action.open.filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                bundle.getString("oridraw.action.save.filedialog.description.cp"), "*.cp");

        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return;
        }
        Document doc = IOHelper.readFromFile(file.getAbsolutePath());
        if (doc == null) {
            Alert info = new Alert(Alert.AlertType.ERROR, bundle.getString("oridraw.action.open.error"));
            info.showAndWait();
            return;
        }
        doc.setTitle(file.getName());
        createNewFileTab(doc);
        mainTabPane.getSelectionModel().selectLast();
    }

    public void onMouseMoved(MouseEvent e) {
        CanvasTab tab = getSelectedTab();
        if (tab instanceof CreasePatternTab) {
            CreasePatternTab cpTab = (CreasePatternTab) tab;
            statusLabel.setText("Mouse Position: " + MouseHandler
                .normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTab.getDocTransform()));
        }
        tab.getMouseHandler().onMove(e);
    }

    public void onMouseClicked(MouseEvent e) {
        if (e.getY() > CANVAS_CORRECTION) {
            getSelectedTab().getMouseHandler().onClick(e);
        }
    }

    public void onScroll(ScrollEvent e) {
        getSelectedTab().getMouseHandler().onScroll(e);
    }

    public void onMouseDragged(MouseEvent e) {
        getSelectedTab().getMouseHandler().onDrag(e);
    }

    public void onKeyPressed(KeyEvent e) {
        getSelectedTab().getKeyboardHandler().onKeyPressed(e);
    }

    public void onKeyDown(KeyEvent e) {
        getSelectedTab().getKeyboardHandler().onKeyDown(e);
    }

    public void onKeyUp(KeyEvent e) {
        getSelectedTab().getKeyboardHandler().onKeyUp(e);
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
        CanvasTab tab = getSelectedTab();
        if (tab instanceof CreasePatternTab) {
            CreasePatternTab cptab = (CreasePatternTab) tab;
            CreasePatternTool tool = cptab.getActiveTool();
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
        getSelectedTab().getMouseHandler().onMouseUp(event);
    }

    public void onMouseDown(MouseEvent event) {
        getSelectedTab().getMouseHandler().onMouseDown(event);
    }

    public void setGridSize() {
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
}
