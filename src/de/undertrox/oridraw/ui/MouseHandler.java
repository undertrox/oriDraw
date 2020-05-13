package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MouseHandler {

    private static final double CANVAS_HEIGHT_CORRECTION = 28; // px

    /**
     * Maps Mouse coordinates from Canvas to Crease Pattern Coordinates
     *
     * @param mousePos:  Mouse Position
     * @param transform: Transform of the Crease pattern
     * @return Mouse Coordinates mapped to the Crease Pattern
     */
    public static Vector normalizeMouseCoords(Vector mousePos, Transform transform) {
        return transform.applyInverted(new Vector(mousePos.getX(), mousePos.getY() - CANVAS_HEIGHT_CORRECTION));
    }

    private CreasePattern cp;
    private CreasePatternSelection selection;
    private Transform cpTransform;

    public MouseHandler(CreasePattern cp, CreasePatternSelection selection, Transform cpTransform) {
        this.cpTransform = cpTransform;
        this.cp = cp;
        this.selection = selection;
    }

    public void handleMouseMove(MouseEvent e) {
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        Vector nearestPoint = findNearestPoint(mouseCoords, cp.getPoints());
        if (mouseCoords.distanceSquared(nearestPoint) < Math.pow(2 * RenderSettings.getPointSideLength(), 2)) {
            selection.singleToBeSelected(nearestPoint);
        } else {
            selection.clearToBeSelected();
        }
    }

    public void handleMouseClick(MouseEvent e) {
        // TODO: this is temporary
        selection.toggleToBeSelectedPoints();
    }

    public Vector findNearestPoint(Vector p, List<Vector> points) {
        Vector nearest = points.get(0);
        double smallesSqrDist = p.distanceSquared(nearest);
        for (Vector point : points) {
            double sqrDist = p.distanceSquared(point);
            if (sqrDist < smallesSqrDist) {
                smallesSqrDist = sqrDist;
                nearest = point;
            }
        }
        return nearest;
    }
}
