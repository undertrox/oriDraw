package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.origami.CreaseCollection;
import de.undertrox.oridraw.util.math.Vector;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.render.Transform;

public class CreaseCollectionPointRenderer extends Renderer {
    CreaseCollection cc;

    public CreaseCollectionPointRenderer(Transform t, CreaseCollection cc) {
        super(t);
        this.cc = cc;
    }

    @Override
    protected void draw() {
        for (Vector point : cc.getPoints()) {
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().POINT_COLOR,
                    RenderSettings.getPointSideLength(), getGc(), getTransform());
        }
    }
}
