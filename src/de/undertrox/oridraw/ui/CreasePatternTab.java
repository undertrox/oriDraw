package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.*;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.ui.render.*;
import de.undertrox.oridraw.ui.render.renderer.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends Tab {
    Logger logger = Logger.getLogger(CreasePatternTab.class);
    private Canvas canvas;
    private CreasePattern cp;
    private MouseHandler mouseHandler;
    private KeyboardHandler keyboardHandler;
    private List<Renderer> renderers;
    private List<CreasePatternTool> tools;
    private CreasePatternTool activeTool;
    private Transform cpTransform;
    private Transform bgTransform;
    private Document doc;

    /**
     * Constructor. Binds the width of the Canvas to that of the tabPane
     *
     * @param title   Title of the tab
     * @param canvas  Canvas to be rendered inside the tab
     * @param tabPane parent tabPane of the Tab
     */
    public CreasePatternTab(String title, Canvas canvas, TabPane tabPane) {
        this(new Document(title, Constants.DEFAULT_PAPER_SIZE, 4), canvas, tabPane);

    }

    public KeyboardHandler getKeyboardHandler() {
        return keyboardHandler;
    }

    public CreasePatternTab(Document doc, Canvas canvas, TabPane tabPane) {
        super(doc.getTitle(), canvas);
        logger.debug("Initializing Crease Pattern");
        this.doc = doc;
        this.canvas = canvas;
        cp = doc.getCp();
        cpTransform = new Transform(new Vector(300, 250), 1, 0);
        bgTransform = new Transform(new Vector(0, 0), 1, 0);
        logger.debug("Initializing Renderers");
        renderers = new ArrayList<>();
        logger.debug("Initializing BackgroundRenderer");
        renderers.add(new BackgroundRenderer(bgTransform));
        logger.debug("Initializing Document Renderer");
        renderers.add(new DocumentRenderer(cpTransform, doc));

        mouseHandler = new MouseHandler(doc, cpTransform);
        keyboardHandler = new KeyboardHandler(doc);

        logger.debug("Initializing Tools");
        tools = new ArrayList<>();
        DrawLineTool dlTool = new DrawLineTool(doc, cpTransform, Crease.Type.MOUNTAIN);
        tools.add(dlTool);

        setActiveTool(dlTool);

        logger.debug("Initializing Tool Renderers");
        tools.forEach((tool) -> renderers.add(tool.getRenderer()));

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

    public void setActiveTool(CreasePatternTool activeTool) {
        tools.forEach(t -> t.setEnabled(false));
        this.activeTool = activeTool;
        mouseHandler.setActiveTool(activeTool);
        activeTool.setEnabled(true);
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
