package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.origami.CreasePattern;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.render.Transform;

public class CreasePatternPointRenderer extends Renderer {
    CreasePattern cp;

    public CreasePatternPointRenderer(Transform t, CreasePattern cp) {
        super(t);
        this.cp = cp;
    }

    @Override
    protected void draw() {
        for (Vector point : cp.getPoints()) {
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().POINT_COLOR,
                    RenderSettings.getPointSideLength(), getGc(), getTransform());
        }
    }
}
