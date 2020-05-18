package de.undertrox.oridraw.ui.tab;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.*;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.ui.handler.KeyboardHandler;
import de.undertrox.oridraw.ui.handler.MouseHandler;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.ui.render.*;
import de.undertrox.oridraw.ui.render.renderer.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CreasePatternTab extends CanvasTab {
    private Logger logger = Logger.getLogger(CreasePatternTab.class);
    private List<CreasePatternTool> tools;
    private CreasePatternTool activeTool;
    private Transform cpTransform;
    private Transform bgTransform;
    private Document doc;

    private DrawLineTool p2pTool;

    /**
     * Constructor. Binds the width of the Canvas to that of the tabPane
     *
     * @param title   Title of the tab
     * @param canvas  Canvas to be rendered inside the tab
     * @param tabPane parent tabPane of the Tab
     */
    public CreasePatternTab(String title, Canvas canvas, TabPane tabPane) {
        this(new Document(title, Constants.DEFAULT_PAPER_SIZE, Constants.DEFAULT_GRID_DIVISIONS), canvas, tabPane);

    }

    public CreasePatternTab(Document doc, Canvas canvas, TabPane tabPane) {
        super(doc.getTitle(), canvas);
        logger.debug("Initializing OriLine Pattern");
        this.doc = doc;
        cpTransform = new Transform(new Vector(300, 250), 1, 0);
        bgTransform = new Transform(new Vector(0, 0), 1, 0);
        logger.debug("Initializing Renderers");
        logger.debug("Initializing BackgroundRenderer");
        getRenderers().add(new BackgroundRenderer(bgTransform));
        logger.debug("Initializing Document Renderer");
        getRenderers().add(new DocumentRenderer(cpTransform, doc));
        setMouseHandler(new MouseHandler(doc, cpTransform));
        setKeyboardHandler(new KeyboardHandler(doc));

        logger.debug("Initializing Tools");
        tools = new ArrayList<>();
        DrawLineTool dlTool = new DrawLineTool(this, OriLine.Type.MOUNTAIN);
        p2pTool = dlTool;
        tools.add(dlTool);

        setActiveTool(dlTool);

        logger.debug("Initializing Tool Renderers");
        tools.forEach((tool) -> getRenderers().add(tool.getRenderer()));

        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());
    }

    public DrawLineTool getPointToPointTool() {
        return p2pTool;
    }

    public void setActiveTool(CreasePatternTool activeTool) {
        tools.forEach(t -> t.setEnabled(false));
        this.activeTool = activeTool;
        ((MouseHandler) getMouseHandler()).setActiveTool(activeTool);
        ((KeyboardHandler) getKeyboardHandler()).setActiveTool(activeTool);
        activeTool.setEnabled(true);
    }

    public CreasePatternTool getActiveTool() {
        return activeTool;
    }

    public Transform getCpTransform() {
        return cpTransform;
    }

    public Document getDoc() {
        return doc;
    }
}
