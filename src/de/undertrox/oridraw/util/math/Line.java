package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

public class Line {

    private Vector start, end;
    private HesseNormalLine hesse;

    /**
     * Creates a new Line starting in start and ending in end
     *
     * @param start: starting point of the line
     * @param end:   ending Point of the line
     */
    public Line(Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return starting point of the line
     */
    public Vector getStartPoint() {
        return start;
    }

    /**
     * @return end point of the line
     */
    public Vector getEndPoint() {
        return end;
    }

    /**
     * Flips start- and Endpoint
     */
    public void flipPoints() {
        Vector tmp = getStartPoint();
        start = getEndPoint();
        end = tmp;
    }

    /**
     * @return squared length of the line
     */
    public double lengthSquared() {
        return getStartPoint().distanceSquared(getEndPoint());
    }

    /**
     * @return length of the line
     */
    public double length() {
        return getStartPoint().distance(getEndPoint());
    }

    /**
     * return a Line translated by the Vector translation
     *
     * @param translation: Vector that the Line should be translated by
     * @return new Line translated by translation
     */
    public Line translate(Vector translation) {
        Vector newStart = getStartPoint().add(translation);
        Vector newEnd = getEndPoint().add(translation);
        return new Line(newStart, newEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line) {
            Line l = (Line) obj;
            boolean equal;
            equal = (getStartPoint().equals(l.getStartPoint()) && getEndPoint().equals(l.getEndPoint()));
            equal |= (getEndPoint().equals(l.getStartPoint()) && getStartPoint().equals(l.getEndPoint())); // Lines with flipped Points are still equal
            return equal;
        }
        return false;
    }

    /**
     * Returns the point at which this and line intersect. if the lines are parallel or dont intersect,
     * this method returns null
     *
     * @param line: Line to get the Intersection point with
     * @return null if the lines are parallel or the lines dont intersect, the Intersection point if they do
     */
    public Vector getIntersection(Line line) {
        if (lengthSquared() < Constants.EPSILON || line.lengthSquared() < Constants.EPSILON) {
            return null;
        }
        Vector p = getHesse().intersect(line.getHesse());
        if (line.contains(p) && this.contains(p)) {
            return p;
        }
        return null;
    }

    public boolean contains(Vector p) {
        double dist1 = getStartPoint().distance(p);
        double dist2 = getEndPoint().distance(p);
        return Math.pow(dist1 + dist2, 2) - lengthSquared() < Constants.EPSILON;
    }

    public HesseNormalLine getHesse() {
        if (hesse == null) {
            hesse = new HesseNormalLine(getStartPoint(), getEndPoint());
        }
        return hesse;
    }

    public Vector toVector() {
        return getEndPoint().sub(getStartPoint());
    }

    public Vector getPointAt(double t) {
        return getStartPoint().add(toVector().scale(t));
    }

    /**
     * Distance of point to this line
     *
     * @param point: Point
     * @return distance of Point to this line
     */
    public double getDistance(Vector point) {
        return getHesse().distance(point);
    }

    @Override
    public String toString() {
        return "Line(" + getStartPoint() + "," + getEndPoint() + ")";
    }
}
