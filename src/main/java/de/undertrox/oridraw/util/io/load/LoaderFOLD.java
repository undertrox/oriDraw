package de.undertrox.oridraw.util.io.load;

import com.google.gson.*;
import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

public class LoaderFOLD extends Loader<Document> {
    Logger logger = LogManager.getLogger();
    @Override
    public String[] extensions() {
        return new String[] {"fold"};
    }

    @Override
    public Document load(Reader r) throws IOException {
        @SuppressWarnings( "deprecation" )
        JsonObject root = new JsonParser().parse(r).getAsJsonObject();
        String fileSpec = root.get("file_spec").getAsString();
        JsonArray fileClasses = root.get("file_classes").getAsJsonArray();
        JsonArray frameClasses = root.get("frame_classes").getAsJsonArray();
        String fileTitle = root.get("file_title").getAsString();
        JsonArray vectorCoords = root.get("vertices_coords").getAsJsonArray();
        JsonArray edgesVertices = root.get("edges_vertices").getAsJsonArray();
        JsonArray edgesAssignment = root.get("edges_assignment").getAsJsonArray();
        JsonArray frameAttributes = Objects.requireNonNullElse(root.get("frame_attributes"), new JsonArray()).getAsJsonArray();
        if (!fileClasses.contains(new JsonPrimitive("singleModel"))){
            logger.warn("FOLD File does not specify that it contains a single model.");
        }
        if (!frameClasses.contains(new JsonPrimitive("creasePattern"))){
            logger.warn("FOLD File does not specify that it contains a Crease Pattern.");
        }
        if (!frameAttributes.contains(new JsonPrimitive("2D"))) {
            logger.warn("FOLD File does not specify that the coordinates are in 2D. Z coordinates will be ignored.");
        }
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        CreasePattern cp = new CreasePattern();
        for (int i = 0; i < edgesVertices.size(); i++) {
            JsonElement edgeVertices = edgesVertices.get(i);
            int startIndex = edgeVertices.getAsJsonArray().get(0).getAsInt();
            int endIndex = edgeVertices.getAsJsonArray().get(1).getAsInt();

            double x1 = vectorCoords.get(startIndex).getAsJsonArray().get(0).getAsDouble();
            double y1 = vectorCoords.get(startIndex).getAsJsonArray().get(1).getAsDouble();
            double x2 = vectorCoords.get(endIndex).getAsJsonArray().get(0).getAsDouble();
            double y2 = vectorCoords.get(endIndex).getAsJsonArray().get(1).getAsDouble();
            maxX = Math.max(maxX, Math.max(x1, x2));
            minX = Math.min(minX, Math.min(x1, x2));
            maxY = Math.max(maxY, Math.max(y1, y2));
            minY = Math.min(minY, Math.min(y1, y2));
            String assignment = edgesAssignment.get(i).getAsString();
            OriLine.Type type;
            switch (assignment.toLowerCase()) {
                case "b":
                    type = OriLine.Type.EDGE;
                    break;
                case "m":
                    type = OriLine.Type.MOUNTAIN;
                    break;
                case "v":
                    type = OriLine.Type.VALLEY;
                    break;
                case "f":
                    type = OriLine.Type.AUX;
                    break;
                default:
                    type = OriLine.Type.UNKNOWN;
            }
            cp.addOriLine(new OriPoint(x1, y1), new OriPoint(x2, y2), type);
        }
        Vector center = new Vector((minX+maxX)/2, (minY+maxY)/2);
        Vector paperSize = new Vector(center.getX()-minX, center.getY()-minY).scale(2);

        if (paperSize.lengthSquared() < Constants.EPSILON) {
            throw new RuntimeException("Fold File does not specify Vectors far enough apart");
        }
        Transform t = new Transform(center.invertSign(), paperSize.scale(400/paperSize.getX()), 0);
        cp.apply(cp.transform(t));
        Document doc = new Document(fileTitle, t.apply(paperSize), t.apply(center), Constants.DEFAULT_GRID_DIVISIONS);
        for (OriLine oriLine : cp.getOriLines()) {
            doc.getCp().addOriLine(new OriPoint(oriLine.getStart()), new OriPoint(oriLine.getEnd()), oriLine.getType());
        }
        return doc;
    }
}
