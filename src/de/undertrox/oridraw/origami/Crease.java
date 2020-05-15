package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

public class Crease {

    private Line line;
    private Type type;
    private boolean animating;

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public enum Type {
        EDGE, MOUNTAIN, VALLEY, AUX;

        public Type flip() {
            switch (this) {
                case VALLEY:
                    return MOUNTAIN;
                case MOUNTAIN:
                    return VALLEY;
                default:
                    return this;
            }
        }
    }

    /**
     * Creates a new Crease starting in start and ending in end
     *
     * @param start : starting point of the line
     * @param end   : ending Point of the line
     * @param type  : Type of the Crease
     */
    public Crease(Vector start, Vector end, Type type) {
        this.line = new Line(start, end);
        this.type = type;
    }

    /**
     * @return Crease Type of this Crease
     */
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Crease(" + line +
                ",type=" + type +
                ')';
    }

    /**
     * Equals Method for Crease. The type attribute is ignored on purpose
     *
     * @param obj: object to compare
     * @return whether the lines equal (Line type is ignored)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crease) {
            Crease c = (Crease) obj;
            return getLine().equals(c.getLine());
        }
        return false;
    }

    /**
     * Exact equals method that doesnt ignore the Type
     *
     * @param obj: object to compare
     * @return whethere the creases are equal
     */
    public boolean equalsExact(Object obj) {
        if (obj instanceof Crease) {
            Crease c = (Crease) obj;
            return equals(c) && getType().equals(c.getType());
        }
        return false;
    }

    public Line getLine() {
        return line;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
