package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Grid extends OriLineCollection {
    private Logger logger = LogManager.getLogger(Grid.class);
    private int divisions;
    private double paperSize;
    private double gridSize;
    private double gridSquareSize;
    private Vector topLeftCorner;

    private Vector bottomRightCorner;

    private Vector center;

    public Grid(int divisions, double paperSize, double gridSize, Vector center) {
        super();
        this.divisions = divisions;
        this.paperSize = paperSize;
        this.gridSize = gridSize;
        this.center = center;
        updateGrid();
    }
    public Grid(Grid grid){
        super(grid);
        this.divisions = grid.divisions;
        this.paperSize = grid.paperSize;
        this.gridSize = grid.gridSize;
        this.gridSquareSize = grid.gridSquareSize;
        this.center = new Vector(grid.center);
        this.topLeftCorner = new Vector(grid.topLeftCorner);
        this.bottomRightCorner = new Vector(grid.bottomRightCorner);
    }



    public void reset() {
        getOriLines().clear();
        getPoints().clear();
    }

    public void updateGrid() {
        reset();
        updateGridSquareSize();
        updateCorners();
        logger.info("Creating Grid");
        for (double x = topLeftCorner.getX(); x <= bottomRightCorner.getX(); x += getGridSquareSize()) {
            addOriLine(new OriPoint(x, topLeftCorner.getY()), new OriPoint(x, bottomRightCorner.getY()), OriLine.Type.AUX);
            for (double y = topLeftCorner.getY(); y <= bottomRightCorner.getY(); y += getGridSquareSize()) {
                if (x == topLeftCorner.getX()) {
                    addOriLine(new OriPoint(x, y), new OriPoint(bottomRightCorner.getX(), y), OriLine.Type.AUX);
                }
            }
        }
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
        updateGrid();
    }

    public void updateCorners() {
        topLeftCorner = center.sub(new Vector(gridSize / 2));
        bottomRightCorner = center.add(new Vector(gridSize / 2));
    }

    public double getGridSquareSize() {
        return gridSquareSize;
    }

    private void updateGridSquareSize() {
        gridSquareSize = paperSize / divisions;
    }

    public int getDivisions() {
        return divisions;
    }

    public void setDivisions(int divisions) {
        this.divisions = divisions;
        updateGrid();
    }

    public double getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(double paperSize) {
        this.paperSize = paperSize;
        updateGrid();
    }

    public double getGridSize() {
        return gridSize;
    }

    public void setGridSize(double gridSize) {
        this.gridSize = gridSize;
        updateGrid();
    }

    @Override
    public UniqueItemList<OriLine> getOriLines() {
        return oriLines;
    }

    @Override
    public UniqueItemList<OriPoint> getPoints() {
        return points;
    }

    public List<OriPoint> getGridPointsNear(Vector pos, int radius) {
        double x = topLeftCorner.getX();
        double y = topLeftCorner.getY();
        ArrayList<OriPoint> points = new ArrayList<>();

        while (x < pos.getX()) {
            x += getGridSquareSize();
        }
        while (y < pos.getY()) {
            y += getGridSquareSize();
        }
        for (double gridX = x - getGridSquareSize() * radius;
             gridX < x + getGridSquareSize() * radius;
             gridX += getGridSquareSize()) {
            for (double gridY = y - getGridSquareSize() * radius;
                 gridY < y + getGridSquareSize() * radius;
                 gridY += getGridSquareSize()) {
                OriPoint p = new OriPoint(gridX, gridY);
                if (inGrid(p)) {
                    points.add(new OriPoint(gridX, gridY));
                }
            }
        }

        return points;
    }

    private boolean inGrid(OriPoint p) {
        return p.getX() >= topLeftCorner.getX() && p.getY() >= topLeftCorner.getY() &&
                p.getY() <= bottomRightCorner.getY() && p.getX() <= bottomRightCorner.getX();
    }
}
