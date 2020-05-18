package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

public class CreaseCollection {
    protected UniqueItemList<OriLine> oriLines;
    protected UniqueItemList<OriPoint> points;

    public CreaseCollection() {
        oriLines = new UniqueItemList<>();
        points = new UniqueItemList<>();
    }

    /**
     * Adds a Point into the Points list if it isnt already there
     *
     * @param vec: Point to be added
     * @return vec if it wasnt in the List, otherwise the point that is in the list with vec's coordinates
     */
    public OriPoint addPoint(OriPoint vec) {
        return points.push(vec);
    }

    public void addCrease(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        startPoint = addPoint(startPoint);
        endPoint = addPoint(endPoint);
        OriLine oriLine = new OriLine(startPoint, endPoint, type);
        OriLine c = oriLines.push(oriLine);
        c.setType(type);
    }


    public UniqueItemList<OriLine> getOriLines() {
        return oriLines;
    }

    public UniqueItemList<OriPoint> getPoints() {
        return points;
    }
}
