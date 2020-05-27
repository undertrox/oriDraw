package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.Document;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawLineTool extends TypedCreasePatternTool {
    private Logger logger = LogManager.getLogger(DrawLineTool.class);
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


                //  Create the cp memento, set it in originator and add mementos
                // with the caretaker. After setting we will also need to keep track of the current target index . We will
                // always be at the end of the arraylist or basically the size of the arraylist - 1 index.
                //Add the CP to the saved state

                Document saveDocState = new Document (this.getDoc());
                getCp().addOriLine(point0, point1, getType());

                this.getCPTab().getOriginator().set(saveDocState);
                logger.debug("Document state being set : ");
                logger.debug(saveDocState);


                this.getCPTab().getCareTaker().addMementos(this.getCPTab().getOriginator().createMementos());
                // update the indexes for the number of cps and the current state index.
                this.getCPTab().setNoOfSavedStates(this.getCPTab().getNoOfSavedStates() + 1);
                this.getCPTab().setCurrMementoStateIndex(this.getCPTab().getCurrMementoStateIndex() + 1);
                logger.debug("DrawLineTool Class -- getNoOfSavedStates : "+this.getCPTab().getNoOfSavedStates());
                logger.debug("DrawLineTool Class -- getCurrMementoStateIndex : "+this.getCPTab().getCurrMementoStateIndex());

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
