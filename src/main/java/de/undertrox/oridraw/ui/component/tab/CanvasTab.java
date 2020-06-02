package de.undertrox.oridraw.ui.component.tab;

import de.undertrox.oridraw.ui.handler.KeyboardHandlerInterface;
import de.undertrox.oridraw.ui.handler.MouseHandlerInterface;
import de.undertrox.oridraw.ui.render.Renderer;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 * This class is used to use a Canvas inside a tab while scaling it appropriately
 */
public class CanvasTab extends Tab {

    private Canvas canvas;
    private MouseHandlerInterface mouseHandler;
    private KeyboardHandlerInterface keyboardHandler;
    private List<Renderer> renderers;

    public CanvasTab(String title, Canvas canvas) {
        super(title, canvas);
        this.canvas = canvas;
        renderers = new ArrayList<>();
        canvas.setCache(false);
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
        // nothing to do
    }

    /**
     * Renders all enabled renderers
     */
    public void render() {
        getCanvas().getGraphicsContext2D().clearRect(0,0,getCanvas().getWidth(), getCanvas().getHeight());
        for (Renderer renderer : getRenderers()) {
            renderer.render(getCanvas());
        }
    }
}
