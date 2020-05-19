package de.undertrox.oridraw.util.io;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.util.io.export.ExporterCP;
import de.undertrox.oridraw.util.io.load.LoaderCP;
import org.apache.log4j.Logger;

import java.io.*;

public class IOHelper {
    private static Logger logger = Logger.getLogger(IOHelper.class);

    public static void saveToFile(String filename, Document doc) {
        if (filename.toLowerCase().endsWith(".cp")) {
            saveToFile(filename, doc, FileType.CP);
        }
    }

    public static Document readFromFile(String filename) {
        if (filename.toLowerCase().endsWith(".cp")) {
            return readFromFile(filename, FileType.CP);
        }
        return null;
    }

    public static Document readFromFile(String filename, FileType type) {
        try {
            FileReader reader = new FileReader(new File(filename));

            switch (type) {
                case CP:
                    Document doc = new LoaderCP().load(reader);
                    doc.setTitle(filename);
                    return doc;
            }
        } catch (Exception e) {
            logger.error("An error occurred while reading a Document from " + filename, e);
        }
        return null;
    }

    public static void saveToFile(String fileName, Document doc, FileType type) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            switch (type) {
                case CP:
                    new ExporterCP().export(doc, fileWriter);
                    break;
            }
            fileWriter.close();
            logger.info("Saved Document to " + fileName);
        } catch (Exception e) {
            logger.error("An error occurred while writing a Document to " + fileName, e);
        }
    }

    public static enum FileType {
        CP
    }

}
