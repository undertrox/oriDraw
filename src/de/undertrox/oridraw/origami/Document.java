package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.log4j.Logger;

public class Document {
    private Logger logger = Logger.getLogger(Document.class);

    private CreasePattern cp;
    private Grid grid;
    private boolean showGrid;
    private CreasePatternSelection selection;
    private String title;
    private double paperSize;

    public Document(String title, double paperSize, int gridDivisions) {
        this.paperSize = paperSize;
        this.cp = new CreasePattern();
        this.grid = new Grid(gridDivisions, paperSize, paperSize, Vector.ORIGIN);
        showGrid = true;

        cp.createSquare(Vector.ORIGIN, paperSize);
        this.selection = new CreasePatternSelection(cp);
        this.title = title;
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

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public double getPaperSize() {
        return paperSize;
    }

    public UniqueItemList<Vector> getAllVisiblePoints() {
        if (showGrid) {
            UniqueItemList<Vector> points = new UniqueItemList<>();
            points.addAll(cp.getPoints());
            points.addAll(grid.getPoints());
            return points;
        }
        return cp.getPoints();
    }
}
