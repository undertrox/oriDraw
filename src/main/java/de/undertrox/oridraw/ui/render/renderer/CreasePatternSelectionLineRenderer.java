package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import javafx.scene.paint.Color;
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
            Color p = RenderSettings.getColorManager().TO_BE_SELECTED_COLOR;
            Color c = Color.color(p.getRed(), p.getGreen(), p.getBlue(), 0.9);
            RenderHelper.drawLine(line, c, RenderSettings.getWidthForCreaseType(line.getType()),
                    getGc(), getTransform());
        }
        for (OriLine line : selection.getSelectedLines()) {
            Color p = RenderSettings.getColorManager().SELECTED_COLOR;
            Color c = Color.color(p.getRed(), p.getGreen(), p.getBlue(), 0.9);
            RenderHelper.drawLine(line, c, RenderSettings.getWidthForCreaseType(line.getType()),
                    getGc(), getTransform());
        }
    }
}
