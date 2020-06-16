package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

import java.util.Collection;
import java.util.Comparator;

public class CreasePattern extends OriLineCollection {

    public CreasePattern() {
        super();
    }

    /**
     * creates a square centered on center with the side length sidelength
     *
     * @param center:     center of the Square
     * @param sideLength: side length of the square
     */
    public void createSquare(Vector center, double sideLength) {
        double radius = sideLength / 2;
        OriPoint point1 = new OriPoint(center.add(new Vector(-radius, -radius)));
        OriPoint point2 = new OriPoint(center.add(new Vector(-radius, radius)));
        OriPoint point3 = new OriPoint(center.add(new Vector(radius, radius)));
        OriPoint point4 = new OriPoint(center.add(new Vector(radius, -radius)));
        addOriLine(point1, point2, OriLine.Type.EDGE);
        addOriLine(point2, point3, OriLine.Type.EDGE);
        addOriLine(point3, point4, OriLine.Type.EDGE);
        addOriLine(point4, point1, OriLine.Type.EDGE);
    }

    /**
     * adds a OriLine into the Creases list if it doesnt already exist. If it does, the old one will be overwritten
     * It also checks for Intersections and splits the OriLine if necessary
     *
     * @param startPoint : Start point of the OriLine
     * @param endPoint   :   end Point of the crease
     * @param type       : OriLine Type
     */
    @Override
    public OriLine addOriLine(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        addOriLineWithoutRebuild(startPoint, endPoint, type);
        rebuildCP();
        return null;
    }

    private void addOriLineWithoutRebuild(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        if (startPoint.equals(endPoint)) {
            return;
        }
        OriLine oriLine = super.addOriLine(startPoint, endPoint, type);
        UniqueItemList<OriPoint> intersections = getLineIntersections(oriLine);
        points.addAll(intersections);
    }

    public void addOriLines(Collection<OriLine> lines) {
        for (OriLine line : lines) {
            addOriLineWithoutRebuild(new OriPoint(line.getStartPoint()), new OriPoint(line.getEndPoint()), line.getType());
        }
        rebuildCP();
    }


    // Maybe this can be optimized by only rebuilding the cp around new lines
    private void rebuildCP() {
        OriLineCollection newcp = new OriLineCollection();

        for (OriLine line : oriLines) {
            UniqueItemList<OriPoint> pointsOnLine = new UniqueItemList<>();
            for (OriPoint point : points) {
                if(line.contains(point)) {
                    pointsOnLine.add(new OriPoint(point));
                }
            }
            pointsOnLine.sort(Comparator.comparingDouble(point -> line.getStartPoint().distanceSquared(point)));
            for (int i = 1; i < pointsOnLine.size(); i++) {
                OriLine.Type type = line.getType();
                newcp.addOriLine(pointsOnLine.get(i-1), pointsOnLine.get(i), type);
            }
        }
        apply(newcp);
    }

    /**
     * Returns a List of Points at which l intersects lines in this cp
     * @param l: Line
     * @return List of Points at which l intersects lines in this cp
     */
    public UniqueItemList<OriPoint> getLineIntersections(Line l) {
        UniqueItemList<OriPoint> intersections = new UniqueItemList<>();
        for (OriLine c : oriLines) {
            Vector intersection = l.getIntersection(c);
            if (intersection != null
                    && !(intersection.equals(l.getStartPoint()))
                    && !(intersection.equals(l.getEndPoint()))
                    && intersection != Vector.UNDEFINED) {
                OriPoint p = new OriPoint(intersection);
                intersections.push(p);
            }
        }
        return intersections;
    }

    public UniqueItemList<OriPoint> getPointsOnLine(Line l) {
        UniqueItemList<OriPoint> pointsOnLine = new UniqueItemList<>();
        for (OriPoint point : points) {
            if (l.contains(point)) {
                pointsOnLine.add(new OriPoint(point));
            }
        }
        return pointsOnLine;
    }

    public void addLineWithoutIntersectionCheck(OriPoint start, OriPoint end, OriLine.Type type) {
        super.addOriLine(start, end, type);
    }
}
