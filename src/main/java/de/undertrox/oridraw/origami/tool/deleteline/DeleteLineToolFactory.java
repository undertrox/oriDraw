package de.undertrox.oridraw.origami.tool.deleteline;

import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.setting.ToolSettings;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;

public class DeleteLineToolFactory extends CreasePatternToolFactory<DeleteLineTool> {
    @Override
    public DeleteLineTool create(CreasePatternTab tab) {
        return new DeleteLineTool(tab, this);
    }

    @Override
    public ToolSettings getSettings() {
        return ToolSettings.NONE;
    }
}
