package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.ui.render.settings.ColorManager;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundRenderer extends Renderer {

    public BackgroundRenderer(Transform t) {
        super(t);
    }

    @Override
    protected void draw() {
        GraphicsContext gc = getGc();
        gc.setFill(RenderSettings.getColorManager().CP_EDITOR_BACKGROUND_COLOR);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
}
