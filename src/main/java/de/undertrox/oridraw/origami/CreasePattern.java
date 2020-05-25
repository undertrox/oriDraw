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
        UniqueItemList<OriPoint> intersections = getLineIntersections(oriLine);
        intersections.addAll(getPointIntersections(oriLine));
        intersections.sort(Comparator.comparingDouble(a -> oriLine.getStartPoint().distanceSquared(a)));
        OriPoint lastPoint = startPoint;
        for (OriPoint intersection : intersections) {
            super.addOriLine(lastPoint, new OriPoint(intersection), type);
            if (intersection.getLines().size() == 1) {
                OriLine l = intersection.getLines().get(0);
                intersection.getLines().remove(l);
                splitLine(l, intersection);
            }
            lastPoint = intersection;
        }
        super.addOriLine(lastPoint, endPoint, type);
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
