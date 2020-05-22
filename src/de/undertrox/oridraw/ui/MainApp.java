package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.Main;
import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.util.io.export.ExporterCP;
import de.undertrox.oridraw.util.io.load.LoaderCP;
import de.undertrox.oridraw.util.registry.Registries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainApp extends Application {
    private static Logger logger = Logger.getLogger(Main.class);
    private static String title = "OriDraw v" + OriDraw.VERSION;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApp.primaryStage = primaryStage;
        logger.info("Starting " + title);
        logger.debug("Loading Resources");
        Locale locale = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("lang.lang", locale);
        logger.debug("Initializing Window");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("ui/mainWindow.fxml")), bundle);
        primaryStage.setTitle(title);
        Scene rootScene = new Scene(root);
        rootScene.getStylesheets().add("css/main.css");
        populateRegistries();
        primaryStage.setScene(rootScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        logger.debug("OriDraw is now running.");
        Registries.lockAll();
    }

    private void populateRegistries() {
        registerDocumentExporters();
        registerDocumentLoaders();
    }

    private void registerDocumentExporters() {
        String domain = "document_exporter";
        var registry = Registries.documentExporterRegistry;
        registry.register(domain, "cp", new ExporterCP());
    }

    private void registerDocumentLoaders() {
        String domain = "document_loader";
        var registry = Registries.documentLoaderRegistry;
        registry.register(domain, "cp", new LoaderCP());
    }

    @Override
    public void init() throws Exception {
        logger.info("Initializing");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Exiting OriDraw");
    }

    public static void run(String[] args) {
        launch(args);
    }
}
