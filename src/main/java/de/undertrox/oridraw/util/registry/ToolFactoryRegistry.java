package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.MainWindowController;
import de.undertrox.oridraw.ui.action.Action;

public class ToolFactoryRegistry extends Registry<CreasePatternToolFactory<? extends CreasePatternTool>> {
    @Override
    protected void onRegistered(RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> item) {
        // Nothing to do
    }

    @Override
    protected void onLocked() {
        // TODO: move code for creating Tool buttons here
        for (RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> entry : getEntries()) {
            Action action = new Action(() ->
                MainWindowController.instance.getToolButtonForRegistryKey(entry.getKey()).fire()
            );
            Registries.ACTION_REGISTRY.register(new RegistryKey(Constants.REGISTRY_DOMAIN, "activate_" + entry.getKey().getId()),
                    action, MainWindowController.instance.toolMenu);
        }
    }
}
