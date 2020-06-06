package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

public class Rectangle {
    private Vector corner1;
    private Vector corner2;
    private Vector size;
    private Line[] lines;

    public Rectangle(Vector corner1, Vector corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        if (corner1.getY() > corner2.getY()) {
            Vector tmp = new Vector(corner1.getX(), corner2.getY());
            corner2 = new Vector(corner2.getX(), corner1.getY());
            corner1 = tmp;
        }
        if (corner1.getX() > corner2.getX()) {
            Vector tmp = new Vector(corner2.getX(), corner1.getY());
            corner2 = new Vector(corner1.getX(), corner2.getY());
            corner1 = tmp;
        }
        lines = new Line[] {
                new Line(corner1, new Vector(corner1.getX(), corner2.getY())),
                new Line(corner1, new Vector(corner2.getX(), corner1.getY())),
                new Line(corner2, new Vector(corner1.getX(), corner2.getY())),
                new Line(corner2, new Vector(corner2.getX(), corner1.getY())),
        };
        this.size =corner2.sub(corner1);
    }

    public Vector getCorner1() {
        return corner1;
    }

    public Vector getCorner2() {
        return corner2;
    }

    public Vector getSize() {
        return size;
    }

    public boolean contains(Vector p) {
        return check(corner1.getX(), corner1.getY(),
                corner1.getX(), corner2.getY(),
                corner2.getX(), corner2.getY(),
                corner2.getX(), corner1.getY(),
                p.getX(), p.getY());
    }

    double area(double x1, double y1, double x2, double y2,
                double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) +
                x3 * (y1 - y2)) / 2.0);
    }


    boolean check(double x1, double y1, double x2, double y2, double x3,
                  double y3, double x4, double y4, double x, double y)
    {
        /* Calculate area of rectangle ABCD */
        double A = area(x1, y1, x2, y2, x3, y3) +
                area(x1, y1, x4, y4, x3, y3);

        /* Calculate area of triangle PAB */
        double A1 = area(x, y, x1, y1, x2, y2);

        /* Calculate area of triangle PBC */
        double A2 = area(x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PCD */
        double A3 = area(x, y, x3, y3, x4, y4);

        /* Calculate area of triangle PAD */
        double A4 = area(x, y, x1, y1, x4, y4);

    /* Check if sum of A1, A2, A3 and A4
       is same as A */
        return (Math.abs(A - (A1 + A2 + A3 + A4)) < Constants.EPSILON);
    }

    public boolean touches(Line l) {
        if (contains(l.getEndPoint()) || contains(l.getStartPoint())) return true;
        for (Line line : lines) {
            if (l.getIntersection(line).isValid()) {
                return true;
            }
        }
        return false;
    }

    public Line[] getLines() {
        return lines;
    }
}
