package de.undertrox.oridraw.ui;

import de.undertrox.oridraw.ui.render.renderer.Renderer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;

public class CanvasTab extends Tab {

    private Canvas canvas;
    private MouseHandlerInterface mouseHandler;
    private KeyboardHandlerInterface keyboardHandler;
    private List<Renderer> renderers;

    public CanvasTab(String title, Canvas canvas) {
        super(title, canvas);
        this.canvas = canvas;
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


    /**
     * Renders all enabled renderers
     */
    public void render() {
        for (Renderer renderer : getRenderers()) {
            renderer.render(getCanvas());
        }
    }
}
