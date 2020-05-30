package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.handler.KeyboardHandlerInterface;
import de.undertrox.oridraw.ui.handler.MouseHandlerInterface;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.util.registry.Registrable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class CreasePatternTool extends Registrable implements MouseHandlerInterface, KeyboardHandlerInterface {
    boolean enabled;
    ToolRenderer<? extends CreasePatternTool> renderer;
    private Document doc;
    private Transform cpTransform;
    private Vector currentMousePos;
    private CreasePatternTab tab;
    private CreasePatternToolFactory<? extends CreasePatternTool> factory;

    public CreasePatternToolFactory<? extends CreasePatternTool> getFactory() {
        return factory;
    }

    public CreasePatternTool(CreasePatternTab tab, CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        this.doc = tab.getDoc();
        this.cpTransform = tab.getDocTransform();
        this.currentMousePos = new Vector(0, 0);
        this.tab = tab;
        this.factory = factory;
        reset();
    }

    public void activate() {
        this.tab.setActiveTool(this);
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

    public void onClick(MouseEvent e) {
        if (e.getButton().equals(MouseButton.SECONDARY)) {
            reset();
        }
    }

    public void onKeyDown(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ESCAPE)) {
            reset();
        }
    }
}
