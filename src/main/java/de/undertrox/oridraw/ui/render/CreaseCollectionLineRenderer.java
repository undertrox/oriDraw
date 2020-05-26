package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriLineCollection;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.theme.LineStyle;
import de.undertrox.oridraw.util.math.Transform;

public class CreaseCollectionLineRenderer extends Renderer {
    private OriLineCollection cc;

    public CreaseCollectionLineRenderer(Transform t, OriLineCollection cc) {
        super(t);
        this.cc = cc;
    }

    @Override
    protected void draw() {
        for (OriLine oriLine : cc.getOriLines()) {
            LineStyle s = RenderSettings.getColorManager().getLineStyleForCreaseType(oriLine.getType());
            RenderHelper.drawLine(oriLine, s, getGc(), getTransform());

        }
    }

}
