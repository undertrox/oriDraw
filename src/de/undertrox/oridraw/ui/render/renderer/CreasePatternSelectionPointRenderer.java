package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Vector;

public class CreasePatternSelectionPointRenderer extends Renderer {
    CreasePatternSelection selection;

    public CreasePatternSelectionPointRenderer(Transform t, CreasePatternSelection s) {
        super(t);
        selection = s;
    }

    @Override
    protected void draw() {
        for (Vector point : selection.getSelectedPoints()) {
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().SELECTED_POINT_COLOR,
                    RenderSettings.getPointSideLength(), getGc());
        }
        for (Vector point : selection.getToBeSelectedPoints()) {
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().TO_BE_SELECTED_POINT_COLOR,
                    RenderSettings.getPointSideLength(), getGc());
        }
    }
}
