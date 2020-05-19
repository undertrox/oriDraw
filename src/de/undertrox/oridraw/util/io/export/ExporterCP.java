package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;

import java.io.Writer;

public class ExporterCP extends Exporter<Document> {
    @Override
    public void export(Document doc, Writer writer) throws Exception {
        for (OriLine line : doc.getCp().getOriLines()) {
            writer.write(line.getType().toCpId() + " " + line.getStartPoint().getX() + " " + line.getStartPoint().getY()
                    + " " + line.getEndPoint().getX() + " " + line.getEndPoint().getY() + "\n");
        }
    }
}
