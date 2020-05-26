package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundRenderer extends Renderer {

    public BackgroundRenderer(Transform t) {
        super(t);
    }

    @Override
    protected void draw() {
        GraphicsContext gc = getGc();
        gc.setFill(RenderSettings.getColorManager().getActiveTheme().getBackground());
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
}
