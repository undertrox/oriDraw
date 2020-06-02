package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.setting.KeybindSetting;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public Button btnTheme;
    public Button btnKeybinds;
    public Button btnCancel;
    public Button btnApply;
    public Button btnOk;
    public Button btnGeneral;
    public AnchorPane paneSettings;

    public TableView<KeybindSetting> tableViewKeybinds;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnGeneral.fire();
        tableViewKeybinds = new TableView<>();
        tableViewKeybinds.getItems().addAll(MainApp.getSettings().getKeybindSettings().getKeybinds());
        tableViewKeybinds.setMaxWidth(Double.MAX_VALUE);
        tableViewKeybinds.setPrefWidth(1000000);
        TableColumn<KeybindSetting, String> colAction = new TableColumn<>("Action");
        colAction.setCellValueFactory(param -> new SimpleStringProperty(LocalizationHelper.getString(param.getValue().localizationKey.get())));
        tableViewKeybinds.getColumns().add(colAction);
        TableColumn<KeybindSetting, String> colBind = new TableColumn<>("Shortcut");
        colBind.setCellValueFactory(new PropertyValueFactory<>("keybind"));
        tableViewKeybinds.getColumns().add(colBind);
        tableViewKeybinds.getColumns().add(new TableColumn<>(""));
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnApplyClick(ActionEvent actionEvent) {
        // TODO: Save Settings
    }

    public void btnOkClick(ActionEvent actionEvent) {
        btnApply.fire();
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void btnGeneralClick(ActionEvent actionEvent) {
        paneSettings.getChildren().clear();
    }

    public void btnThemeClick(ActionEvent actionEvent) {
        paneSettings.getChildren().clear();
    }

    public void btnKeybindClick(ActionEvent actionEvent) {
        paneSettings.getChildren().clear();
        paneSettings.getChildren().add(tableViewKeybinds);
    }
}
