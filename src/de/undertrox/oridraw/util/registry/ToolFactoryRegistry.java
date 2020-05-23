package de.undertrox.oridraw.util.registry;

import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.factory.CreasePatternToolFactory;

public class ToolFactoryRegistry extends Registry<CreasePatternToolFactory<? extends CreasePatternTool>> {
    @Override
    protected void onRegistered(RegistryKey key, CreasePatternToolFactory<? extends CreasePatternTool> item) {

    }

    @Override
    protected void onLocked() {

    }
}
