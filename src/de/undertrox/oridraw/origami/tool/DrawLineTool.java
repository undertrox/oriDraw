package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.ui.MouseHandler;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.tool.DrawLineToolRenderer;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseEvent;

public class DrawLineTool extends CreasePatternTool {
    DrawLineToolRenderer renderer;
    Vector point0, point1;
    MouseEvent lastMoveEvent;

    public DrawLineTool(CreasePattern cp, CreasePatternSelection selection, Transform cpTransform) {
        super(cp, selection, cpTransform);
    }

    @Override
    protected void enable() {
        getSelection().setMode(CreasePatternSelection.Mode.POINT);
    }

    @Override
    protected void disable() {

    }

    protected void clearSelection() {
        getSelection().clearSelection();
        point0 = null;
        point1 = null;
    }

    @Override
    public ToolRenderer<DrawLineTool> getRenderer() {
        if (renderer == null) {
            renderer = new DrawLineToolRenderer(getTransform(), this);
        }
        return renderer;
    }

    @Override
    public void update() {
        if (lastMoveEvent != null) {
            onMove(lastMoveEvent);
        }
    }

    @Override
    public void onClick(MouseEvent e) {
        getCp().addPoint(getNextPoint());
        // If this is the first Point
        if (point0 == null) {
            point0 = getNextPoint();
            getSelection().select(point0);
            getCp().addPoint(point0);
        } else {
            point1 = getNextPoint();
            getCp().addCrease(point0, point1,
                    Crease.Type.MOUNTAIN);
            clearSelection();
        }
    }

    public Vector getNextPoint() {
        if (!getSelection().getToBeSelectedPoints().isEmpty()) {
            return getSelection().getToBeSelectedPoints().get(0);
        } else {
            return getCurrentMousePos();
        }
    }

    @Override
    public void onDrag(MouseEvent e) {
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint();
    }

    @Override
    public void onMove(MouseEvent e) {
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint();
        lastMoveEvent = e;
    }

    public Vector getPoint0() {
        return point0;
    }

    public Vector getPoint1() {
        return point1;
    }
}
