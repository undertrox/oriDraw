package de.undertrox.oridraw.util.math;

public class Triangle {
    private Vector a;
    private Vector b;
    private Vector c;

    public Triangle(Vector a, Vector b, Vector c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public Vector getC() {
        return c;
    }

    /**
     *
     * @return Incenter of the Triangle, the intersection of the three Angle Bisectors
     */
    public Vector incenter() {
        double bc = b.distance(c);
        double ab = a.distance(b);
        double ca = c.distance(a);

        double ox = (bc * a.getX() + ca * b.getX() + ab * c.getX()) / perimeter();
        double oy = (bc * a.getY() + ca * b.getY() + ab * c.getY()) / perimeter();
        return new Vector(ox, oy);
    }

    /**
     *
     * @return Perimeter of the Triangle
     */
    public double perimeter() {
        return b.distance(c) + a.distance(b) + a.distance(c);
    }
}
