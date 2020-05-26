package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.theme.LineStyle;
import de.undertrox.oridraw.util.math.Transform;

public class CreasePatternSelectionLineRenderer extends Renderer {
    CreasePatternSelection selection;

    public CreasePatternSelectionLineRenderer(Transform t, CreasePatternSelection s) {
        super(t);
        this.selection = s;
    }

    @Override
    protected void draw() {
        for (OriLine line : selection.getToBeSelectedLines()) {
            LineStyle s = RenderSettings.getColorManager().getActiveTheme().getToBeSelectedLine();
            RenderHelper.drawLine(line, s,
                    getGc(), getTransform());
        }
        for (OriLine line : selection.getSelectedLines()) {
            LineStyle s = RenderSettings.getColorManager().getActiveTheme().getSelectedLine();
            RenderHelper.drawLine(line, s, getGc(), getTransform());
        }
    }
}
