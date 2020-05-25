package de.undertrox.oridraw.util.io;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.export.Exporter;
import de.undertrox.oridraw.util.io.load.Loader;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class IOHelper {

    private static Logger logger = LogManager.getLogger(IOHelper.class);

    // prevent instantiation
    private IOHelper() {}

    public static void saveToFile(String filename, Document doc) {
        String extension = getExtension(filename);

        for (RegistryEntry<Exporter<Document>> item : Registries.DOCUMENT_EXPORTER_REGISTRY.getEntries()) {
            if (Arrays.asList(item.getValue().extensions()).contains(extension)) {
                logger.debug("Saving Document to '{}'", filename);
                saveToFile(filename, doc, item.getValue());
            }
        }
    }

    public static Document readFromFile(String filename) {
        String extension = getExtension(filename);

        for (RegistryEntry<Loader<Document>> item : Registries.DOCUMENT_LOADER_REGISTRY.getEntries()) {
            if (Arrays.asList(item.getValue().extensions()).contains(extension)) {
                logger.debug("Reading Document from '{}'", filename);
                return readFromFile(filename, item.getValue());
            }
        }
        return null;
    }

    public static void saveToFile(String fileName, Document doc, Exporter<Document> exporter) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            exporter.export(doc, fileWriter);
            fileWriter.close();
            logger.info("Saved Document to '{}'", fileName);
        } catch (Exception e) {
            logger.error("An error occurred while writing a Document to '{}'", fileName, e);
        }
    }

    public static Document readFromFile(String fileName, Loader<Document> loader) {
        try {
            FileReader reader = new FileReader(new File(fileName));
            Document doc = loader.load(reader);
            reader.close();
            logger.info("read Document from '{}'", fileName);
            return doc;
        } catch (Exception e) {
            logger.error("An error occurred while reading a Document from '{}'", fileName, e);
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

}
