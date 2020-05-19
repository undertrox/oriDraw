package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.Main;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.AngleBisectorTool;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.origami.tool.TypedCreasePatternTool;
import de.undertrox.oridraw.ui.button.ToolButton;
import de.undertrox.oridraw.ui.tab.CanvasTab;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.math.Vector;
import javafx.animation.AnimationTimer;
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
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Logger logger = Logger.getLogger(MainWindowController.class);

    public Label statusLabel;
    public GridPane creasetypeGridpane;
    public ToggleButton btnMountain;
    public ToggleButton btnValley;
    public ToggleButton btnEdge;
    public ToggleButton btnAux;

    public ToolButton btnPointToPoint;
    public ToolButton btnAngleBisector;

    public TextFlow statusText;
    public VBox vBoxLeft;
    public VBox vBoxRight;
    public TabPane mainTabPane;

    public ToolBar toolBar;
    public Button btnSave;
    public Button btnNew;
    public Button btnOpen;

    private static final int CANVAS_CORRECTION = 28;


    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initializing MainWindowController");
        bundle = resources;

        ChangeListener<Number> sizeChangeListener = (obs, oldVal, newVal) -> {
            getSelectedTab().render();
        };
        mainTabPane.widthProperty().addListener(sizeChangeListener);
        mainTabPane.heightProperty().addListener(sizeChangeListener);
        updateText();
        createNewFileTab(null);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                CanvasTab tab = getSelectedTab();
                if (tab == null) {
                    Main.primaryStage.close();
                    return;
                }
                tab.render();
                if (tab instanceof CreasePatternTab) {
                    CreasePatternTab cpTab = (CreasePatternTab) tab;
                    cpTab.setText(cpTab.getDoc().getTitle());
                }
                updateCreaseType();
                updateActiveTool();
            }
        };


        btnPointToPoint.setToolSupplier(() -> {
            CanvasTab tab = getSelectedTab();
            if (tab instanceof CreasePatternTab) {
                return ((CreasePatternTab) tab).getPointToPointTool();
            }
            return null;
        });
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("ui/icon/pointtopoint/lightmode/enabled_mountain.png"));
        ImageView view = new ImageView(image);
        view.setFitHeight(32);
        view.setPreserveRatio(true);
        btnPointToPoint.setGraphic(view);
        btnPointToPoint.setActive(true);


        btnAngleBisector.setToolSupplier(() -> {
            CanvasTab tab = getSelectedTab();
            if (tab instanceof CreasePatternTab) {
                return ((CreasePatternTab) tab).getAngleBisectorTool();
            }
            return null;
        });
        image = new Image(getClass().getClassLoader().getResourceAsStream("ui/icon/anglebisect/lightmode/enabled_mountain.png"));
        view = new ImageView(image);
        view.setFitHeight(32);
        view.setPreserveRatio(true);
        btnAngleBisector.setGraphic(view);

        timer.start();
        mainTabPane.requestFocus();

        Main.primaryStage.setOnCloseRequest(this::onCloseRequest);
    }

    private void updateActiveTool() {
        CreasePatternTab tab;
        if (getSelectedTab() instanceof CreasePatternTab) {
            tab = (CreasePatternTab) getSelectedTab();
            CreasePatternTool activeTool = tab.getActiveTool();
            if (activeTool instanceof DrawLineTool) {
                btnPointToPoint.setSelected(true);
            } else if (activeTool instanceof AngleBisectorTool) {
                btnAngleBisector.setSelected(true);
            }
        }
    }

    CanvasTab getSelectedTab() {
        Tab selected = mainTabPane.getSelectionModel().getSelectedItem();
        if (selected instanceof CreasePatternTab) {
            return (CreasePatternTab) selected;
        }
        logger.error("Selected Tab is not a CreasePatternTab");
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
        File file = chooser.showOpenDialog(Main.primaryStage);
        if (file == null) {
            return;
        }
        Document doc = IOHelper.readFromFile(file.getAbsolutePath());
        if (doc == null) {
            Alert info = new Alert(Alert.AlertType.ERROR, bundle.getString("oridraw.action.open.error"));
            info.showAndWait();
            return;
        }
        createNewFileTab(doc);
        mainTabPane.getSelectionModel().selectLast();
    }

    public void onMouseMoved(MouseEvent e) {
        CanvasTab tab = getSelectedTab();
        if (tab instanceof CreasePatternTab) {
            CreasePatternTab cpTab = (CreasePatternTab) tab;
            statusLabel.setText("Mouse Position: " + MouseHandler
                .normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTab.getCpTransform()));
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

    public void setTypeAux(ActionEvent actionEvent) {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.AUX);
        }
    }

    public void setTypeEdge(ActionEvent actionEvent) {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.EDGE);
        }
    }

    public void setTypeValley(ActionEvent actionEvent) {
        TypedCreasePatternTool tool = getActiveTypedCpTool();
        if (tool != null) {
            tool.setType(OriLine.Type.VALLEY);
        }
    }

    public void setTypeMountain(ActionEvent actionEvent) {
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
                TypedCreasePatternTool ttool = (TypedCreasePatternTool) tool;
                return ttool;
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
        var type = tool.getType();
        switch (type) {
            case MOUNTAIN:
                setBorderColor(btnMountain, RenderSettings.getColorManager().MOUNTAIN_COLOR);
                btnMountain.setSelected(true);
                break;
            case VALLEY:
                setBorderColor(btnValley, RenderSettings.getColorManager().VALLEY_COLOR);
                btnValley.setSelected(true);
                break;
            case EDGE:
                setBorderColor(btnEdge, RenderSettings.getColorManager().EDGE_COLOR);
                btnEdge.setSelected(true);
                break;
            case AUX:
                setBorderColor(btnAux, RenderSettings.getColorManager().AUX_COLOR);
                btnAux.setSelected(true);
                break;
        }
    }

    public void setBorderColor(Region region, Paint color) {
        region.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2))));
    }
}
