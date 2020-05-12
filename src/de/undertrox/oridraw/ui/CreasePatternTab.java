package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.math.Vector;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.render.CreasePatternRenderer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends Tab {
    private Canvas canvas;
    private CreasePattern cp;
    private CreasePatternRenderer renderer;

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
        cp = new CreasePattern();
        cp.createSquare(Vector.ORIGIN, 400);
        renderer = new CreasePatternRenderer(canvas, cp);
        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());
    }

    /**
     * @return Canvas of this Tab
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public CreasePatternRenderer getRenderer() {
        return renderer;
    }
}
