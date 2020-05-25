package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.tool.DrawLineToolRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.Vector;
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
                point0 = getNextPoint();
                getSelection().select(point0);
            } else {
                point1 = getNextPoint();
                getCp().addOriLine(point0, point1, getType());
                clearSelection();
            }
        }
    }

    public void reset() {
        clearSelection();
    }

    public OriPoint getNextPoint() {
        if (!getSelection().getToBeSelectedPoints().isEmpty()) {
            return new OriPoint(getSelection().getToBeSelectedPoints().get(0));
        } else {
            return new OriPoint(getCurrentMousePos());
        }
    }

    @Override
    public void onMove(MouseEvent e) {
        super.onMove(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint();
    }

    public Vector getPoint0() {
        return point0;
    }

    public Vector getPoint1() {
        return point1;
    }
}
