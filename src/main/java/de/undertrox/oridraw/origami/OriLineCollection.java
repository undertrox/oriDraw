package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;

public class OriLineCollection {
    protected UniqueItemList<OriLine> oriLines;
    protected UniqueItemList<OriPoint> points;

    public OriLineCollection() {
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

    /**
     * Adds an OriLine starting from startPoint, ending on endPoint and with the type type.
     * @param startPoint: Starting Point of the Crease
     * @param endPoint: Ending Point of the Crease
     * @param type: Type of the crease
     */
    public void addOriLine(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        if (startPoint.equals(endPoint)) {
            return;
        }
        startPoint = addPoint(startPoint);
        endPoint = addPoint(endPoint);
        OriLine oriLine = new OriLine(startPoint, endPoint, type);
        OriLine c = oriLines.push(oriLine);
        if (c == oriLine) {
            System.out.println("Added Line " + oriLine);
        }
        c.setType(type);
    }


    public UniqueItemList<OriLine> getOriLines() {
        return oriLines;
    }

    public UniqueItemList<OriPoint> getPoints() {
        return points;
    }

    /**
     * Adds an OriLine
     * @param line: OriLine to add
     */
    public void addOriLine(OriLine line) {
        addOriLine(line.getStartPoint(), line.getEndPoint(), line.getType());
    }
}
