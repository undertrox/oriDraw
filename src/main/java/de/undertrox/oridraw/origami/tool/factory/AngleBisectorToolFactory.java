package de.undertrox.oridraw.origami.tool.factory;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.tool.AngleBisectorTool;
import de.undertrox.oridraw.origami.tool.setting.AngleBisectorSettings;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;

public class AngleBisectorToolFactory extends CreasePatternToolFactory<AngleBisectorTool> {
    @Override
    public AngleBisectorTool create(CreasePatternTab tab) {
        return new AngleBisectorTool(tab, OriLine.Type.MOUNTAIN, this);
    }
    private AngleBisectorSettings settings = new AngleBisectorSettings();

    public AngleBisectorSettings getSettings() {
        return settings;
    }
}
