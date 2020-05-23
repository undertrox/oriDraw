package de.undertrox.oridraw.origami.tool.factory;

import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;
import de.undertrox.oridraw.util.registry.Registerable;

public abstract class CreasePatternToolFactory<T extends CreasePatternTool> extends Registerable {
    public abstract T create(CreasePatternTab tab);

}
