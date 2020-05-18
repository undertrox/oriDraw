package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;

public class AngleBisectorTool extends TypedCreasePatternTool {
    public AngleBisectorTool(CreasePatternTab tab, OriLine.Type type) {
        super(tab, type);
    }

    @Override
    protected void enable() {

    }

    @Override
    protected void disable() {

    }

    @Override
    public void reset() {

    }

    @Override
    protected ToolRenderer<? extends CreasePatternTool> createRenderer() {
        return null;
    }

    @Override
    public void update() {

    }
}
