package de.undertrox.oridraw.ui.handler;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.List;

public class MouseHandler implements MouseHandlerInterface {

    private static final double CANVAS_HEIGHT_CORRECTION = 28; // px
    private Document doc;
    private Transform cpTransform;
    private CreasePatternTool activeTool;

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
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        activeTool.onMove(e);
    }

    public void onMouseCoordsChange(Vector mouseCoords) {
        if (doc.getSelection().getMode().selectPoints()) {
            // Adds the nearest Point to Selection.toBeSelected
            OriPoint nearestPoint = findNearestPoint(mouseCoords, doc.getAllVisiblePoints());
            if (mouseCoords.distanceSquared(nearestPoint)
                    < Math.pow(2 * Constants.MOUSE_RANGE / cpTransform.getScale(), 2)) {
                doc.getSelection().singleToBeSelected(nearestPoint);
            } else {
                doc.getSelection().clearToBeSelected();
            }
        }
    }

    public void onClick(MouseEvent e) {
        activeTool.onClick(e);
    }

    public void onDrag(MouseEvent e) {
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

    public void onScroll(ScrollEvent e) {
        activeTool.update();
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        cpTransform.zoom(e.getDeltaY() / (e.getMultiplierY() * 10));
    }

}
