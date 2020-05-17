package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.KeyboardHandlerInterface;
import de.undertrox.oridraw.ui.MouseHandlerInterface;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.util.math.Vector;

public abstract class CreasePatternTool implements MouseHandlerInterface, KeyboardHandlerInterface {
    boolean enabled;
    ToolRenderer<? extends CreasePatternTool> renderer;
    private Document doc;
    private Transform cpTransform;
    private Vector currentMousePos;

    public CreasePatternTool(Document doc, Transform cpTransform) {
        this.doc = doc;
        this.cpTransform = cpTransform;
        this.currentMousePos = new Vector(0, 0);
        reset();
    }

    /**
     * This Method should do preparations like setting the selection mode
     */
    protected abstract void enable();

    /**
     * This Method can do cleanup before the Tool is disabled if necessary
     */
    protected abstract void disable();

    public abstract void reset();

    public CreasePattern getCp() {
        return doc.getCp();
    }

    public CreasePatternSelection getSelection() {
        return doc.getSelection();
    }

    public Transform getTransform() {
        return cpTransform;
    }

    public ToolRenderer<? extends CreasePatternTool> getRenderer() {
        if (renderer == null) {
            renderer = createRenderer();
        }
        return renderer;
    }

    protected abstract ToolRenderer<? extends CreasePatternTool> createRenderer();

    public Vector getCurrentMousePos() {
        return currentMousePos;
    }

    protected void setCurrentMousePos(Vector mousePos) {
        this.currentMousePos = mousePos;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean e) {
        if (e && !enabled) {
            enable();
        } else if (!e && enabled) disable();
        this.enabled = e;
    }

    public abstract void update();
}