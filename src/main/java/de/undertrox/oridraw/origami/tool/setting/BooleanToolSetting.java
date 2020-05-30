package de.undertrox.oridraw.origami.tool.setting;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class BooleanToolSetting extends ToolSetting<Boolean> {

    CheckBox checkBox;

    public BooleanToolSetting(String id, Boolean startingState) {
        super(id, startingState);
        checkBox = new CheckBox();
        checkBox.setOnAction(e -> setState(checkBox.isSelected()));
    }

    @Override
    public Node getControlNode() {
        return checkBox;
    }
}
