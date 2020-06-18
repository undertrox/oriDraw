package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.util.math.Vector;

import java.io.IOException;
import java.io.Writer;

public class ExporterSVG extends Exporter<Document> {

    @Override
    public String[] extensions() {
        return new String[] {"svg"};
    }

    @Override
    public boolean isLossLess() {
        return false;
    }

    @Override
    public void export(Document doc, Writer writer) throws IOException {
        int finalSize = 1000;
        double scale = finalSize/400.0;
        Transform transform = new Transform(new Vector(200*scale), scale, 0);
        writer.append(String.format("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\"\n" +
                "\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\"\n" +
                " xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:space=\"preserve\"\n" +
                " width=\"%spx\" height=\"%s\"\n" +
                " viewBox=\"0 0 %s %s\" >", finalSize, finalSize, finalSize, finalSize));
        for (OriLine oriLine : doc.getCp().getOriLines()) {
            Vector transformedStart = transform.apply(oriLine.getStart());
            Vector transformedEnd = transform.apply(oriLine.getEnd());
            writer.append(String.format("<line style=\"%s\" x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\"/>",
                    SVGHelper.toSVGStyle(RenderSettings.getColorManager().getLineStyleForCreaseType(oriLine.getType())),
                    transformedStart.getX(), transformedStart.getY(), transformedEnd.getX(), transformedEnd.getY()
                    ));
        }
        writer.append("</svg>");
    }
}
