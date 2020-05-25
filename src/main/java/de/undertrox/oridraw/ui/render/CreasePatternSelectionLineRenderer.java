package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Transform;
import javafx.scene.paint.Paint;

public class CreasePatternSelectionLineRenderer extends Renderer {
    CreasePatternSelection selection;

    public CreasePatternSelectionLineRenderer(Transform t, CreasePatternSelection s) {
        super(t);
        this.selection = s;
    }

    @Override
    protected void draw() {
        for (OriLine line : selection.getToBeSelectedLines()) {
            Paint p = RenderSettings.getColorManager().getToBeSelectedColor();
            RenderHelper.drawLine(line, p, RenderSettings.getWidthForCreaseType(line.getType()),
                    getGc(), getTransform());
        }
        for (OriLine line : selection.getSelectedLines()) {
            Paint p = RenderSettings.getColorManager().getSelectedColor();
            RenderHelper.drawLine(line, p, RenderSettings.getWidthForCreaseType(line.getType()),
                    getGc(), getTransform());
        }
    }
}
