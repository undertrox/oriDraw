package de.undertrox.oridraw.origami.tool.drawline;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.TypedCreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.tool.DrawLineToolRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.Circle;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DrawLineTool extends TypedCreasePatternTool {
    private OriPoint point0;
    private List<OriPoint> intersections;
    private OriPoint point1;

    public DrawLineTool(CreasePatternTab tab, OriLine.Type type,
                        CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        super(tab, type, factory);
        intersections = new ArrayList<>();
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

    public DrawLineToolSettings getSettings() {
        return (DrawLineToolSettings) getFactory().getSettings();
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
                point1 = getNextPoint(getSettings().snapTo225());
                if (!getSettings().flipTypeAtIntersection()) {
                    getCp().addOriLine(point0, point1, getType());
                } else {
                    getCp().addOriLines(splitLineAtIntersections());
                }
                if (!getSettings().continueLine()) {
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

    private List<OriLine> splitLineAtIntersections() {
        OriPoint last = point0;
        OriLine.Type type = getType();
        List<OriLine> lines = new ArrayList<>();
        for (OriPoint intersection : intersections) {
            if (!intersection.isValid()) continue;
            lines.add(new OriLine(last, intersection, type));
            last = intersection;
            type = type.flip();
        }
        lines.add(new OriLine(last, point1, type));
        return lines;
    }

    public void reset() {
        clearSelection();
        if (intersections != null) {
            intersections.clear();
        }
    }

    public OriPoint getNextPoint(boolean lockTo225) {
        if (!lockTo225 && !getSelection().getToBeSelectedPoints().isEmpty()) {
            return new OriPoint(getSelection().getToBeSelectedPoints().get(0));
        } else if (lockTo225 && point0 != null) {
            double dist = point0.distance(getCurrentMousePos());
            Vector[] inc = new Circle(point0, dist).get225Points();
            Vector nearest = getCurrentMousePos().nearestOf(inc);
            if (!getSelection().getToBeSelectedLines().isEmpty() && !point0.getLines().contains(getSelection().getToBeSelectedLines().get(0))) {
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
        getIntersections().clear();
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        point1 = getNextPoint(getSettings().snapTo225());
        if (getSettings().snapTo225() && point0 != null) {
            if (getSelection().getMode() == CreasePatternSelection.Mode.POINT) {
                getSelection().setMode(CreasePatternSelection.Mode.LINE);
            }
        } else if (getSelection().getMode() == CreasePatternSelection.Mode.LINE){
            getSelection().setMode(CreasePatternSelection.Mode.POINT);
        }
        if (getSettings().flipTypeAtIntersection() && point0 != null && point1 != null) {
            intersections = getCp().getLineIntersections(new Line(point0, point1));
            intersections.sort(Comparator.comparingDouble(p -> p.distanceSquared(point0)));
        }
    }

    public Vector getPoint0() {
        return point0;
    }

    public Vector getPoint1() {
        return point1;
    }

    public List<OriPoint> getIntersections() {
        return intersections;
    }
}
