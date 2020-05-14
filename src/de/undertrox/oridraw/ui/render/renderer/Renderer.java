package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Renderer {
    private Canvas canvas;
    private GraphicsContext gc;
    private double height;
    private double width;
    private Transform transform;
    private boolean enabled;

    public Renderer(Transform t) {
        transform = t;
        enabled = true;
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    private void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        height = canvas.getHeight();
        width = canvas.getWidth();
        transform.apply(gc);
    }

    protected GraphicsContext getGc() {
        return gc;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Transform getTransform() {
        return transform;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * calls draw(canvas) on Canvas c if the renderer is enabled
     *
     * @param c: Canvas to draw on
     */
    public void render(Canvas c) {
        if (getEnabled()) {
            setCanvas(c);
            draw();
        }
    }

    protected abstract void draw();
}
