package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;

public class ToolFactoryRegistry extends Registry<CreasePatternToolFactory<? extends CreasePatternTool>> {
    @Override
    protected void onRegistered(RegistryEntry<CreasePatternToolFactory<? extends CreasePatternTool>> item) {
        // Nothing to do
    }

    @Override
    protected void onLocked() {
        // TODO: move code for creating Tool buttons here
    }
}
