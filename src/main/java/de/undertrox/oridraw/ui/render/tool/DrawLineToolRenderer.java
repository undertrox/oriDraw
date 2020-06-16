package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.OriPoint;
import de.undertrox.oridraw.origami.tool.drawline.DrawLineTool;
import de.undertrox.oridraw.ui.render.RenderHelper;
import de.undertrox.oridraw.ui.render.settings.RenderSettings;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.util.math.Vector;

import java.util.List;

public class DrawLineToolRenderer extends ToolRenderer<DrawLineTool> {
    public DrawLineToolRenderer(Transform t, DrawLineTool tool) {
        super(t, tool);
    }

    @Override
    protected void draw() {
        if (getTool().getPoint0() != null && getTool().getPoint1() != null ) {
            if (!getTool().getSettings().flipTypeAtIntersection()) {
                RenderHelper.drawLine(new Line(getTool().getPoint0(), getTool().getPoint1()),
                        RenderSettings.getColorManager().getLineStyleForCreaseType(getTool().getType()).previewStyle(),
                        getGc(), getTransform());
            } else {
                Vector last = getTool().getPoint0();
                OriLine.Type type = getTool().getType();
                List<OriPoint> intersections = getTool().getIntersections();
                for (OriPoint intersection : intersections) {
                    if (!intersection.isValid()) continue;
                    RenderHelper.drawLine(new Line(last, intersection),
                            RenderSettings.getColorManager().getLineStyleForCreaseType(type).previewStyle(),
                            getGc(), getTransform());
                    last = intersection;
                    type = type.flip();
                }
                RenderHelper.drawLine(new Line(last, getTool().getPoint1()),
                        RenderSettings.getColorManager().getLineStyleForCreaseType(type).previewStyle(),
                        getGc(), getTransform());
            }
        }
    }
}
