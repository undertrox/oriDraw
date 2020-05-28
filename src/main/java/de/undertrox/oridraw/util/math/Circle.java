package de.undertrox.oridraw.util.math;

public class Circle {
    Vector center;
    double radius;

    public Circle(Vector center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the Vector at the angle angle (clockwise) of the boundary of the circle
     * getPointAt(0) = the point with the highest y coordinate on the circle
     * @param angle: angle in radians
     * @return point at the angle
     */
    public Vector getPointAt(double angle){
        return new Vector(center.getX() + radius * Math.sin(angle), center.getY() + radius * Math.cos(angle));
    }

    /**
     * Returns an array of the 16 Points on 22.5 degree increments on the circle.
     * @return array of the 8 Points on 22.5 degree increments on the circle.
     */
    public Vector[] get225Points() {
        Vector[] points = new Vector[16];
        for (int i = 0; i < 16; i++) {
            points[i] = getPointAt(Math.toRadians(i*22.5));
        }
        return points;
    }

    public Vector getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
