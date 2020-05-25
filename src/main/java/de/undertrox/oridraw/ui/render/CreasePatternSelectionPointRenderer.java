package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.util.math.Transform;
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
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().getSelectedColor(),
                    RenderSettings.getPointSideLength(), getGc(), getTransform());
        }
        for (Vector point : selection.getToBeSelectedPoints()) {
            RenderHelper.drawSquare(point, RenderSettings.getColorManager().getToBeSelectedColor(),
                    RenderSettings.getPointSideLength(), getGc(), getTransform());
        }
    }
}
