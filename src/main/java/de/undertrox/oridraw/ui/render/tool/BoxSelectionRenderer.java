package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.tool.select.box.BoxSelectionTool;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Transform;

public class BoxSelectionRenderer extends ToolRenderer<BoxSelectionTool> {
    public BoxSelectionRenderer(Transform t, BoxSelectionTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getRect() != null) {
            for (Line line : getTool().getRect().getLines()) {
                RenderHelper.drawLine(line, RenderSettings.getColorManager().getActiveTheme().getSelectionOutline(), getGc(), getTransform());
            }
        }
    }
}
