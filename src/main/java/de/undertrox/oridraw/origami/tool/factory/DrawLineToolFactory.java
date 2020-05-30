package de.undertrox.oridraw.origami.tool.factory;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.DrawLineTool;
import de.undertrox.oridraw.origami.tool.setting.DrawLineToolSettings;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;

public class DrawLineToolFactory extends CreasePatternToolFactory<DrawLineTool> {

    private DrawLineToolSettings settings = new DrawLineToolSettings();

    public DrawLineToolSettings getSettings() {
        return settings;
    }

    @Override
    public DrawLineTool create(CreasePatternTab tab) {
        return new DrawLineTool(tab, OriLine.Type.MOUNTAIN, this);
    }
}
