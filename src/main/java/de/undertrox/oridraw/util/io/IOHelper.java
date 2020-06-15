package de.undertrox.oridraw.util.io;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.export.Exporter;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class IOHelper {

    private static Logger logger = LogManager.getLogger(IOHelper.class);

    // prevent instantiation
    private IOHelper() {}

    public static void saveToFile(String path, Document doc) {
        String extension = getExtension(path);

        for (RegistryEntry<Exporter<Document>> item : Registries.DOCUMENT_EXPORTER_REGISTRY.getEntries()) {
            if (Arrays.asList(item.getValue().extensions()).contains(extension)) {
                logger.debug("Saving Document to '{}'", path);
                saveToFile(path, doc, item.getValue());
            }
        }
    }

    public static Document readFromFile(String path) {
        String extension = getExtension(path);
        for (RegistryEntry<Loader<Document>> item : Registries.DOCUMENT_LOADER_REGISTRY.getEntries()) {
            if (Arrays.asList(item.getValue().getExtensions()).contains(extension)) {
                logger.debug("Reading Document from '{}'", path);
                return readFromFile(path, item.getValue());
            }
        }
        return null;
    }

    public static void saveToFile(String path, Document doc, Exporter<Document> exporter) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            exporter.export(doc, fileWriter);
            fileWriter.close();
            logger.info("Saved Document to '{}'", path);
        } catch (Exception e) {
            logger.error("An error occurred while writing a Document to '{}'", path, e);
        }
    }

    public static Document readFromFile(String path, Loader<Document> loader) {
        try {
            FileReader reader = new FileReader(new File(path));
            Document doc = loader.load(reader);
            if (doc.getTitle().isBlank()) {
                doc.setTitle(getFileName(path));
            }
            reader.close();
            logger.info("read Document from '{}'", path);
            return doc;
        } catch (Exception e) {
            logger.error("An error occurred while reading a Document from '{}'", path, e);
        }
        return null;
    }

    // https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
    public static String getExtension(String fileName) {
        char ch;
        int len;
        if (fileName == null ||
                (len = fileName.length()) == 0 ||
                (ch = fileName.charAt(len - 1)) == '/' || ch == '\\' || //in the case of a directory
                ch == '.') //in the case of . or ..
            return "";
        int dotInd = fileName.lastIndexOf('.');
        int sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (dotInd <= sepInd)
            return "";
        else
            return fileName.substring(dotInd + 1).toLowerCase();
    }

    public static String loadResource(String resourcePath) {
        StringBuilder data = new StringBuilder();
        InputStream resourceStream = IOHelper.class.getClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
            String line;
            while (true) {
                try {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    data.append(line).append("\n");
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
            return data.toString();
        } else {
            return "";
        }
    }

    public static String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

}
