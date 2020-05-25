package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.util.UniqueItemList;

import java.util.ArrayList;

public class Polygon {
    private UniqueItemList<Vector> points;
    private UniqueItemList<Line> boundary;

    public Polygon(Vector... points) {
        this.points = new UniqueItemList<>();
        for (Vector point : points) {
            this.points.push(point);
        }
        createBoundary();
    }

    private void createBoundary() {
        boundary = new UniqueItemList<>();
        for (int i = 1; i < points.size(); i++) {
            boundary.push(new Line(points.get(i - 1), points.get(i)));
        }
        boundary.push(new Line(points.get(points.size()-1), points.get(0)));
    }

    /**
     * Flips the Polygon along the line L
     *
     * @param l: Mirror line
     * @return flipped Polygon
     */
    public Polygon flipAlong(Line l) {
        HesseNormalLine line = l.getHesse();

        ArrayList<Vector> newPoints = new ArrayList<>();
        for (Vector point : points) {
            newPoints.add(line.flip(point));
        }
        return new Polygon(newPoints.toArray(new Vector[0]));
    }
}
