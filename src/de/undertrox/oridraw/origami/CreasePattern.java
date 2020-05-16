package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;

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
        Vector point1 = center.add(new Vector(-radius, -radius));
        Vector point2 = center.add(new Vector(-radius, radius));
        Vector point3 = center.add(new Vector(radius, radius));
        Vector point4 = center.add(new Vector(radius, -radius));
        addCrease(point1, point2, Crease.Type.EDGE);
        addCrease(point2, point3, Crease.Type.EDGE);
        addCrease(point3, point4, Crease.Type.EDGE);
        addCrease(point4, point1, Crease.Type.EDGE);
    }

    /**
     * adds a Crease into the Creases list if it doesnt already exist. If it does, the old one will be overwritten
     * It also checks for Intersections and splits the Crease if necessary
     *
     * @param startPoint : Start point of the Crease
     * @param endPoint   :   end Point of the crease
     * @param type       : Crease Type
     */
    public void addCrease(Vector startPoint, Vector endPoint, Crease.Type type) {
        if (startPoint.equals(endPoint)) {
            return;
        }
        Crease crease = new Crease(startPoint, endPoint, type);
        UniqueItemList<Vector> intersections = getLineIntersections(crease.getLine());
        intersections.addAll(getPointIntersections(crease.getLine()));
        intersections.sort(Comparator.comparingDouble(a -> crease.getLine().getStartPoint().distanceSquared(a)));
        Vector lastPoint = startPoint;
        for (Vector intersection : intersections) {
            super.addCrease(lastPoint, intersection, type);
            lastPoint = intersection;
        }
        super.addCrease(lastPoint, endPoint, type);
    }

    public UniqueItemList<Vector> getLineIntersections(Line l) {
        UniqueItemList<Vector> intersections = new UniqueItemList<>();
        for (Crease c : creases) {
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
