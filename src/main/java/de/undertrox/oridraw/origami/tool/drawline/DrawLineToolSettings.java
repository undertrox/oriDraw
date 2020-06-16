package de.undertrox.oridraw.origami.tool.drawline;

import de.undertrox.oridraw.origami.tool.setting.BooleanToolSetting;
import de.undertrox.oridraw.origami.tool.setting.ToolSettings;
import de.undertrox.oridraw.util.LocalizationHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DrawLineToolSettings extends ToolSettings {
    private BooleanToolSetting continueLine;
    private BooleanToolSetting snapTo225;
    private BooleanToolSetting flipTypeAtIntersection;

    public DrawLineToolSettings() {
        continueLine = new BooleanToolSetting("continueline", false);
        snapTo225 = new BooleanToolSetting("snap225", false);
        flipTypeAtIntersection = new BooleanToolSetting("flipintersection", false);
    }

    @Override
    public void showToolSettings(GridPane grid) {
        grid.add(new Label(LocalizationHelper.getString("oridraw.tool.point_to_point.setting.continueline")), 0, 0);
        grid.add(continueLine.getControlNode(), 1, 0);
        grid.add(new Label(LocalizationHelper.getString("oridraw.tool.point_to_point.setting.snap225")), 0, 1);
        grid.add(snapTo225.getControlNode(), 1, 1);
        grid.add(new Label(LocalizationHelper.getString("oridraw.tool.point_to_point.setting.flipintersection")), 0, 2);
        grid.add(flipTypeAtIntersection.getControlNode(), 1, 2);
    }

    public BooleanToolSetting getContinueLine() {
        return continueLine;
    }

    public BooleanToolSetting getSnapTo225() {
        return snapTo225;
    }

    public BooleanToolSetting getFlipTypeAtIntersection() {
        return flipTypeAtIntersection;
    }

    public boolean continueLine() {
        return continueLine.getState();
    }
    public boolean snapTo225() {
        return snapTo225.getState();
    }
    public boolean flipTypeAtIntersection() {
        return flipTypeAtIntersection.getState();
    }
}
