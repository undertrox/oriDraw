package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

public class Rectangle {
    private Vector corner1;
    private Vector corner2;
    private Vector size;
    private Line[] lines;

    public Rectangle(Vector corner1, Vector corner2) {
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
        this.corner1 = corner1;
        this.corner2 = corner2;
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

    public boolean overlaps(Rectangle r) {
        if (getCorner1().getX() >= r.getCorner2().getX() || r.getCorner1().getX() >= getCorner2().getX()) {
            return false;
        }
        if (getCorner1().getY() >= r.getCorner2().getY() || r.getCorner1().getY() >= getCorner2().getY()) {
            return false;
        }
        return true;
    }

    private double area(double x1, double y1, double x2, double y2,
                double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) +
                x3 * (y1 - y2)) / 2.0);
    }


    private boolean check(double x1, double y1, double x2, double y2, double x3,
                  double y3, double x4, double y4, double x, double y)
    {
        /* Calculate area of rectangle ABCD */
        double a = area(x1, y1, x2, y2, x3, y3) +
                area(x1, y1, x4, y4, x3, y3);

        /* Calculate area of triangle PAB */
        double a1 = area(x, y, x1, y1, x2, y2);

        /* Calculate area of triangle PBC */
        double a2 = area(x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PCD */
        double a3 = area(x, y, x3, y3, x4, y4);

        /* Calculate area of triangle PAD */
        double a4 = area(x, y, x1, y1, x4, y4);

    /* Check if sum of A1, A2, A3 and A4
       is same as A */
        return (Math.abs(a - (a1 + a2 + a3 + a4)) < Constants.EPSILON);
    }

    public boolean overlaps(Line l) {
        if (contains(l.getEnd()) || contains(l.getStart())) return true;
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
