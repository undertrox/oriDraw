package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;

import java.io.IOException;
import java.io.Writer;

public class ExporterCP extends Exporter<Document> {
    @Override
    public String[] extensions() {
        return new String[]{"cp"};
    }

    @Override
    public boolean isLossLess() {
        return true;
    }

    /**
     * exports a Document into a .cp file using the Writer writer
     * @param writer: writer to export data into
     * @param doc: Document to export
     * @throws IOException if writing fails
     */
    @Override
    public void export(Document doc, Writer writer) throws IOException {
        for (OriLine line : doc.getCp().getOriLines()) {
            writer.write(line.getType().toCpId() + " " + line.getStartPoint().getX() + " " + line.getStartPoint().getY()
                    + " " + line.getEndPoint().getX() + " " + line.getEndPoint().getY() + "\n");
        }
    }
}
