package de.undertrox.oridraw.util.io;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.StringTokenizer;

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
            BufferedReader bufferedReader = new BufferedReader(reader);

            switch (type) {
                case CP:
                    return readFromCpFile(filename, bufferedReader);
            }
        } catch (IOException e) {
            logger.error("An error occurred while reading a Document from " + filename, e);
        }
        return null;
    }

    public static void saveToFile(String fileName, Document doc, FileType type) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            switch (type) {
                case CP:
                    saveToCpFile(fileWriter, doc);
                    break;
            }
            fileWriter.close();
            logger.info("Saved Document to " + fileName);
        } catch (IOException e) {
            logger.error("An error occurred while writing a Document to " + fileName, e);
        }
    }

    public static enum FileType {
        CP
    }

    public static void saveToCpFile(FileWriter fw, Document doc) throws IOException {
        for (OriLine line : doc.getCp().getOriLines()) {
            fw.write(line.getType().toCpId() + " " + line.getStartPoint().getX() + " " + line.getStartPoint().getY()
                    + " " + line.getEndPoint().getX() + " " + line.getEndPoint().getY() + "\n");
        }
    }

    public static Document readFromCpFile(String fileName, BufferedReader reader) throws IOException {
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        Document doc = new Document(fileName, Constants.DEFAULT_PAPER_SIZE, Constants.DEFAULT_GRID_DIVISIONS);
        String line;
        while ((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, " ", false);
            try {
                int lineType = Integer.parseInt(tokenizer.nextToken());
                double x1 = Double.parseDouble(tokenizer.nextToken());
                double y1 = Double.parseDouble(tokenizer.nextToken());
                double x2 = Double.parseDouble(tokenizer.nextToken());
                double y2 = Double.parseDouble(tokenizer.nextToken());

                doc.getCp().addCrease(new OriPoint(x1, y1), new OriPoint(x2, y2), OriLine.Type.fromCpId(lineType));
            } catch (NumberFormatException e) {
                logger.warn("Syntax error in " + fileName);
            }
        }
        return doc;
    }
}
