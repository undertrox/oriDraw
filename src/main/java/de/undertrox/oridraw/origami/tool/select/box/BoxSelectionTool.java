package de.undertrox.oridraw.origami.tool.select.box;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.select.SelectionTool;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.ui.render.tool.BoxSelectionRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.util.math.Rectangle;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BoxSelectionTool extends SelectionTool {
    private Rectangle rect;
    private Vector startPos;


    public BoxSelectionTool(CreasePatternTab tab, CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        super(tab, factory);
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    protected void enable() {

    }

    @Override
    protected void disable() {

    }

    @Override
    public void reset() {
        getSelection().getToBeSelectedLines().clear();
        getSelection().getToBeSelectedPoints().clear();
        rect = null;
        startPos = null;
    }

    @Override
    public void onMouseDown(MouseEvent e) {
        super.onMouseDown(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        if (e.getButton() == MouseButton.PRIMARY || e.getButton() == MouseButton.SECONDARY) {
            if (!getAddToSelection()) {
                getSelection().clear();
            }
            startPos = getCurrentMousePos();
            rect = new Rectangle(getCurrentMousePos(), getCurrentMousePos());
        }
    }

    @Override
    public void onMouseUp(MouseEvent e) {
        super.onMouseUp(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        if (e.getButton() == MouseButton.SECONDARY) {
            getSelection().getSelectedLines().removeAll(getSelection().getToBeSelectedLines());
            getSelection().getSelectedPoints().removeAll(getSelection().getToBeSelectedPoints());
            getSelection().clearToBeSelected();
        } else if (e.getButton() == MouseButton.PRIMARY) {
            getSelection().selectToBeSelectedPoints();
            getSelection().selectToBeSelectedLines();
        }
        reset();
    }

    @Override
    public void onDrag(MouseEvent e) {
        super.onDrag(e);
        setCurrentMousePos(MouseHandler.normalizeMouseCoords(new Vector(e.getX(), e.getY()), getTransform()));
        if (e.getButton() == MouseButton.PRIMARY || e.getButton() == MouseButton.SECONDARY && startPos != null) {
            rect = new Rectangle(startPos, getCurrentMousePos());
            getSelection().clearToBeSelected();
            for (OriPoint point : getCp().getPoints()) {
                if (rect.contains(point)) {
                    getSelection().addToBeSelected(point);
                }
            }
            for (OriLine line : getCp().getOriLines()) {
                if (rect.overlaps(line)) {
                    getSelection().addToBeSelected(line);
                }
            }
        }
    }

    @Override
    protected ToolRenderer<? extends CreasePatternTool> createRenderer() {
        return new BoxSelectionRenderer(getTransform(), this);
    }
}
