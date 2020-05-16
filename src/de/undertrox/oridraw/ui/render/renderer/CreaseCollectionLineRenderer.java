package de.undertrox.oridraw.ui.render.renderer;
import de.undertrox.oridraw.origami.Crease;
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
        for (Crease crease : cc.getCreases()) {
            if (!crease.isAnimating()) {
                Paint p = RenderSettings.getColorManager().getPaintForCreaseType(crease.getType());
                RenderHelper.drawLine(crease.getLine(), p, RenderSettings.getWidthForCreaseType(crease.getType()),
                        getGc(), getTransform());
            }
        }
    }

}
