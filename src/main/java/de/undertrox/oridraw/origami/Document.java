package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.actions.CareTaker;
import de.undertrox.oridraw.util.actions.Mementos;
import de.undertrox.oridraw.util.actions.Originator;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.print.Doc;

public class Document {
    private Logger logger = LogManager.getLogger(Document.class);
    private CreasePattern cp;
    private Grid grid;
    private boolean showGrid;
    private CreasePatternSelection selection;
    private String title;
    private double paperSize;
    private boolean hasUnsavedChanges;



    public Document(Document document){

        this.cp = new CreasePattern(document.getCp()); // TODO : this may be wrong
        this.grid = new Grid(document.getGrid());
        this.showGrid = document.showGrid;
        this.selection = new CreasePatternSelection(this.cp);
        this.title = document.getTitle();
        this.paperSize = document.getPaperSize();
        this.hasUnsavedChanges = document.hasUnsavedChanges();
    }

    public Document(String title, double paperSize, int gridDivisions) {

        this.paperSize = paperSize;
        this.cp = new CreasePattern();
        this.grid = new Grid(gridDivisions, paperSize, paperSize, Vector.ORIGIN);
        showGrid = true;

        this.selection = new CreasePatternSelection(cp);
        this.title = title;
        hasUnsavedChanges = false;
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

    public double getPaperSize() {
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
