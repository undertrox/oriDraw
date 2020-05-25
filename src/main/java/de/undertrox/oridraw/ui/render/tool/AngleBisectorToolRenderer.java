package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.AngleBisectorTool;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Transform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class AngleBisectorToolRenderer extends ToolRenderer<AngleBisectorTool> {
    public AngleBisectorToolRenderer(Transform t, AngleBisectorTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null && getTool().getPoint2() != null
                && !getTool().getSelection().getToBeSelectedLines().isEmpty()) {

            Paint p = RenderSettings.getColorManager().getPaintForCreaseType(getTool().getType());
            if (p instanceof Color) {
                Color c = (Color) p;
                p = Color.color(c.getRed(), c.getGreen(), c.getBlue(), 0.5);
            }

            OriLine bisector = getTool().angleBisector(
                    getTool().getPoint0(), getTool().getPoint1(), getTool().getPoint2(),
                    getTool().getSelection().getToBeSelectedLines().get(0));
            if (bisector != null) {
                RenderHelper.drawLine(bisector, p,
                        RenderSettings.getWidthForCreaseType(getTool().getType()), getGc(), getTransform());
            }
        }
    }
}
