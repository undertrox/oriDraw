package de.undertrox.oridraw;

import de.undertrox.oridraw.ui.CanvasTab;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
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
            Canvas canvas = ((Canvas) mainTabPane.getSelectionModel().getSelectedItem().getContent());
            initCanvas(canvas);
        };
        mainTabPane.widthProperty().addListener(sizeChangeListener);
        mainTabPane.heightProperty().addListener(sizeChangeListener);
        updateText();
        createNewFileTab();
    }

    private void updateText() {
        logger.debug("Loading Localization");
        btnSave.setText(bundle.getString("oridraw.toolbar.button.save"));
        btnNew.setText(bundle.getString("oridraw.toolbar.button.new"));
        btnOpen.setText(bundle.getString("oridraw.toolbar.button.open"));
    }

    public void createNewFileTab() {
        logger.debug("Creating new File Tab");
        Canvas c = new Canvas();
        Tab tab = new CanvasTab(bundle.getString("oridraw.file.new"), c, mainTabPane);
        tab.setOnCloseRequest(this::onFileTabCloseRequest);
        mainTabPane.getTabs().add(tab);
        initCanvas(c);
    }

    private void initCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
        System.out.println(e.getSource());
        logger.info("Closed Tab '");
    }

    public void btnOpenClick() {
        logger.debug("Open Button clicked");
    }

}
