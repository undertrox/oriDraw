package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;
import org.apache.log4j.Logger;

public class Grid extends CreaseCollection {
    private Logger logger = Logger.getLogger(Grid.class);
    private int divisions;
    private double paperSize;
    private double gridSize;
    private double gridSquareSize;

    private Vector center;

    public Grid(int divisions, double paperSize, double gridSize, Vector center) {
        super();
        this.divisions = divisions;
        this.paperSize = paperSize;
        this.gridSize = gridSize;
        this.center = center;
        updateGridSquareSize();
        logger.info("Creating Grid");
        Vector topLeftCorner = center.sub(new Vector(gridSize / 2));
        Vector bottomRightCorner = center.add(new Vector(gridSize / 2));
        for (double x = topLeftCorner.getX(); x <= bottomRightCorner.getX(); x += getGridSquareSize()) {
            addCrease(new OriPoint(x, topLeftCorner.getY()), new OriPoint(x, bottomRightCorner.getY()), OriLine.Type.AUX);
            for (double y = topLeftCorner.getY(); y <= bottomRightCorner.getY(); y += getGridSquareSize()) {
                getPoints().push(new OriPoint(x, y));
                if (x == topLeftCorner.getX()) {
                    addCrease(new OriPoint(x, y), new OriPoint(bottomRightCorner.getX(), y), OriLine.Type.AUX);
                }
            }
        }
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
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
        updateGridSquareSize();
    }

    public double getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(double paperSize) {
        this.paperSize = paperSize;
        updateGridSquareSize();
    }

    public double getGridSize() {
        return gridSize;
    }

    public void setGridSize(double gridSize) {
        this.gridSize = gridSize;
    }

    public UniqueItemList<OriLine> getOriLines() {
        return oriLines;
    }

    public UniqueItemList<OriPoint> getPoints() {
        return points;
    }
}
