package de.undertrox.oridraw.ui.button;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.function.Supplier;


public class ToolButton extends ToggleButton {
    Supplier<CreasePatternTool> toolSupplier;
    RegistryKey toolKey;
    MainWindowController controller;
    ResourceBundle bundle;

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

    public void init(MainWindowController controller, ResourceBundle bundle) {
        this.controller = controller;
        this.bundle = bundle;
        init();
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
        controller.toolNameLabel.setText(bundle.getString("oridraw.tool." + toolKey.getId() + ".name"));
        StringBuilder documentation = new StringBuilder();
        InputStream docStream = getClass().getClassLoader().getResourceAsStream(
                "lang/doc/tools/" + toolKey.getId() + "/tips_" + bundle.getLocale().toString() + ".html");
        if (docStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(docStream));
            String line;
            while (true) {
                try {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    documentation.append(line).append("\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    break;
                }
            }
            controller.documentation.getEngine().loadContent(documentation.toString());
        } else {
            controller.documentation.getEngine().loadContent("");
        }
    }
}
