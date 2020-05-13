package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.ui.render.settings.ColorManager;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.paint.Paint;

public class CreasePatternLineRenderer extends Renderer {
    private CreasePattern cp;

    public CreasePatternLineRenderer(Transform t, CreasePattern cp) {
        super(t);
        this.cp = cp;
    }

    @Override
    protected void draw() {
        getGc().fillRect(0, 0, getWidth(), getHeight());
        for (Crease crease : cp.getCreases()) {
            Paint p = RenderSettings.getColorManager().getPaintForCreaseType(crease.getType());
            RenderHelper.drawLine(crease.getLine(), p, RenderSettings.getWidthForCreaseType(crease.getType()), getGc());
        }
    }

}
