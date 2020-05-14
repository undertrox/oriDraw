package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.List;

public class MouseHandler implements MouseHandlerInterface {

    private static final double CANVAS_HEIGHT_CORRECTION = 28; // px
    private CreasePattern cp;
    private CreasePatternSelection selection;
    private Transform cpTransform;
    private CreasePatternTool activeTool;

    public MouseHandler(CreasePattern cp, CreasePatternSelection selection, Transform cpTransform) {
        this.cpTransform = cpTransform;
        this.cp = cp;
        this.selection = selection;
    }

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

    public void setActiveTool(CreasePatternTool activeTool) {
        this.activeTool = activeTool;
    }

    public void onMove(MouseEvent e) {
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        activeTool.onMove(e);
    }

    public void onMouseCoordsChange(Vector mouseCoords) {
        if (selection.getMode().selectPoints()) {
            Vector nearestPoint = findNearestPoint(mouseCoords, cp.getPoints());
            if (mouseCoords.distanceSquared(nearestPoint)
                    < Math.pow(2 * RenderSettings.getPointSideLength() / cpTransform.getScale(), 2)) {
                selection.singleToBeSelected(nearestPoint);
            } else {
                selection.clearToBeSelected();
            }
        }
    }

    public void onClick(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            activeTool.onClick(e);
        }
    }

    public void onDrag(MouseEvent e) {
        activeTool.onDrag(e);
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

    public void onScroll(ScrollEvent e) {
        activeTool.update();
        Vector mouseCoords = normalizeMouseCoords(new Vector(e.getX(), e.getY()), cpTransform);
        onMouseCoordsChange(mouseCoords);
        cpTransform.zoom(e.getDeltaY() / (e.getMultiplierY() * 10));
    }

}
