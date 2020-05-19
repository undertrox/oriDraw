package de.undertrox.oridraw.ui.tab;

import de.undertrox.oridraw.ui.handler.KeyboardHandlerInterface;
import de.undertrox.oridraw.ui.handler.MouseHandlerInterface;
import de.undertrox.oridraw.ui.render.renderer.Renderer;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CanvasTab extends Tab {

    private Canvas canvas;
    private MouseHandlerInterface mouseHandler;
    private KeyboardHandlerInterface keyboardHandler;
    private List<Renderer> renderers;

    protected ResourceBundle bundle;

    public CanvasTab(String title, Canvas canvas, ResourceBundle bundle) {
        super(title, canvas);
        this.canvas = canvas;
        this.bundle = bundle;
        renderers = new ArrayList<>();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public MouseHandlerInterface getMouseHandler() {
        return mouseHandler;
    }

    public KeyboardHandlerInterface getKeyboardHandler() {
        return keyboardHandler;
    }

    public List<Renderer> getRenderers() {
        return renderers;
    }

    public void setMouseHandler(MouseHandlerInterface mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    public void setKeyboardHandler(KeyboardHandlerInterface keyboardHandler) {
        this.keyboardHandler = keyboardHandler;
    }

    public void onCloseRequest(Event e) {
    }

    /**
     * Renders all enabled renderers
     */
    public void render() {
        for (Renderer renderer : getRenderers()) {
            renderer.render(getCanvas());
        }
    }
}
