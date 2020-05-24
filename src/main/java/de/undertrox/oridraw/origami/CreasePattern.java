package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

public class CreasePattern extends CreaseCollection {

    private Logger logger;

    public CreasePattern() {
        super();
        logger = LogManager.getLogger(this.getClass());
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
        UniqueItemList<OriPoint> intersections = getLineIntersections(oriLine);
        intersections.addAll(getPointIntersections(oriLine));
        intersections.sort(Comparator.comparingDouble(a -> oriLine.getStartPoint().distanceSquared(a)));
        OriPoint lastPoint = startPoint;
        for (OriPoint intersection : intersections) {
            super.addCrease(lastPoint, new OriPoint(intersection), type);
            if (intersection.getLines().size() == 1) {
                OriLine l = intersection.getLines().get(0);
                intersection.getLines().remove(l);
                splitLine(l, intersection);
            }
            lastPoint = intersection;
        }
        super.addCrease(lastPoint, endPoint, type);
    }

    public UniqueItemList<OriPoint> getLineIntersections(Line l) {
        UniqueItemList<OriPoint> intersections = new UniqueItemList<>();
        for (OriLine c : oriLines) {
            Vector intersection = l.getIntersection(c);
            if (intersection != null
                    && !(intersection.equals(l.getStartPoint()))
                    && !(intersection.equals(l.getStartPoint()))
                    && intersection.isValid()) {
                OriPoint p = new OriPoint(intersection);
                p.addLine(c);
                intersections.push(p);
            }
        }
        return intersections;
    }

    public UniqueItemList<OriPoint> getPointIntersections(Line l) {
        UniqueItemList<OriPoint> intersections = new UniqueItemList<>();
        for (Vector p : points) {
            if (l.contains(p)) {
                intersections.push(new OriPoint(p));
            }
        }
        return intersections;
    }

    public void splitLine(OriLine l, OriPoint p) {
        OriPoint start = l.getStartPoint();
        OriPoint end = l.getEndPoint();
        OriLine.Type t = l.getType();
        getOriLines().remove(l);
        start.getLines().remove(l);
        end.getLines().remove(l);
        super.addCrease(start, p, t);
        super.addCrease(p, end, t);
    }
}
