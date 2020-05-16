package de.undertrox.oridraw.origami;

import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;

public class Grid extends CreaseCollection {
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
        Vector topLeftCorner = center.sub(new Vector(gridSize / 2));
        Vector bottomRightCorner = center.add(new Vector(gridSize / 2));
        for (double x = topLeftCorner.getX(); x <= bottomRightCorner.getX(); x += getGridSquareSize()) {
            for (double y = topLeftCorner.getY(); y <= bottomRightCorner.getY(); y += getGridSquareSize()) {
                getPoints().push(new Vector(x, y));
                if (y > topLeftCorner.getY()) {
                    creases.add(new Crease(new Vector(x, y), new Vector(x, y - getGridSquareSize()),
                            Crease.Type.AUX));
                }
                if (x > topLeftCorner.getX()) {
                    creases.add(new Crease(new Vector(x, y), new Vector(x - getGridSquareSize(), y),
                            Crease.Type.AUX));
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

    public UniqueItemList<Crease> getCreases() {
        return creases;
    }

    public UniqueItemList<Vector> getPoints() {
        return points;
    }
}
