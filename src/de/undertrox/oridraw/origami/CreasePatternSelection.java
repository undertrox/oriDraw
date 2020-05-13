package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;

public class CreasePatternSelection {
    CreasePattern cp;

    UniqueItemList<Line> selectedLines;
    UniqueItemList<Vector> selectedPoints;
    UniqueItemList<Line> toBeSelectedLines;
    UniqueItemList<Vector> toBeSelectedPoints;

    public CreasePatternSelection(CreasePattern cp) {
        this.cp = cp;
        selectedLines = new UniqueItemList<>();
        selectedPoints = new UniqueItemList<>();
        toBeSelectedLines = new UniqueItemList<>();
        toBeSelectedPoints = new UniqueItemList<>();
    }

    public UniqueItemList<Line> getSelectedLines() {
        return selectedLines;
    }

    public UniqueItemList<Vector> getSelectedPoints() {
        return selectedPoints;
    }

    public UniqueItemList<Line> getToBeSelectedLines() {
        return toBeSelectedLines;
    }

    public UniqueItemList<Vector> getToBeSelectedPoints() {
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
    public void selectSingle(Vector p) {
        selectedPoints.clear();
        select(p);
    }

    /**
     * Adds p to the current Selection
     *
     * @param p: Point to add to the selection
     */
    public void select(Vector p) {
        toBeSelectedPoints.remove(p);
        selectedPoints.push(p);
    }

    /**
     * Adds p to the to be selected list
     *
     * @param p: point to be added to the list
     */
    public void addToBeSelected(Vector p) {
        toBeSelectedPoints.add(p);
    }

    /**
     * makes p the only point in toBeSelectedPoints
     *
     * @param p: point to be added to the list
     */
    public void singleToBeSelected(Vector p) {
        toBeSelectedPoints.clear();
        addToBeSelected(p);
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
        for (Vector point : toBeSelectedPoints) {
            if (selectedPoints.contains(point)) {
                selectedPoints.remove(point);
            } else {
                selectedPoints.add(point);
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
}
