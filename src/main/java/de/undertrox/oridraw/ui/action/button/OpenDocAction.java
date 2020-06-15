package de.undertrox.oridraw.ui.action.button;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.MainApp;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;
import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.io.IOHelper;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenDocAction extends Action {
    @Override
    public void action() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(LocalizationHelper.getString("oridraw.action.open.filedialog.title"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                LocalizationHelper.getString("oridraw.action.save.filedialog.description.cp"), "*.cp");
        List<String> allSupportedExtensions = new ArrayList<>();
        for (RegistryEntry<Loader<Document>> entry : Registries.DOCUMENT_LOADER_REGISTRY.getEntries()) {
            allSupportedExtensions.addAll(Arrays.asList(entry.getValue().extensions()));
        }
        FileChooser.ExtensionFilter all = new FileChooser.ExtensionFilter(LocalizationHelper.getString("oridraw.action.save.filedialog.description.allsupported"),
                allSupportedExtensions.toArray(new String[0]));
        chooser.getExtensionFilters().add(all);
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(MainApp.getPrimaryStage());
        if (file == null) {
            return;
        }
        Document doc = IOHelper.readFromFile(file.getAbsolutePath());
        if (doc == null) {
            Alert info = new Alert(Alert.AlertType.ERROR, LocalizationHelper.getString("oridraw.action.open.error"));
            info.showAndWait();
            return;
        }
        doc.setTitle(file.getName());
        MainWindowController.instance.createNewFileTab(doc);
        MainWindowController.instance.mainTabPane.getSelectionModel().selectLast();
    }
}
