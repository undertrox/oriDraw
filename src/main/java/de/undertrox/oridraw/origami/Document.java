package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

public class Document {

    private CreasePattern cp;
    private Grid grid;
    private boolean showGrid;
    private CreasePatternSelection selection;
    private String title;
    private Vector paperSize;
    private Vector center;
    private boolean hasUnsavedChanges;

    public Document(String title, Vector paperSize, Vector center, int gridDivisions) {
        this.paperSize = paperSize;
        this.cp = new CreasePattern();
        this.center = center;
        this.grid = new Grid(gridDivisions, paperSize, paperSize, center);
        showGrid = true;

        this.selection = new CreasePatternSelection(cp);
        this.title = title;
        hasUnsavedChanges = false;
    }

    public Document(String title, Vector paperSize, int gridDivisions) {
        this(title, paperSize, Vector.ORIGIN, gridDivisions);
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.hasUnsavedChanges = hasUnsavedChanges;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CreasePattern getCp() {
        return cp;
    }

    public CreasePatternSelection getSelection() {
        return selection;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean showGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public Vector getPaperSize() {
        return paperSize;
    }

    public UniqueItemList<OriPoint> getAllVisiblePoints(Vector mousePos) {
        if (showGrid) {
            UniqueItemList<OriPoint> points = new UniqueItemList<>();
            points.addAll(cp.getPoints());
            points.addAll(grid.getGridPointsNear(mousePos, 3));
            return points;
        }
        return cp.getPoints();
    }
}
