package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.anglebisector.AngleBisectorTool;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.ui.theme.LineStyle;
import de.undertrox.oridraw.util.math.Transform;

public class AngleBisectorToolRenderer extends ToolRenderer<AngleBisectorTool> {
    public AngleBisectorToolRenderer(Transform t, AngleBisectorTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null && getTool().getPoint2() != null
                && !getTool().getSelection().getToBeSelectedLines().isEmpty()) {

            LineStyle s = RenderSettings.getColorManager().getLineStyleForCreaseType(getTool().getType()).previewStyle();

            OriLine bisector = getTool().angleBisector(
                    getTool().getPoint0(), getTool().getPoint1(), getTool().getPoint2(),
                    getTool().getSelection().getToBeSelectedLines().get(0));
            if (bisector != null) {
                RenderHelper.drawLine(bisector, s, getGc(), getTransform());
            }
        }
    }
}
