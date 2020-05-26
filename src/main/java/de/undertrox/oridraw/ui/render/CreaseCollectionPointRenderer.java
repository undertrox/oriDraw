package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.OriLineCollection;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Transform;

public class CreaseCollectionPointRenderer extends Renderer {
    OriLineCollection cc;

    public CreaseCollectionPointRenderer(Transform t, OriLineCollection cc) {
        super(t);
        this.cc = cc;
    }

    @Override
    protected void draw() {
        for (Vector point : cc.getPoints()) {
            RenderHelper.drawPoint(point, RenderSettings.getColorManager().getActiveTheme().getDefaultPoint(),
                    getGc(), getTransform());
        }
    }
}
