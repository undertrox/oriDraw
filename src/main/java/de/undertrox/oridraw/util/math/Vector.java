package de.undertrox.oridraw.util.math;

import de.undertrox.oridraw.Constants;

import java.util.Objects;

/**
 * 2D Vector. Can be used as a Point or as a Vector
 */
public class Vector {
    public static final double TOLERANCE = Constants.EPSILON;
    public static final Vector ORIGIN = new Vector(0, 0);
    public static final Vector UNDEFINED = new Vector(Double.POSITIVE_INFINITY);
    private double x;
    private double y;

    /**
     * Creates a new Vector
     *
     * @param x: X-Coordinate of the Vector
     * @param y: Y-Coordinate of the Vector
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x) {
        this(x, x);
    }

    /**
     * @return x coordinate of the Vector
     */
    public double getX() {
        return x;
    }

    /**
     * @return y coordinate of the Vector
     */
    public double getY() {
        return y;
    }

    /**
     * Squared Distance between two Vectors/Points
     *
     * @param vec: Vector/Point to calculate the Squared Distance to
     * @return Squared distance to the other Vector/Point
     */
    public double distanceSquared(Vector vec) {
        return Math.pow(getX() - vec.getX(), 2) + Math.pow(getY() - vec.getY(), 2);
    }

    /**
     * Distance between two Vectors/Points
     *
     * @param vec: Vector/Point to calculate the distance to
     * @return Distance to other Vector/Point
     */
    public double distance(Vector vec) {
        return Math.sqrt(distanceSquared(vec));
    }

    /**
     * Manhattan distance |x1-x2|+|y1-y2| between this and vec
     *
     * @param vec: Vector/Point to calculate the manhattan distance to
     * @return Manhattan distance to Vector/Point
     */
    public double manhattanDistance(Vector vec) {
        return Math.abs(vec.getX() - getX()) + Math.abs(vec.getY() - getY());
    }

    /**
     * Squared Length of the Vector / Squared Distance of the Point to (0,0)
     *
     * @return Squared length of the Vector
     */
    public double lengthSquared() {
        return distanceSquared(ORIGIN);
    }

    /**
     * Length of the Vector / Distance of the Point to (0,0)
     *
     * @return length of the Vector
     */
    public double length() {
        return distance(ORIGIN);
    }

    /**
     * Adds two vectors and returns the result
     *
     * @param vec: vector to be added to this vector
     * @return new Vector which is the sum of this and vec
     */
    public Vector add(Vector vec) {
        double newX = getX() + vec.getX();
        double newY = getY() + vec.getY();
        return new Vector(newX, newY);
    }

    /**
     * @return new Vector with the signs of the coordinates inverted
     */
    public Vector invertSign() {
        return new Vector(-getX(), -getY());
    }

    /**
     * Subtracts vec from this
     *
     * @param vec: Vector to be subtracted from this vector
     * @return new Vector which is the difference between this and vec
     */
    public Vector sub(Vector vec) {
        return add(vec.invertSign());
    }

    /**
     * returns a new vector that is this vector scaled by factor
     *
     * @param factor: scaling factor
     * @return new, scaled vector
     */
    public Vector scale(double factor) {
        return new Vector(factor * getX(), factor * getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            if (Math.abs(getX() - v.getX()) > TOLERANCE) {
                return false;
            }
            return Math.abs(getY() - v.getY()) < TOLERANCE;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    /**
     * @return True if none of the Coordinates are Infinity or NaN
     */
    public boolean isValid() {
        boolean valid = Double.isFinite(getX()) && !Double.isNaN(getX());
        valid &= Double.isFinite(getY()) && !Double.isNaN(getY());
        return valid;
    }

    @Override
    public String toString() {
        return "Vector(" + getX() + "," + getY() + ")";
    }
}
