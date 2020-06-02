package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.tool.setting.ToolSettings;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.util.registry.Registrable;

public abstract class CreasePatternToolFactory<T extends CreasePatternTool> extends Registrable {
    public abstract T create(CreasePatternTab tab);

    public abstract ToolSettings getSettings();
}
