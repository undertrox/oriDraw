package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

public class CreaseCollection {
    protected UniqueItemList<Crease> creases;
    protected UniqueItemList<Vector> points;

    public CreaseCollection() {
        creases = new UniqueItemList<>();
        points = new UniqueItemList<>();
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

    public void addCrease(Vector startPoint, Vector endPoint, Crease.Type type) {
        startPoint = addPoint(startPoint);
        endPoint = addPoint(endPoint);
        Crease crease = new Crease(startPoint, endPoint, type);
        Crease c = creases.push(crease);
        c.setType(type);
    }


    public UniqueItemList<Crease> getCreases() {
        return creases;
    }

    public UniqueItemList<Vector> getPoints() {
        return points;
    }
}
