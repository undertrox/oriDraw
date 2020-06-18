package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

import java.util.Objects;

/**
 * this class represents a Line that starts at a Point start
 * and ends at the Point end
 */
public class Line {

    private Vector start;
    private Vector end;
    private HesseNormalLine hesse;
    private boolean isValid;
    private Rectangle boundingBox;

    public boolean isValid() {
        return isValid;
    }

    /**
     * Creates a new Line starting in start and ending in end
     *
     * @param start: starting point of the line
     * @param end:   ending Point of the line
     */
    public Line(Vector start, Vector end) {
        isValid = start.isValid() && end.isValid();
        this.start = start;
        this.end = end;
        isValid = isValid() && lengthSquared()>Constants.EPSILON;
    }

    public Rectangle getBoundingBox() {
        if (boundingBox == null) {
            updateBoundingBox();
        }
        return boundingBox;
    }

    private void updateBoundingBox() {
        boundingBox = new Rectangle(getStart(), getEnd());
    }

    /**
     * @return starting point of the line
     */
    public Vector getStart() {
        return start;
    }

    /**
     * @return end point of the line
     */
    public Vector getEnd() {
        return end;
    }

    /**
     * Flips start- and Endpoint
     */
    public void flipPoints() {
        Vector tmp = getStart();
        start = getEnd();
        end = tmp;
    }

    /**
     * @return squared length of the line
     */
    public double lengthSquared() {
        return isValid()? getStart().distanceSquared(getEnd()) : -1;
    }

    /**
     * @return length of the line
     */
    public double length() {
        return isValid()? getStart().distance(getEnd()) : -1;
    }

    /**
     * return a Line translated by the Vector translation
     *
     * @param translation: Vector that the Line should be translated by
     * @return new Line translated by translation
     */
    public Line translate(Vector translation) {
        Vector newStart = getStart().add(translation);
        Vector newEnd = getEnd().add(translation);
        return new Line(newStart, newEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line) {
            Line l = (Line) obj;
            boolean equal;
            equal = (getStart().equals(l.getStart()) && getEnd().equals(l.getEnd()));
            equal |= (getEnd().equals(l.getStart()) && getStart().equals(l.getEnd())); // Lines with flipped Points are still equal
            return equal;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd())+Objects.hash(getEnd(), getStart());
    }

    /**
     * Returns the point at which this and line intersect. if the lines are parallel or dont intersect,
     * this method returns null
     *
     * @param line: Line to get the Intersection point with
     * @return null if the lines are parallel or the lines dont intersect, the Intersection point if they do
     */
    public Vector getIntersection(Line line) {
        if (!isValid) {return Vector.UNDEFINED;}
        if (!getBoundingBox().overlaps(line.getBoundingBox())) {
            return Vector.UNDEFINED;
        }
        if (getHesse().parallel(line.getHesse())) {
            return Vector.UNDEFINED;
        }
        if (lengthSquared() < Constants.EPSILON || line.lengthSquared() < Constants.EPSILON) {
            return Vector.UNDEFINED;
        }
        Vector p = getHesse().intersect(line.getHesse());
        if (line.contains(p) && this.contains(p)) {
            return p;
        }
        return Vector.UNDEFINED;
    }

    /**
     * whether p is on this line
     * @param p: point to check
     * @return true if p is on this line, false if p is not on this line
     */
    public boolean contains(Vector p) {
        if (!isValid) return false;
        double dist1 = getStart().distance(p);
        double dist2 = getEnd().distance(p);
        return Math.abs(Math.pow(dist1 + dist2, 2) - lengthSquared()) < Constants.EPSILON;
    }

    /**
     *
     * @return Hesse Normal Form representation of this line
     */
    public HesseNormalLine getHesse() {
        if (!isValid) return null;
        if (hesse == null) {
            hesse = new HesseNormalLine(getStart(), getEnd()).normalize();
        }
        return hesse;
    }

    /**
     *
     * @return Vector representation of this line
     */
    public Vector toVector() {
        return isValid()? getEnd().sub(getStart()) : Vector.UNDEFINED;
    }

    public Vector getPointAt(double t) {
        return isValid()? getStart().add(toVector().scale(t)) : Vector.UNDEFINED;
    }

    /**
     * Distance of point to this line
     *
     * @param point: Point
     * @return distance of Point to this line
     */
    public double getDistance(Vector point) {
        return isValid? getHesse().distance(point) : -1;
    }

    /**
     * Squared Distance of point to this line
     * @param point: Point
     * @return Squared distance of Point to this line
     */
    public double getDistanceSquared(Vector point) {
        return isValid? getHesse().squaredDistance(point) : -1;
    }

    @Override
    public String toString() {
        return "Line(" + getStart() + "," + getEnd() + ")";
    }

    public Line extendUntilIntersection(Line l) {
        if (l == null || !isValid || !l.isValid()) return new Line(start, end);
        HesseNormalLine hnl = l.getHesse();
        if (!hnl.parallel(getHesse())) {
            Vector p = hnl.intersect(getHesse());
            if (p.isValid())
                return new Line(start, p);
        }
        return new Line(start, end);
    }
}
