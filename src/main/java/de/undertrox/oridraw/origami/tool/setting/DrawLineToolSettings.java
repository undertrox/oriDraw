package de.undertrox.oridraw.origami.tool.setting;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class DrawLineToolSettings extends ToolSettings {
    private BooleanToolSetting continueLine;
    private BooleanToolSetting snapTo225;

    public DrawLineToolSettings() {
        continueLine = new BooleanToolSetting("continueline", false);
        snapTo225 = new BooleanToolSetting("snap225", false);
    }

    @Override
    public void showToolSettings(GridPane grid, ResourceBundle bundle) {
        grid.add(new Label(bundle.getString("oridraw.tool.point_to_point.setting.continueline")), 0, 0);
        grid.add(continueLine.getControlNode(), 1, 0);
        grid.add(new Label(bundle.getString("oridraw.tool.point_to_point.setting.snap225")), 0, 1);
        grid.add(snapTo225.getControlNode(), 1, 1);
    }

    public BooleanToolSetting getContinueLine() {
        return continueLine;
    }

    public BooleanToolSetting getSnapTo225() {
        return snapTo225;
    }

    public boolean continueLine() {
        return continueLine.getState();
    }
    public boolean snapTo225() {
        return snapTo225.getState();
    }
}
