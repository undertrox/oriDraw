package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.tool.DrawLineToolRenderer;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DrawLineTool extends TypedCreasePatternTool {
    OriPoint point0, point1;
    MouseEvent lastMoveEvent;

    public DrawLineTool(CreasePatternTab tab, OriLine.Type type) {
        super(tab, type);
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

    protected ToolRenderer<DrawLineTool> createRenderer() {
        return new DrawLineToolRenderer(getTransform(), this);
    }

    @Override
    public void update() {
        if (lastMoveEvent != null) {
            onMove(lastMoveEvent);
        }
    }

    @Override
    public void onClick(MouseEvent e) {
        super.onClick(e);
        if (e.getButton() == MouseButton.PRIMARY) {
            getCp().addPoint(getNextPoint());
            // If this is the first Point
            if (point0 == null) {
                point0 = getNextPoint();
                getSelection().select(point0);
                getCp().addPoint(point0);
            } else {
                point1 = getNextPoint();
                getCp().addCrease(point0, point1, getType());
                clearSelection();
            }
        } else if (e.getButton() == MouseButton.SECONDARY) {
            reset();
        }
    }

    public void reset() {
        point0 = null;
        point1 = null;
        getSelection().clearSelection();
    }

    public OriPoint getNextPoint() {
        if (!getSelection().getToBeSelectedPoints().isEmpty()) {
            return new OriPoint(getSelection().getToBeSelectedPoints().get(0));
        } else {
            return new OriPoint(getCurrentMousePos());
        }
    }

    @Override
    public void onDrag(MouseEvent e) {
        super.onDrag(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint();
    }

    @Override
    public void onMove(MouseEvent e) {
        super.onMove(e);
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
