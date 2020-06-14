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
    private Vector paperSize;
    private Vector gridSize;
    private Vector gridSquareSize;
    private Vector topLeftCorner;

    private Vector bottomRightCorner;

    private Vector center;

    public Grid(int divisions, Vector paperSize, Vector gridSize, Vector center) {
        super();
        this.divisions = divisions;
        this.paperSize = paperSize;
        this.gridSize = gridSize;
        this.center = center;
        updateGrid();
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
        for (double x = topLeftCorner.getX(); x <= bottomRightCorner.getX(); x += getGridSquareSize().getX()) {
            addOriLine(new OriPoint(x, topLeftCorner.getY()), new OriPoint(x, bottomRightCorner.getY()), OriLine.Type.AUX);
            for (double y = topLeftCorner.getY(); y <= bottomRightCorner.getY(); y += getGridSquareSize().getY()) {
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
        topLeftCorner = center.sub(gridSize.scale(0.5));
        bottomRightCorner = center.add(gridSize.scale(0.5));
    }

    public Vector getGridSquareSize() {
        return gridSquareSize;
    }

    private void updateGridSquareSize() {
        gridSquareSize = paperSize.scale(1.0/divisions);
    }

    public int getDivisions() {
        return divisions;
    }

    public void setDivisions(int divisions) {
        this.divisions = divisions;
        updateGrid();
    }

    public Vector getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(Vector paperSize) {
        this.paperSize = paperSize;
        updateGrid();
    }

    public Vector getGridSize() {
        return gridSize;
    }

    public void setGridSize(Vector gridSize) {
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
            x += getGridSquareSize().getX();
        }
        while (y < pos.getY()) {
            y += getGridSquareSize().getY();
        }
        for (double gridX = x - getGridSquareSize().getX() * radius;
             gridX < x + getGridSquareSize().getX() * radius;
             gridX += getGridSquareSize().getX()) {
            for (double gridY = y - getGridSquareSize().getY() * radius;
                 gridY < y + getGridSquareSize().getY() * radius;
                 gridY += getGridSquareSize().getY()) {
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
