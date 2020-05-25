package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

import static java.lang.Math.sqrt;

/**
 * Represents a Line in 2D space using the formula
 * ax + by + c = 0
 */
public class HesseNormalLine {
    private double a;
    private double b;
    private double c;

    /**
     * Creates a Line that goes through point1 and point2
     * @param point1: Vector
     * @param point2: Vector
     */
    public HesseNormalLine(Vector point1, Vector point2) {
        double x1 = point1.getX();
        double y1 = point1.getY();
        double x2 = point2.getX();
        double y2 = point2.getY();

        if (point1.equals(point2)) {
            throw new IllegalArgumentException("Start and End Points of a Hesse Normal Line cant be equal");
        }

        a = y2 - y1;
        b = -(x2 - x1);
        c = -a * x1 - b * y1;
        correctCoeffs();
    }

    /**
     * Creates a Line using the Forumla
     * ax + by + c = 0
     * @param a: double
     * @param b: double
     * @param c:double
     */
    public HesseNormalLine(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        correctCoeffs();
    }


    /**
     * Corrects a, b, and c so that as x increases, y does as well (if applicable)
     */
    private void correctCoeffs() {
        if ((a < 0)) {
            a = -a;
            b = -b;
            c = -c;
        }
        if (Math.abs(a) < Constants.EPSILON && b < 0) {
            a = -a;
            b = -b;
            c = -c;
        }
    }

    /**
     * Normalizes the Line
     * @return Normalized line
     */
    public HesseNormalLine normalize() {
        double m = sqrt(a * a + b * b);
        return new HesseNormalLine(a/m, b/m, c/m);
    }

    /**
     * Squared distance to a Point p
     * @param p: Point to calculate the distance to
     * @return Squared Distance to p
     */
    public double squaredDistance(Vector p) {
        double x = p.getX();
        double y = p.getY();
        return (a * x + b * y + c) * (a * x + b * y + c) / (a * a + b * b);
    }

    /**
     * distance to a Point p
     * @param p: Point to calculate the distance to
     * @return Distance to p
     */
    public double distance(Vector p) {
        double x = p.getX();
        double y = p.getY();
        return Math.abs((a * x + b * y + c) / sqrt(a * a + b * b));
    }

    /**
     * Returns p projected onto this line so that there is a Normal line orthogonal to this line that goes throuh
     * p and the projected Point
     *
     * @param p: Point to be projected
     * @return Shadow point of p / p projected onto this line
     */
    public Vector getShadowPoint(Vector p) {
        return intersect(normal(p));
    }

    /**
     * returns a Normal line orthogonal to this line that goes through p
     *
     * @param p: point for the Normal line to go through
     * @return Normal line going through p
     */
    public HesseNormalLine normal(Vector p) {
        HesseNormalLine l = new HesseNormalLine(0, 0, 0);
        double x = p.getX();
        double y = p.getY();
        l.c = -b * x + a * y;
        l.a = b;
        l.b = -a;
        return l;
    }

    /**
     * @param l: Line to intersect
     * @return Intersection point between this line and l
     */
    public Vector intersect(HesseNormalLine l) {
        double a2 = l.a;
        double b2 = l.b;
        double c2 = l.c;

        double x = (b * c2 - b2 * c) / (a * b2 - a2 * b);
        double y = (a2 * c - a * c2) / (a * b2 - a2 * b);
        Vector v = new Vector(x,y);
        return v.isValid() ? v : Vector.UNDEFINED;
    }

    /**
     * whether the Line l is Parallel to this line
     * @param l: line to check
     * @return true if l is parallel to this line, false otherwise
     */
    public boolean parallel(HesseNormalLine l) {
        return a == l.a && b == l.b;
    }

    /**
     * Flips the Vector along the line
     *
     * @return flipped Vector
     */
    public Vector flip(Vector p) {
        double d = distance(p);
        double newX = p.getX() - 2 * a * d;
        double newY = p.getY() - 2 * b * d;
        return new Vector(newX, newY);
    }
}
