package de.undertrox.oridraw.util.math;

public class Line {

    private Vector start, end;

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

    @Override
    public String toString() {
        return "Line(" + getStartPoint() + "," + getEndPoint() + ")";
    }
}
