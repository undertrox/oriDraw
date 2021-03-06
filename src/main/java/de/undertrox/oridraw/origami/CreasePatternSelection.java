package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;

public class CreasePatternSelection {
    CreasePattern cp;

    UniqueItemList<OriLine> selectedLines;
    UniqueItemList<OriPoint> selectedPoints;
    UniqueItemList<OriLine> toBeSelectedLines;
    UniqueItemList<OriPoint> toBeSelectedPoints;

    Mode mode;

    public CreasePatternSelection(CreasePattern cp) {
        this.cp = cp;
        selectedLines = new UniqueItemList<>();
        selectedPoints = new UniqueItemList<>();
        toBeSelectedLines = new UniqueItemList<>();
        toBeSelectedPoints = new UniqueItemList<>();
        mode = Mode.LINE;
    }

    public UniqueItemList<OriLine> getSelectedLines() {
        return selectedLines;
    }

    public UniqueItemList<OriPoint> getSelectedPoints() {
        return selectedPoints;
    }

    public UniqueItemList<OriLine> getToBeSelectedLines() {
        return toBeSelectedLines;
    }

    public UniqueItemList<OriPoint> getToBeSelectedPoints() {
        return toBeSelectedPoints;
    }

    public void clear() {
        clearSelection();
        clearToBeSelected();
    }

    public void clearSelection() {
        selectedPoints.clear();
        selectedLines.clear();
    }

    public void clearToBeSelected() {
        toBeSelectedPoints.clear();
        toBeSelectedLines.clear();
    }

    /**
     * Selects a single point
     *
     * @param p: Point to be selected
     */
    public void selectSingle(OriPoint p) {
        if (p == null) return;
        selectedPoints.clear();
        select(p);
    }

    /**
     * Adds p to the current Selection
     *
     * @param p: Point to add to the selection
     */
    public void select(OriPoint p) {
        if (p == null) return;
        toBeSelectedPoints.remove(p);
        selectedPoints.push(p);
    }

    public void selectToBeSelectedLines() {
        selectedLines.addAll(toBeSelectedLines);
        toBeSelectedLines.clear();
    }

    /**
     * Adds p to the to be selected list
     *
     * @param p: point to be added to the list
     */
    public void addToBeSelected(OriPoint p) {
        if (p == null) return;
        toBeSelectedPoints.add(p);
    }

    /**
     * makes p the only point in toBeSelectedPoints
     *
     * @param p: point to be added to the list
     */
    public void singleToBeSelected(OriPoint p) {
        if (p == null) return;
        toBeSelectedPoints.clear();
        addToBeSelected(p);
    }

    public void singleToBeSelected(OriLine l) {
        if (l == null) return;
        toBeSelectedLines.clear();
        addToBeSelected(l);
    }

    public void addToBeSelected(OriLine l) {
        if (l == null) return;
        toBeSelectedLines.add(l);
    }

    /**
     * Adds all points in toBeSelectedPoints to SelectedPoints
     */
    public void selectToBeSelectedPoints() {
        toBeSelectedPoints.forEach(selectedPoints::push);
        toBeSelectedPoints.clear();
    }

    /**
     * Toggles the Selection status on all points in toBeSelected
     */
    public void toggleToBeSelectedPoints() {
        for (OriPoint point : toBeSelectedPoints) {
            if (selectedPoints.contains(point)) {
                selectedPoints.remove(point);
            } else {
                selectedPoints.add(point);
            }
        }
    }

    public void toggleToBeSelectedLines() {
        for (OriLine line : toBeSelectedLines) {
            if (selectedLines.contains(line)) {
                selectedLines.remove(line);
            } else {
                selectedLines.add(line);
            }
        }
    }

    /**
     * selects only the points that are in toBeSelected
     */
    public void selectOnlyToBeSelectedPoints() {
        clearSelection();
        selectToBeSelectedPoints();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        clearToBeSelected();
    }

    public void clearToBeSelectedLines() {
        toBeSelectedLines.clear();
    }

    public void clearToBeSelectedPoints() {
        toBeSelectedPoints.clear();
    }

    public enum Mode {
        LINE, POINT, LINE_AND_POINT;

        public boolean selectPoints() {
            return this == POINT || this == LINE_AND_POINT;
        }

        public boolean selectLines() {
            return this == LINE_AND_POINT || this == LINE;
        }
    }
}
