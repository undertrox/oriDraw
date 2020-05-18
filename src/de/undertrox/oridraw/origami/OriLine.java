package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

public class OriLine extends Line {

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
        super(start, end);
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
        return "OriLine(" + super.toString() +
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
            return super.equals(c.asLine());
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

    public Line asLine() {
        return this;
    }

    public enum Type {
        EDGE, MOUNTAIN, VALLEY, AUX, UNKNOWN;

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

        public int toCpId() {
            switch (this) {
                case MOUNTAIN:
                    return 2;
                case VALLEY:
                    return 3;
                case EDGE:
                    return 1;
                case AUX:
                    return 4;
                default:
                    return 0;
            }
        }

        public static Type fromCpId(int cpId) {
            switch (cpId) {
                case 1:
                    return EDGE;
                case 2:
                    return MOUNTAIN;
                case 3:
                    return VALLEY;
                case 4:
                    return AUX;
                default:
                    return UNKNOWN;
            }
        }
    }

    public OriPoint getStartPoint() {
        return (OriPoint) super.getStartPoint();
    }

    public OriPoint getEndPoint() {
        return (OriPoint) super.getEndPoint();
    }
}
