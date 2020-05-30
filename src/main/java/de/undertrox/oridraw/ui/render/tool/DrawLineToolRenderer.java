package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.tool.drawline.DrawLineTool;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Transform;

public class DrawLineToolRenderer extends ToolRenderer<DrawLineTool> {
    public DrawLineToolRenderer(Transform t, DrawLineTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null) {
            RenderHelper.drawLine(new Line(getTool().getPoint0(), getTool().getPoint1()),
                    RenderSettings.getColorManager().getLineStyleForCreaseType(getTool().getType()).previewStyle(),
                    getGc(), getTransform());
        }
    }
}
