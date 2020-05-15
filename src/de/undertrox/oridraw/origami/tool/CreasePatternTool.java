package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.ui.MouseHandlerInterface;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.util.math.Vector;

public abstract class CreasePatternTool implements MouseHandlerInterface {
    private CreasePattern cp;
    private CreasePatternSelection selection;
    private Transform cpTransform;
    boolean enabled;
    private Vector currentMousePos;
    ToolRenderer<? extends CreasePatternTool> renderer;

    public CreasePatternTool(CreasePattern cp, CreasePatternSelection selection, Transform cpTransform) {
        this.cp = cp;
        this.selection = selection;
        this.cpTransform = cpTransform;
        this.currentMousePos = new Vector(0, 0);
    }

    public void setEnabled(boolean e) {
        if (e && !enabled) {
            enable();
        } else if (!e && enabled) disable();
        this.enabled = e;
    }

    /**
     * This Method should do preparations like setting the selection mode
     */
    protected abstract void enable();

    /**
     * This Method can do cleanup before the Tool is disabled if necessary
     */
    protected abstract void disable();

    public CreasePattern getCp() {
        return cp;
    }

    public CreasePatternSelection getSelection() {
        return selection;
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

    public boolean isEnabled() {
        return enabled;
    }

    protected void setCurrentMousePos(Vector mousePos) {
        this.currentMousePos = mousePos;
    }

    public abstract void update();
}
