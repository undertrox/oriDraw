package de.undertrox.oridraw.ui.render.renderer.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.AngleBisectorTool;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import javafx.scene.paint.Color;

public class AngleBisectorToolRenderer extends ToolRenderer<AngleBisectorTool> {
    public AngleBisectorToolRenderer(Transform t, AngleBisectorTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null && getTool().getPoint2() != null
                && !getTool().getSelection().getToBeSelectedLines().isEmpty()) {

            Color p = RenderSettings.getColorManager().getPaintForCreaseType(getTool().getType());
            Color c = Color.color(p.getRed(), p.getGreen(), p.getBlue(), 0.5);
            OriLine bisector = getTool().angleBisector(
                    getTool().getPoint0(), getTool().getPoint1(), getTool().getPoint2(),
                    getTool().getSelection().getToBeSelectedLines().get(0));
            if (bisector != null) {
                RenderHelper.drawLine(bisector, c,
                        RenderSettings.getWidthForCreaseType(getTool().getType()), getGc(), getTransform());
            }
        }
    }
}
