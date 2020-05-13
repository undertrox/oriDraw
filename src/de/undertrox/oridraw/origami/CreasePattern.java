package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

import java.util.List;

public class CreasePattern {
    private UniqueItemList<Vector> points;
    private UniqueItemList<Crease> creases;

    public CreasePattern() {
        points = new UniqueItemList<>();
        creases = new UniqueItemList<>();
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
     * Adds a Point into the Points list if it isnt already there
     *
     * @param vec: Point to be added
     * @return vec if it wasnt in the List, otherwise the point that is in the list with vec's coordinates
     */
    public Vector addPoint(Vector vec) {
        return points.push(vec);
    }


    /**
     * adds a Crease into the Creases list if it doesnt already exist. If it does, the old one will be overwritten
     *
     * @param startPoint: Start point of the Crease
     * @param endPoint:   end Point of the crease
     * @param type        : Crease Type
     * @return the added Crease
     */
    public Crease addCrease(Vector startPoint, Vector endPoint, Crease.Type type) {
        startPoint = addPoint(startPoint);
        endPoint = addPoint(endPoint);
        Crease crease = new Crease(startPoint, endPoint, type);
        Crease c = creases.push(crease);
        c.setType(type);
        return c;
    }

    public List<Crease> getCreases() {
        return creases;
    }

    public List<Vector> getPoints() {
        return points;
    }
}
