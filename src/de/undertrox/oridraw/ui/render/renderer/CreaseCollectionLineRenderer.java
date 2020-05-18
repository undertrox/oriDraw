package de.undertrox.oridraw.ui.render.renderer;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.CreaseCollection;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.paint.Paint;

public class CreaseCollectionLineRenderer extends Renderer {
    private CreaseCollection cc;

    public CreaseCollectionLineRenderer(Transform t, CreaseCollection cc) {
        super(t);
        this.cc = cc;
    }

    @Override
    protected void draw() {
        for (OriLine oriLine : cc.getOriLines()) {
            if (!oriLine.isAnimating()) {
                Paint p = RenderSettings.getColorManager().getPaintForCreaseType(oriLine.getType());
                RenderHelper.drawLine(oriLine, p, RenderSettings.getWidthForCreaseType(oriLine.getType()),
                        getGc(), getTransform());
            }
        }
    }

}
