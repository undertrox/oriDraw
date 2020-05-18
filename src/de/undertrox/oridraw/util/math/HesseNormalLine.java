package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

import static java.lang.Math.sqrt;

public class HesseNormalLine {
    private double a;
    private double b;
    private double c;

    public HesseNormalLine(Vector start, Vector end) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();

        if (start.equals(end)) {
            throw new IllegalArgumentException("Start and End Points of a Hesse Normal Line cant be equal");
        }

        a = y2 - y1;
        b = -(x2 - x1);
        c = -a * x1 - b * y1;
        correctCoeffs();
    }

    public HesseNormalLine(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        correctCoeffs();
    }


    private void correctCoeffs() {
        if ((a < 0.0)) {
            a = -a;
            b = -b;
            c = -c;
        }
        if (Math.abs(a) < Constants.EPSILON) {
            if (b < 0.0) {
                a = -a;
                b = -b;
                c = -c;
            }
        }
    }

    public HesseNormalLine normalize() {
        double m = sqrt(a * a + b * b);
        double newA = a / m;
        double newB = b / m;
        double newC = c / m;
        return new HesseNormalLine(a, b, c);
    }

    public double squaredDistance(Vector p) {
        double x = p.getX();
        double y = p.getY();
        return (a * x + b * y + c) * (a * x + b * y + c) / (a * a + b * b);
    }

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
     * @return: Shadow point of p / p projected onto this line
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
        double e;
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
        return new Vector(x, y);
    }

    /**
     * Flips the Vector along the line
     *
     * @return
     */
    public Vector flip(Vector p) {
        double d = distance(p);
        double newX = p.getX() - 2 * a * d;
        double newY = p.getY() - 2 * b * d;
        return new Vector(newX, newY);
    }
}
