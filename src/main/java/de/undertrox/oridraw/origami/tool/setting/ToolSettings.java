package de.undertrox.oridraw.origami.tool.setting;

import javafx.scene.layout.GridPane;

public abstract class ToolSettings {
    public static ToolSettings NONE = new ToolSettings() {
        @Override
        public void showToolSettings(GridPane grid) {

        }
    };
    public abstract void showToolSettings(GridPane grid);
}
