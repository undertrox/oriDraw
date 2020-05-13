package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.ui.render.*;
import de.undertrox.oridraw.ui.render.renderer.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends Tab {
    Logger logger = Logger.getLogger(CreasePatternTab.class);
    private Canvas canvas;
    private CreasePattern cp;
    private CreasePatternSelection cpSel;
    private MouseHandler mouseHandler;
    private List<Renderer> renderers;
    private Transform cpTransform;
    private Transform bgTransform;

    /**
     * Constructor. Binds the width of the Canvas to that of the tabPane
     *
     * @param title   Title of the tab
     * @param canvas  Canvas to be rendered inside the tab
     * @param tabPane parent tabPane of the Tab
     */
    public CreasePatternTab(String title, Canvas canvas, TabPane tabPane) {
        super(title, canvas);
        this.canvas = canvas;
        cpTransform = new Transform(new Vector(300, 250), 1, 0);
        bgTransform = new Transform(new Vector(0, 0), 1, 0);
        logger.debug("Initializing Crease Pattern");
        cp = new CreasePattern();
        cp.createSquare(Vector.ORIGIN, 400);
        cpSel = new CreasePatternSelection(cp);
        mouseHandler = new MouseHandler(cp, cpSel, cpTransform);
        logger.debug("Initializing Renderers");
        renderers = new ArrayList<>();
        logger.debug("Initializing BackgroundRenderer");
        renderers.add(new BackgroundRenderer(bgTransform));
        logger.debug("Initializing CreasePatternLineRenderer");
        renderers.add(new CreasePatternLineRenderer(cpTransform, cp));
        logger.debug("Initializing CreasePatternPointRenderer");
        renderers.add(new CreasePatternPointRenderer(cpTransform, cp));
        logger.debug("Initializing CreasePatternSelectionPointRenderer");
        renderers.add(new CreasePatternSelectionPointRenderer(cpTransform, cpSel));

        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());
    }

    /**
     * Renders all enabled renderers
     */
    public void render() {
        for (Renderer renderer : renderers) {
            renderer.render(canvas);
        }
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    /**
     * @return Canvas of this Tab
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public CreasePattern getCreasePattern() {
        return cp;
    }

    public Transform getCpTransform() {
        return cpTransform;
    }
}
