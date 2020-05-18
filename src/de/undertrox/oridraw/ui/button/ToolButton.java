package de.undertrox.oridraw.ui.button;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

import java.util.function.Supplier;


public class ToolButton extends ToggleButton {
    Supplier<CreasePatternTool> toolSupplier;
    boolean isActive;

    public ToolButton() {
        super();
        initialize();
    }

    public ToolButton(String text) {
        super(text);
        initialize();
    }

    public ToolButton(String text, Node graphic) {
        super(text, graphic);
        initialize();
    }

    public ToolButton(String text, Supplier<CreasePatternTool> toolSupplier) {
        super(text);
        this.toolSupplier = toolSupplier;
        initialize();
    }

    public ToolButton(String text, Node graphic, Supplier<CreasePatternTool> toolSupplier) {
        super(text, graphic);
        this.toolSupplier = toolSupplier;
        initialize();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private void initialize() {
        isActive = false;
        this.onActionProperty().setValue(this::onClick);
    }

    public void setToolSupplier(Supplier<CreasePatternTool> toolSupplier) {
        this.toolSupplier = toolSupplier;
    }

    public void onClick(ActionEvent e) {
        CreasePatternTool tool = toolSupplier.get();
        if (tool != null) {
            tool.activate();
            OriDraw.getLogger().info(tool.getClass().getName() + " activated");
        }
    }
}
