package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.math.Vector;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.ui.render.BackgroundRenderer;
import de.undertrox.oridraw.ui.render.CreasePatternRenderer;
import de.undertrox.oridraw.ui.render.Renderer;
import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends Tab {
    private Canvas canvas;
    private CreasePattern cp;
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
        cp = new CreasePattern();
        cp.createSquare(Vector.ORIGIN, 400);
        renderers = new LinkedList<>();
        renderers.add(new BackgroundRenderer(bgTransform));
        renderers.add(new CreasePatternRenderer(cpTransform, cp));

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

    /**
     * @return Canvas of this Tab
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
