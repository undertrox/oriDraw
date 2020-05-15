package de.undertrox.oridraw.ui.render.renderer.tool;

import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.ui.render.Transform;
import de.undertrox.oridraw.ui.render.renderer.RenderHelper;
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
            Color p = RenderSettings.getColorManager().getPaintForCreaseType(getTool().getType());
            Color c = Color.color(p.getRed(), p.getGreen(), p.getBlue(), 0.5);
            RenderHelper.drawLine(new Line(getTool().getPoint0(), getTool().getPoint1()),
                    c,
                    RenderSettings.getWidthForCreaseType(getTool().getType()), getGc(), getTransform());
        }
    }
}
