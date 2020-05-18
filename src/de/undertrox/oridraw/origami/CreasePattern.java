package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.log4j.Logger;

import java.util.Comparator;

public class CreasePattern extends CreaseCollection {

    private Logger logger;

    public CreasePattern() {
        super();
        logger = Logger.getLogger(this.getClass());
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
        addCrease(point1, point2, OriLine.Type.EDGE);
        addCrease(point2, point3, OriLine.Type.EDGE);
        addCrease(point3, point4, OriLine.Type.EDGE);
        addCrease(point4, point1, OriLine.Type.EDGE);
    }

    /**
     * adds a OriLine into the Creases list if it doesnt already exist. If it does, the old one will be overwritten
     * It also checks for Intersections and splits the OriLine if necessary
     *
     * @param startPoint : Start point of the OriLine
     * @param endPoint   :   end Point of the crease
     * @param type       : OriLine Type
     */
    public void addCrease(OriPoint startPoint, OriPoint endPoint, OriLine.Type type) {
        if (startPoint.equals(endPoint)) {
            return;
        }
        OriLine oriLine = new OriLine(startPoint, endPoint, type);
        UniqueItemList<Vector> intersections = getLineIntersections(oriLine.getLine());
        intersections.addAll(getPointIntersections(oriLine.getLine()));
        intersections.sort(Comparator.comparingDouble(a -> oriLine.getLine().getStartPoint().distanceSquared(a)));
        OriPoint lastPoint = startPoint;
        for (Vector intersection : intersections) {
            super.addCrease(lastPoint, new OriPoint(intersection), type);
            lastPoint = new OriPoint(intersection);
        }
        super.addCrease(lastPoint, endPoint, type);
    }

    public UniqueItemList<Vector> getLineIntersections(Line l) {
        UniqueItemList<Vector> intersections = new UniqueItemList<>();
        for (OriLine c : oriLines) {
            Vector intersection = l.getIntersection(c.getLine());
            if (intersection != null
                    && !(intersection.equals(l.getStartPoint()))
                    && !(intersection.equals(l.getStartPoint()))
                    && intersection.isValid()) {
                intersections.push(intersection);
            }
        }
        return intersections;
    }

    public UniqueItemList<Vector> getPointIntersections(Line l) {
        UniqueItemList<Vector> intersections = new UniqueItemList<>();
        for (Vector p : points) {
            if (l.contains(p)) {
                intersections.push(p);
            }
        }
        return intersections;
    }
}
