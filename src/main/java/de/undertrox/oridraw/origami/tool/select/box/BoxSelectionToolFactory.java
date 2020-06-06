package de.undertrox.oridraw.origami.tool.select.box;

import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.origami.tool.setting.ToolSettings;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;

public class BoxSelectionToolFactory extends CreasePatternToolFactory<BoxSelectionTool> {
    @Override
    public BoxSelectionTool create(CreasePatternTab tab) {
        return new BoxSelectionTool(tab, this);
    }

    @Override
    public ToolSettings getSettings() {
        return ToolSettings.NONE;
    }
}
