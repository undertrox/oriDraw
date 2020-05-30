package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.anglebisector.AngleBisectorToolFactory;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.drawline.DrawLineToolFactory;
import de.undertrox.oridraw.util.io.export.Exporter;
import de.undertrox.oridraw.util.io.export.ExporterCP;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.io.load.LoaderCP;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.Registry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static de.undertrox.oridraw.Constants.REGISTRY_DOMAIN;

public class MainApp extends Application {
    private static Logger logger = LogManager.getLogger(MainApp.class);
    private static String title = "OriDraw v" + OriDraw.VERSION;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;
        logger.debug("Loading Resources");
        Locale locale = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("lang.lang", locale);
        logger.debug("Initializing Window");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("ui/mainWindow.fxml")), bundle);
        primaryStage.setTitle(title);
        Scene rootScene = new Scene(root);
        rootScene.getStylesheets().add("css/main.css");
        primaryStage.setScene(rootScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        logger.debug("OriDraw is now running.");
        Registries.lockAll();
    }

    /**
     * registers all Elements
     */
    private void populateRegistries() {
        registerDocumentExporters();
        registerDocumentLoaders();
        registerTools();
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
    }

    @Override
    public void init() {
        logger.info("Starting {}", title);
        logger.info("Initializing");
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
