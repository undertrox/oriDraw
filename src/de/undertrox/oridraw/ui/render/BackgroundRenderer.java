package de.undertrox.oridraw.ui.render;

import javafx.scene.canvas.GraphicsContext;

public class BackgroundRenderer extends Renderer {

    public BackgroundRenderer(Transform t) {
        super(t);
    }

    @Override
    protected void draw() {
        GraphicsContext gc = getGc();
        gc.setFill(ColorManager.getInstance().CP_EDITOR_BACKGROUND_COLOR);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
}
