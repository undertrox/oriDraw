package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Transform;

import java.util.Collection;
import java.util.List;

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
    public OriLine addOriLine(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        if (startPoint.equals(endPoint)) {
            return null;
        }
        startPoint = addPoint(startPoint);
        endPoint = addPoint(endPoint);
        OriLine oriLine = new OriLine(startPoint, endPoint, type);
        OriLine c = oriLines.push(oriLine);
        startPoint.addLine(oriLine);
        endPoint.addLine(oriLine);
        c.setType(type);
        return oriLine;
    }

    /**
     * Remove a line From the Collection while taking care of deleting references to the line
     * in the OriPoints
     * @param l line to be removed
     * @return true if the line was successfully removed, false if it could not be found
     */
    public boolean removeOriLine(OriLine l) {
        int index = oriLines.indexOf(l);
        if (index == -1) {
            return false;
        }
        l = oriLines.get(index);
        l.getStart().getLines().remove(l);
        l.getEnd().getLines().remove(l);
        oriLines.remove(index);
        return true;
    }

    public void removeAllOrilines(Collection<OriLine> lines) {
        for (OriLine line : lines) {
            removeOriLine(line);
        }
    }


    public List<OriLine> getOriLines() {
        return oriLines;
    }

    public UniqueItemList<OriPoint> getPoints() {
        return points;
    }

    /**
     * Transforms the whole Collection by a transform
     * @param transform: Transformation to apply to the Collection
     * @return transformed collection
     */
    public OriLineCollection transform(Transform transform) {
        OriLineCollection newColl = new OriLineCollection();
        for (OriLine oriLine : oriLines) {
            newColl.addOriLine(new OriPoint(transform.apply(oriLine.getStart())),
                    new OriPoint(transform.apply(oriLine.getEnd())),
                    oriLine.getType());
        }
        return newColl;
    }

    /**
     * copies coll into this
     * @param coll: Collection to copy
     */
    public void apply(OriLineCollection coll) {
        this.oriLines = coll.oriLines;
        this.points = coll.points;
    }

    /**
     * Adds an OriLine
     * @param line: OriLine to add
     */
    public void addOriLine(OriLine line) {
        this.addOriLine(line.getStart(), line.getEnd(), line.getType());
    }
}
