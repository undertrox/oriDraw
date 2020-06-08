package de.undertrox.oridraw.ui.component;

import de.undertrox.oridraw.OriDraw;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.util.LocalizationHelper;
import de.undertrox.oridraw.util.registry.Registries;
import de.undertrox.oridraw.util.registry.RegistryKey;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.function.Supplier;

import static de.undertrox.oridraw.util.io.IOHelper.loadResource;


public class ToolButton extends ToggleButton {
    private static Logger logger = LogManager.getLogger(ToolButton.class);
    Supplier<CreasePatternTool> toolSupplier;
    RegistryKey toolKey;
    MainWindowController controller;

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

    public ToolButton(RegistryKey key, MainWindowController controller) {
        super();
        this.controller = controller;
        this.toolKey = key;
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
        loadIconForTool(toolKey);
        this.onActionProperty().setValue(this::onClick);
        setToggleGroup(controller.toolToggleGroup);
        initToolSupplier();
    }

    private void loadIconForTool(RegistryKey toolKey) {
        // TODO: add a path utitlity to have different Icon sets
        InputStream iconStream = getClass().getClassLoader().getResourceAsStream(
                "ui/icon/" + toolKey.getId() + "/lightmode/enabled_mountain.png");
        if (iconStream != null) {
            Image image = new Image(iconStream);
            ImageView view = new ImageView(image);
            view.setFitHeight(32);
            view.setPreserveRatio(true);
            this.setText("");
            this.setGraphic(view);
        } else {
            this.setText(toolKey.getId());
            logger.warn("Could not find icon for '{}'. Using id as text instead", toolKey);
        }
    }

    private void initToolSupplier() {
        setToolSupplier(() -> {
            if (controller.getSelectedCpTab() != null) {
                for (CreasePatternTool tool : controller.getSelectedCpTab().getTools()) {
                    if (tool.getFactory().getRegistryKey().equals(toolKey)) {
                        return tool;
                    }
                }
            }
            return null;
        });
    }

    public void setToolSupplier(Supplier<CreasePatternTool> toolSupplier) {
        this.toolSupplier = toolSupplier;
    }

    public void onClick(ActionEvent e) {
        if (!isSelected()) {
            // This will only happen when the user clicked on the button when it already was
            // selected. normally, that would deselect it, but it doesnt make sense
            // to have no tool active, so deselecting is prevented
            setSelected(true);
        }
        CreasePatternTool tool = toolSupplier.get();
        if (tool != null) {
            tool.activate();
            OriDraw.getLogger().info("{} activated", tool.getClass().getSimpleName());
        }
        loadHelpFile();
        loadToolSettings();
    }

    private void loadToolSettings() {
        controller.toolSettingsGridPane.getChildren().clear();
        Registries.TOOL_FACTORY_REGISTRY.getItem(toolKey).getSettings().showToolSettings(controller.toolSettingsGridPane);
    }

    private void loadHelpFile() {
        controller.toolNameLabel.setText(LocalizationHelper.getString("oridraw.tool." + toolKey.getId() + ".name"));
        String documentation = loadResource(
                "lang/doc/tools/" + toolKey.getId() + "/tips_" + LocalizationHelper.getLocale().toString() + ".html");
        if (!documentation.isBlank()) {
            controller.documentation.getEngine().loadContent(documentation);
        } else {
            controller.documentation.getEngine().loadContent(LocalizationHelper.getString("oridraw.tool.help.error"));
        }
    }
}
