package de.undertrox.oridraw;

import de.undertrox.oridraw.math.Vector;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.render.CreasePatternRenderer;
import de.undertrox.oridraw.ui.CreasePatternTab;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private Logger logger = Logger.getLogger(MainWindowController.class);

    public TextFlow statusText;
    public VBox vBoxLeft;
    public VBox vBoxRight;
    public TabPane mainTabPane;


    public ToolBar toolBar;
    public Button btnSave;
    public Button btnNew;
    public Button btnOpen;


    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initializing MainWindowController");
        bundle = resources;

        ChangeListener<Number> sizeChangeListener = (obs, oldVal, newVal) -> {
            CreasePatternTab selected = getSelectedTab();
            selected.getRenderer().draw(new Vector(0, 0));
        };
        mainTabPane.widthProperty().addListener(sizeChangeListener);
        mainTabPane.heightProperty().addListener(sizeChangeListener);
        updateText();
        createNewFileTab();
    }

    CreasePatternTab getSelectedTab() {
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
    public void createNewFileTab() {
        logger.debug("Creating new File Tab");
        Canvas c = new Canvas();
        CreasePatternTab tab = new CreasePatternTab(bundle.getString("oridraw.file.new"), c, mainTabPane);
        tab.setOnCloseRequest(this::onFileTabCloseRequest);
        mainTabPane.getTabs().add(tab);
        tab.getRenderer().draw(new Vector(0, 0));
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
        CreasePatternRenderer renderer = getSelectedTab().getRenderer();
        renderer.draw(new Vector(e.getX(), e.getY()));
    }

}
