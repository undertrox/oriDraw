package de.undertrox.oridraw;

import de.undertrox.oridraw.io.IOHelper;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private Logger logger = Logger.getLogger(MainWindowController.class);

    public ToolBar toolBar;
    public TextFlow statusText;
    public VBox vBoxLeft;
    public VBox vBoxRight;
    public TabPane mainTabPane;
    public Button btnSave;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initializing MainWindowController");
        bundle = resources;
        btnSave.setText(bundle.getString("oridraw.toolbar.button.save"));
    }


    public void btnSaveClick() {
        logger.debug("Save Button clicked");
    }

}
