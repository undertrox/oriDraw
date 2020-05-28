package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.tool.DrawLineToolRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.Circle;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DrawLineTool extends TypedCreasePatternTool {
    OriPoint point0;
    OriPoint point1;

    public DrawLineTool(CreasePatternTab tab, OriLine.Type type,
                        CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        super(tab, type, factory);
    }

    @Override
    protected void enable() {
        getSelection().setMode(CreasePatternSelection.Mode.POINT);
    }

    @Override
    protected void disable() {
        // Nothing to do
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
    public void onClick(MouseEvent e) {
        super.onClick(e);
        if (e.isConsumed()) return;
        if (e.getButton() == MouseButton.PRIMARY) {
            // If this is the first Point
            if (point0 == null) {
                point0 = getNextPoint(false);
                getSelection().select(point0);
            } else {
                point1 = getNextPoint(e.isControlDown());
                getCp().addOriLine(point0, point1, getType());
                if (!e.isShiftDown()) {
                    clearSelection();
                } else {
                    getSelection().clearSelection();
                    point0 = point1;
                    point1 = null;
                    getSelection().select(point0);
                }
            }
        }
    }

    public void reset() {
        clearSelection();
    }


    public OriPoint getNextPoint(boolean lockTo225) {
        if (!lockTo225 && !getSelection().getToBeSelectedPoints().isEmpty()) {
            return new OriPoint(getSelection().getToBeSelectedPoints().get(0));
        } else if (lockTo225 && point0 != null) {
            // this should ideally be somewhere else
            double dist = point0.distance(getCurrentMousePos());
            Vector[] inc = new Circle(point0, dist).get225Points();
            Vector nearest = getCurrentMousePos().nearestOf(inc);
            if (!getSelection().getToBeSelectedLines().isEmpty()) {
                Line l = getSelection().getToBeSelectedLines().get(0);
                Line newL = new Line(point0, nearest).extendUntilIntersection(l);
                return new OriPoint(newL.getEndPoint());
            }
            return new OriPoint(getCurrentMousePos().nearestOf(inc));
        } else {
            return new OriPoint(getCurrentMousePos());
        }
    }


    @Override
    public void onMove(MouseEvent e) {
        super.onMove(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint(e.isControlDown());
        if (e.isControlDown() && point0 != null) {
            if (getSelection().getMode() == CreasePatternSelection.Mode.POINT) {
                getSelection().setMode(CreasePatternSelection.Mode.LINE);
            }
        } else if (getSelection().getMode() == CreasePatternSelection.Mode.LINE){
            getSelection().setMode(CreasePatternSelection.Mode.POINT);
        }
    }

    public Vector getPoint0() {
        return point0;
    }

    public Vector getPoint1() {
        return point1;
    }
}
