package de.undertrox.oridraw.ui.button;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

import java.util.function.Supplier;


public class ToolButton extends ToggleButton {
    Supplier<CreasePatternTool> toolSupplier;
    RegistryKey toolKey;

    public RegistryKey getToolKey() {
        return toolKey;
    }

    public void setToolKey(RegistryKey toolKey) {
        this.toolKey = toolKey;
    }

    boolean isActive;

    public ToolButton() {
        super();
        init();
    }

    public ToolButton(String text) {
        super(text);
        init();
    }

    public ToolButton(String text, Node graphic) {
        super(text, graphic);
        init();
    }

    public ToolButton(String text, Supplier<CreasePatternTool> toolSupplier) {
        super(text);
        this.toolSupplier = toolSupplier;
        init();
    }

    public ToolButton(String text, Node graphic, Supplier<CreasePatternTool> toolSupplier) {
        super(text, graphic);
        this.toolSupplier = toolSupplier;
        init();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private void init() {
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
            OriDraw.getLogger().info("{} activated", tool.getClass().getSimpleName());
        }
    }
}
