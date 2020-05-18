package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

public class OriLine {

    private Line line;
    private Type type;
    private boolean animating;

    /**
     * Creates a new OriLine starting in start and ending in end
     *
     * @param start : starting point of the line
     * @param end   : ending Point of the line
     * @param type  : Type of the OriLine
     */
    public OriLine(OriPoint start, OriPoint end, Type type) {
        this.line = new Line(start, end);
        this.type = type;
        start.addLine(this);
        end.addLine(this);
    }

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    /**
     * @return OriLine Type of this OriLine
     */
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OriLine(" + line +
                ",type=" + type +
                ')';
    }

    /**
     * Equals Method for OriLine. The type attribute is ignored on purpose
     *
     * @param obj: object to compare
     * @return whether the lines equal (Line type is ignored)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OriLine) {
            OriLine c = (OriLine) obj;
            return getLine().equals(c.getLine());
        }
        return false;
    }

    /**
     * Exact equals method that doesnt ignore the Type
     *
     * @param obj: object to compare
     * @return whethere the oriLines are equal
     */
    public boolean equalsExact(Object obj) {
        if (obj instanceof OriLine) {
            OriLine c = (OriLine) obj;
            return equals(c) && getType().equals(c.getType());
        }
        return false;
    }

    public Line getLine() {
        return line;
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
}
