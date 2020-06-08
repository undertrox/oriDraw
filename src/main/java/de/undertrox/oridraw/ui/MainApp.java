package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.anglebisector.AngleBisectorToolFactory;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.drawline.DrawLineToolFactory;
import de.undertrox.oridraw.origami.tool.select.box.BoxSelectionToolFactory;
import de.undertrox.oridraw.ui.action.DeleteSelectedLinesAction;
import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.io.export.Exporter;
import de.undertrox.oridraw.util.io.export.ExporterCP;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.io.load.LoaderCP;
import de.undertrox.oridraw.util.registry.ActionRegistry;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.Registry;
import de.undertrox.oridraw.util.registry.RegistryKey;
import de.undertrox.oridraw.util.setting.KeybindSetting;
import de.undertrox.oridraw.util.setting.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static de.undertrox.oridraw.Constants.REGISTRY_DOMAIN;

public class MainApp extends Application {
    private static Logger logger = LogManager.getLogger(MainApp.class);
    private static String title = "OriDraw v" + OriDraw.VERSION;
    private static Stage primaryStage;

    public static Settings getSettings() {
        return settings;
    }

    private static Settings settings;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;
        logger.debug("Loading Resources");
        Locale locale = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("lang.lang", locale);
        LocalizationHelper.setBundle(bundle);
        logger.debug("Initializing Window");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("ui/mainWindow.fxml")), bundle);
        primaryStage.setTitle(title);
        Scene rootScene = new Scene(root);
        rootScene.getStylesheets().add("css/main.css");
        settings.getKeybindSettings().apply(rootScene);
        primaryStage.setScene(rootScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        registerActions();
        logger.debug("OriDraw is now running.");
        Registries.lockAll();
    }

    private void registerActions() {
        ActionRegistry actionRegistry = Registries.ACTION_REGISTRY;

        actionRegistry.register(REGISTRY_DOMAIN, "delete_selected_lines", new DeleteSelectedLinesAction(), MainWindowController.instance.editMenu);
    }

    /**
     * registers all Elements
     */
    private void populateRegistries() {
        registerDocumentExporters();
        registerDocumentLoaders();
        registerTools();
        registerKeybinds();
    }

    /**
     * registers Document exporters
     */
    private void registerDocumentExporters() {
        Registry<Exporter<Document>> registry = Registries.DOCUMENT_EXPORTER_REGISTRY;
        registry.register(REGISTRY_DOMAIN, "cp", new ExporterCP());
    }

    /**
     * registers Document loaders
     */
    private void registerDocumentLoaders() {
        Registry<Loader<Document>> registry = Registries.DOCUMENT_LOADER_REGISTRY;
        registry.register(REGISTRY_DOMAIN, "cp", new LoaderCP());
    }

    /**
     * registers Crease Pattern Tools
     */
    private void registerTools() {
        Registry<CreasePatternToolFactory<? extends CreasePatternTool>> registry = Registries.TOOL_FACTORY_REGISTRY;
        registry.register(REGISTRY_DOMAIN, "point_to_point", new DrawLineToolFactory());
        registry.register(REGISTRY_DOMAIN, "angle_bisect", new AngleBisectorToolFactory());
        registry.register(REGISTRY_DOMAIN, "box_select", new BoxSelectionToolFactory());

    }

    private void registerKeybinds() {
        Preferences prefs = settings.getKeybindNode();

    }

    private void registerKeybind(String id, Preferences node, KeyCombination defaultCombination) {
        Registry<KeybindSetting> registry = Registries.KEYBIND_REGISTRY;
        RegistryKey key = new RegistryKey(REGISTRY_DOMAIN, id);
        registry.register(key, KeybindSetting.fromPref(node, key, Registries.ACTION_REGISTRY.getItem(key), defaultCombination));
    }

    @Override
    public void init() {
        logger.info("Starting {}", title);
        logger.info("Initializing");
        settings = new Settings();
        settings.init();
        populateRegistries();
    }

    @Override
    public void stop() {
        logger.info("Exiting OriDraw");
    }

    public static void run(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
