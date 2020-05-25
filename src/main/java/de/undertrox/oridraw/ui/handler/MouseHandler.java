package de.undertrox.oridraw.ui.handler;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.util.UniqueItemList;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.List;

public class MouseHandler implements MouseHandlerInterface {

    private static final double CANVAS_HEIGHT_CORRECTION = 28; // px
    private Document doc;
    private Transform cpTransform;
    private CreasePatternTool activeTool;
    private Vector lastMousePos;

    private MouseEvent lastMove;

    public MouseHandler(Document doc, Transform cpTransform) {
        this.cpTransform = cpTransform;
        this.doc = doc;
    }

    /**
     * Maps Mouse coordinates from Canvas to OriLine Pattern Coordinates
     *
     * @param mousePos:  Mouse Position
     * @param transform: Transform of the OriLine pattern
     * @return Mouse Coordinates mapped to the OriLine Pattern
     */
    public static Vector normalizeMouseCoords(Vector mousePos, Transform transform) {
        return transform.applyInverted(new Vector(mousePos.getX(), mousePos.getY() - CANVAS_HEIGHT_CORRECTION));
    }

    public void setActiveTool(CreasePatternTool activeTool) {
        this.activeTool = activeTool;
    }

    public void onMove(MouseEvent e) {
        lastMove = e;
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        activeTool.onMove(e);
    }

    public void onMouseCoordsChange(Vector mouseCoords) {
        lastMousePos = mouseCoords;
        if (doc.getSelection().getMode().selectPoints()) {
            // Adds the nearest Point to Selection.toBeSelected
            OriPoint nearestPoint = findNearestPoint(mouseCoords, doc.getAllVisiblePoints(mouseCoords));
            if (mouseCoords.distanceSquared(nearestPoint)
                    < Math.pow(2 * Constants.MOUSE_RANGE / cpTransform.getScale(), 2)) {
                doc.getSelection().singleToBeSelected(nearestPoint);
            } else {
                doc.getSelection().clearToBeSelectedPoints();
            }
        }
        if (doc.getSelection().getMode().selectLines()) {
            OriLine nearestLine = findNearestLine(mouseCoords, doc.getCp().getOriLines());
            if (nearestLine == null) {
                return;
            }
            if (nearestLine.getDistance(mouseCoords) < 2 * Constants.MOUSE_RANGE / cpTransform.getScale()) {
                doc.getSelection().singleToBeSelected(nearestLine);
            } else {
                doc.getSelection().clearToBeSelectedLines();
            }
        }
    }

    private OriLine findNearestLine(Vector mouseCoords, UniqueItemList<OriLine> oriLines) {
        double smallestDist = Double.POSITIVE_INFINITY;
        OriLine nearestLine = null;
        for (OriLine oriLine : oriLines) {
            if (oriLine.lengthSquared() < Constants.EPSILON) {
                continue;
            }
            double d = oriLine.getSquaredDistance(mouseCoords);
            Vector p = oriLine.getHesse().getShadowPoint(mouseCoords);

            if (d < smallestDist && oriLine.contains(p)) {
                smallestDist = d;
                nearestLine = oriLine;
            }
        }
        return nearestLine;
    }

    public void onClick(MouseEvent e) {
        if (e.getButton() != MouseButton.MIDDLE) {
            doc.setHasUnsavedChanges(true);
        }
        activeTool.onClick(e);
        lastMousePos = null;
    }

    public void onDrag(MouseEvent e) {
        if (e.getButton() == MouseButton.MIDDLE) {
            Vector mp = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);

            if (lastMousePos != null) {
                // Move the Crease Pattern
                Vector m = cpTransform.getMove();
                Vector mn = mp.sub(lastMousePos);
                cpTransform.setMove(m.add(mn.scale(cpTransform.getScale())));
            }
            lastMousePos = new Vector(e.getX(), e.getY());
        }
        Vector mousePos = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mousePos);
        activeTool.onDrag(e);
    }

    public OriPoint findNearestPoint(Vector p, List<OriPoint> points) {
        Vector nearest = points.get(0);
        double smallesSqrDist = p.distanceSquared(nearest);
        for (Vector point : points) {
            double sqrDist = p.distanceSquared(point);
            if (sqrDist < smallesSqrDist) {
                smallesSqrDist = sqrDist;
                nearest = point;
            }
        }
        return new OriPoint(nearest);
    }

    @Override
    public void onScroll(ScrollEvent e) {
        activeTool.onMove(lastMove);

        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        cpTransform.zoom(mouseCoords, e.getDeltaY() / (e.getMultiplierY() * 10));
    }

    @Override
    public void onMouseDown(MouseEvent e) {
        activeTool.onMouseDown(e);
    }

    @Override
    public void onMouseUp(MouseEvent e) {
        activeTool.onMouseUp(e);
    }

}
