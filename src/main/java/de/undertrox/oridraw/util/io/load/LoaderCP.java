package de.undertrox.oridraw.util.io.load;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.util.math.Vector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class LoaderCP extends Loader<Document> {
    @Override
    public String[] extensions() {
        return new String[]{"cp"};
    }

    /**
     * Loads a .cp File to create a document
     * @param r: reader to load data from
     * @return Document with the Creases in the file
     * @throws IOException if reading the file fails
     */
    @Override
    public Document load(Reader r) throws IOException {
        Document doc = new Document("", new Vector(Constants.DEFAULT_PAPER_SIZE), Constants.DEFAULT_GRID_DIVISIONS);
        String line;
        BufferedReader reader = new BufferedReader(r);
        while ((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, " ", false);
            try {
                int lineType = Integer.parseInt(tokenizer.nextToken());
                double x1 = Double.parseDouble(tokenizer.nextToken());
                double y1 = Double.parseDouble(tokenizer.nextToken());
                double x2 = Double.parseDouble(tokenizer.nextToken());
                double y2 = Double.parseDouble(tokenizer.nextToken());
                doc.getCp().addLineWithoutIntersectionCheck(new OriPoint(x1, y1), new OriPoint(x2, y2), OriLine.Type.fromCpId(lineType));

            } catch (NumberFormatException e) {
                logger.warn("Syntax error");
            }
        }
        return doc;
    }
}
