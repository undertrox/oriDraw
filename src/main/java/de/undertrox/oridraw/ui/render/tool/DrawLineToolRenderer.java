package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Line;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DrawLineToolRenderer extends ToolRenderer<DrawLineTool> {
    public DrawLineToolRenderer(Transform t, DrawLineTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null) {
            Paint p = RenderSettings.getColorManager().getPaintForCreaseType(getTool().getType());
            if (p instanceof Color) {
                Color c = (Color) p;
                p = Color.color(c.getRed(), c.getGreen(), c.getBlue(), 0.5);
            }
            RenderHelper.drawLine(new Line(getTool().getPoint0(), getTool().getPoint1()),
                    p,
                    RenderSettings.getWidthForCreaseType(getTool().getType()), getGc(), getTransform());
        }
    }
}
