package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;

public class OriLine extends Line {

    private Type type;

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
    }

    public OriLine(OriLine oriLine) {
        this(new OriPoint(oriLine.getStart()), new OriPoint(oriLine.getEnd()), oriLine.getType());
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

    @Override
    public int hashCode() {
        return super.hashCode();
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

    /**
     * Extends the Line in both directions as far as possible so that
     * Crease pattern integrity can still be guaranteed. That means that
     * it will stop at any point where another Line intersects this one.
     * @param linesToRemove Output parameter: List of Lines to remove. Every Line that would
     *                      become redundant with the new extended line
     *                      will be placed in here
     * @return Extended line
     */
    public OriLine extend(UniqueItemList<OriLine> linesToRemove) {
        return extendEndPoint(linesToRemove).extendStartPoint(linesToRemove);
    }

    private OriLine extendStartPoint(UniqueItemList<OriLine> linesToRemove) {
        OriLine current = this;
        OriPoint start = new OriPoint(getStart());
        OriPoint end = new OriPoint(getEnd());
        OriLine next = current;
        int iterations = 0;
        while (next.getStart().getLines().size() == 2 && iterations<100) {
            iterations++;
            if (next.getStart().getLines().get(0).equals(current)) {
                next = current.getStart().getLines().get(1);
            } else {
                next = current.getStart().getLines().get(0);
            }
            if (next.getHesse().parallel(current.getHesse()) && next.getType() == current.getType()) {
                linesToRemove.add(current);
                current = next;
                if (!start.equals(current.getEnd())) {
                    start = current.getStart();
                } else {
                    start = current.getEnd();
                }
            } else {
                break;
            }
        }
        if (!start.equals(getStart())) linesToRemove.add(current);
        return new OriLine( new OriPoint(start), end, getType());
    }

    private OriLine extendEndPoint(UniqueItemList<OriLine> linesToRemove) {
        OriLine current = this;
        OriPoint start = new OriPoint(getStart());
        OriPoint end = new OriPoint(getEnd());
        OriLine next=current;
        int iterations = 0;
        while (next.getEnd().getLines().size() == 2 && iterations < 100) {
            iterations++;
            if (next.getEnd().getLines().get(0).equals(current)) {
                next = current.getEnd().getLines().get(1);
            } else {
                next = current.getEnd().getLines().get(0);
            }
            if (next.getHesse().parallel(current.getHesse()) && next.getType() == current.getType()) {
                linesToRemove.add(current);
                current = next;
                if (!end.equals(current.getStart())) {
                    end = current.getEnd();
                } else {
                    end = current.getStart();
                }
            } else {
                break;
            }
        }
        if (!end.equals(getEnd())) linesToRemove.add(current);
        return new OriLine(start, new OriPoint(end), getType());
    }

    /**
     * Merges two Lines that have a common endpoint. If the lines
     * do not have a common endpoint, null will be returned
     * @param line1 first Line
     * @param line2 second Line
     * @return Merged Line, type will be copied from the first Line
     */
    public static OriLine merge(OriLine line1, OriLine line2) {
        OriPoint start=null;
        OriPoint end=null;
        Type type = line1.getType();
        if (line1.getStart().equals(line2.getStart())) {
            start = line1.getEnd();
            end = line2.getEnd();
        } else if (line1.getStart().equals(line2.getEnd())) {
            start = line1.getEnd();
            end = line2.getStart();
        } else if (line1.getEnd().equals(line2.getStart())) {
            start = line1.getStart();
            end = line2.getEnd();
        } else if (line1.getEnd().equals(line2.getEnd())) {
            start = line1.getStart();
            end = line2.getStart();
        }
        if (start != null && end != null) {
            return new OriLine(start, end, type);
        }
        return null;
    }

    @Override
    public OriPoint getStart() {
        return (OriPoint) super.getStart();
    }

    @Override
    public OriPoint getEnd() {
        return (OriPoint) super.getEnd();
    }
}
