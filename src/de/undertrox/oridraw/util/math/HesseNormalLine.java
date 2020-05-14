package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

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
        b = x1 - x2;
        c = y1 * x2 - x1 * y2;
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

    public double squaredDistance(Vector p) {
        double x = p.getX();
        double y = p.getY();
        return (a * x + b * y + c) * (a * x + b * y + c) / (a * a + b * b);
    }

    public double distance(Vector p) {
        double x = p.getX();
        double y = p.getY();
        return Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b));
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
}
