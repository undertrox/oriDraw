package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

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
    public void addOriLine(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        if (startPoint.equals(endPoint)) {
            return;
        }
        OriLine oriLine = new OriLine(startPoint, endPoint, type);
        super.addOriLine(startPoint, endPoint, type);
        UniqueItemList<OriPoint> intersections = getLineIntersections(oriLine);
        points.addAll(intersections);
        splitLinesAtPoints();
    }

    private void splitLinesAtPoints() {
        OriLineCollection newcp = new OriLineCollection();

        for (OriLine line : oriLines) {
            UniqueItemList<OriPoint> pointsOnLine = new UniqueItemList<>();
            for (OriPoint point : points) {
                if(line.contains(point)) {
                    pointsOnLine.add(point);
                }
            }
            pointsOnLine.sort(Comparator.comparingDouble(point -> line.getStartPoint().distanceSquared(point)));
            for (int i = 1; i < pointsOnLine.size(); i++) {
                OriLine.Type type = line.getType();
                newcp.addOriLine(pointsOnLine.get(i-1), pointsOnLine.get(i), type);
            }
        }
        this.points = newcp.points;
        this.oriLines = newcp.oriLines;
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
                p.addLine(c);
                intersections.push(p);
            }
        }
        return intersections;
    }

    /**
     * Returns a List of all Points where an existing Point in the cp lies on l
     * @param l: line
     * @return List of all Points where an existing Point in the cp lies on l
     */
    public UniqueItemList<OriPoint> getPointIntersections(Line l) {
        UniqueItemList<OriPoint> intersections = new UniqueItemList<>();
        for (Vector p : points) {
            if (l.contains(p)) {
                intersections.push(new OriPoint(p));
            }
        }
        return intersections;
    }

    public void splitAtIntersections(Vector p) {
        for (int i = 0; i<oriLines.size(); i++) {
            OriLine l = oriLines.get(i);
            if (l.contains(p) && !p.equals(l.getStartPoint()) && !p.equals(l.getEndPoint())) {
                OriPoint op = new OriPoint(p);
                splitLine(l,op);
                i = 0;
            }
        }
    }

    public void addLineWithoutIntersectionCheck(OriPoint start, OriPoint end, OriLine.Type type) {
        super.addOriLine(start, end, type);
    }

    /**
     * Splits l at the point p
     * @param l: Line to be split
     * @param p: Point to split the line at
     */
    public void splitLine(OriLine l, OriPoint p) {
        if (!l.contains(p)) {
            throw new IllegalArgumentException("Cant split at a point that is not on the line");
        }
        if (!getOriLines().contains(l)) {
            throw new IllegalArgumentException("Cant split a line that is not in the Crease Pattern");
        }
        OriPoint start = l.getStartPoint();
        OriPoint end = l.getEndPoint();
        OriLine.Type t = l.getType();
        getOriLines().remove(l);
        start.getLines().remove(l);
        end.getLines().remove(l);
        super.addOriLine(start, p, t);
        super.addOriLine(p, end, t);
    }
}
