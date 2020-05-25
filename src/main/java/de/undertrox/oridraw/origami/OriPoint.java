package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

public class OriPoint extends Vector {
    private UniqueItemList<OriLine> lines;

    public OriPoint(double x, double y) {
        super(x, y);
        lines = new UniqueItemList<>();
    }

    public OriPoint(Vector v) {
        this(v.getX(), v.getY());
    }

    public OriPoint(double x) {
        super(x);
    }

    public UniqueItemList<OriLine> getLines() {
        return lines;
    }

    public void addLine(OriLine l) {
        lines.push(l);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
