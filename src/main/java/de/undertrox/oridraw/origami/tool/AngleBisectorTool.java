package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.setting.ToolSetting;
import de.undertrox.oridraw.ui.render.tool.AngleBisectorToolRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.HesseNormalLine;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Triangle;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AngleBisectorTool extends TypedCreasePatternTool {

    private OriPoint point0;
    private OriPoint point1;
    private OriPoint point2;
    private OriLine line;

    public AngleBisectorTool(CreasePatternTab tab, OriLine.Type type,
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

    @Override
    public void reset() {
        point0 = null;
        point1 = null;
        point2 = null;
        line = null;
        getSelection().clear();
        getSelection().setMode(CreasePatternSelection.Mode.POINT);
    }

    @Override
    public void onClick(MouseEvent e) {
        super.onClick(e);
        if (e.isConsumed()) return;
        if (e.getButton() == MouseButton.PRIMARY) {
            if (point0 == null) {
                point0 = getNextPoint();
                getSelection().select(point0);
            } else if (point1 == null) {
                point1 = getNextPoint();
                getSelection().select(point1);
            } else if (point2 == null) {
                point2 = getNextPoint();
                getSelection().select(point2);
                if (point2 != null) {
                    getSelection().setMode(CreasePatternSelection.Mode.LINE);
                }
            } else if (line == null) {
                line = getNextLine();
                if (line != null) {
                    OriLine bisector = angleBisector(point0, point1, point2, line);
                    if (bisector != null) {
                        getCp().addOriLine(bisector);
                    }
                    reset();
                }
            }
        }
    }

    /**
     * Returns an OriLine that is the angle bisector of the lines point0, point1 and point1, point2 where
     * point1 is the point that the new Line will be originating from, and line is the
     * line until which the new line will go.
     *
     * @param point0:
     * @param point1:
     * @param point2:
     * @param line:
     * @return angle bisector
     */
    public OriLine angleBisector(OriPoint point0, OriPoint point1, OriPoint point2, OriLine line) {
        HesseNormalLine bound = line.getHesse();
        if (bound.squaredDistance(point1) < Constants.EPSILON
                || point0.equals(point1) || point2.equals(point1) || point1.equals(point0)) {
            return null;
        }
        HesseNormalLine bisector;
        HesseNormalLine l = new Line(point0, point1).getHesse();
        // if the three points are on one line, a triangle cant be constructed, so
        // the normal of the line is returned
        if (l.distance(point2) < Constants.EPSILON) {
            bisector = l.normal(point1);
        } else {
            Vector incenter = new Triangle(point0, point1, point2).incenter();
            bisector = new Line(point1, incenter).getHesse();
        }

        return new OriLine(point1, new OriPoint(bisector.intersect(bound)), getType());
    }

    public OriPoint getNextPoint() {
        if (!getSelection().getToBeSelectedPoints().isEmpty()) {
            return new OriPoint(getSelection().getToBeSelectedPoints().get(0));
        } else {
            return null;
        }
    }

    public OriLine getNextLine() {
        if (!getSelection().getToBeSelectedLines().isEmpty()) {
            return new OriLine(getSelection().getToBeSelectedLines().get(0));
        } else {
            return null;
        }
    }

    @Override
    protected ToolRenderer<? extends CreasePatternTool> createRenderer() {
        return new AngleBisectorToolRenderer(getTransform(), this);
    }

    @Override
    public ToolSetting[] getSettings() {
        return new ToolSetting[0];
    }


    public OriPoint getPoint0() {
        return point0;
    }

    public OriPoint getPoint1() {
        return point1;
    }

    public OriPoint getPoint2() {
        return point2;
    }

    public OriLine getLine() {
        return line;
    }


}
