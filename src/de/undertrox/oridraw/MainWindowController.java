package de.undertrox.oridraw;

import de.undertrox.oridraw.ui.CanvasTab;
import de.undertrox.oridraw.ui.CreasePatternTab;
import de.undertrox.oridraw.ui.MouseHandler;
import de.undertrox.oridraw.util.math.Vector;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public Label statusLabel;
    public RadioButton radioMountain;
    public RadioButton radioValley;
    public RadioButton radioEdge;
    public RadioButton radioAux;
    public GridPane creasetypeGridpane;
    private Logger logger = Logger.getLogger(MainWindowController.class);

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
        createNewFileTab();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                getSelectedTab().render();
            }
        };
        timer.start();
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
     * Creates a new Tab for editing a new OriLine Pattern
     */
    public void createNewFileTab() {
        logger.debug("Creating new File Tab");
        Canvas c = new Canvas();
        CreasePatternTab tab = new CreasePatternTab(bundle.getString("oridraw.file.new"), c, mainTabPane);
        tab.setOnCloseRequest(this::onFileTabCloseRequest);
        mainTabPane.getTabs().add(tab);
        tab.render();
    }

    public void btnSaveClick() {
        logger.debug("Save Button clicked");
    }

    public void btnNewClick() {
        logger.debug("New Button clicked");
        createNewFileTab();
        mainTabPane.getSelectionModel().selectLast();

    }

    public void onFileTabCloseRequest(Event e) {
        logger.info("Closed Tab '");
    }

    public void btnOpenClick() {
        logger.debug("Open Button clicked");
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

}
