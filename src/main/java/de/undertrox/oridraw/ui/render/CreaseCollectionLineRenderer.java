package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.OriLineCollection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import javafx.scene.paint.Paint;

public class CreaseCollectionLineRenderer extends Renderer {
    private OriLineCollection cc;

    public CreaseCollectionLineRenderer(Transform t, OriLineCollection cc) {
        super(t);
        this.cc = cc;
    }

    @Override
    protected void draw() {
        for (OriLine oriLine : cc.getOriLines()) {
            Paint p = RenderSettings.getColorManager().getPaintForCreaseType(oriLine.getType());
            RenderHelper.drawLine(oriLine, p, RenderSettings.getWidthForCreaseType(oriLine.getType()),
                    getGc(), getTransform());

        }
    }

}
