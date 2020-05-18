package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.render.renderer.tool.AngleBisectorToolRenderer;
import de.undertrox.oridraw.ui.render.renderer.tool.ToolRenderer;
import de.undertrox.oridraw.ui.tab.CreasePatternTab;

public class AngleBisectorTool extends TypedCreasePatternTool {
    public AngleBisectorTool(CreasePatternTab tab, OriLine.Type type) {
        super(tab, type);
    }

    @Override
    protected void enable() {
        getSelection().setMode(CreasePatternSelection.Mode.LINE);
    }

    @Override
    protected void disable() {

    }

    @Override
    public void reset() {

    }

    @Override
    protected ToolRenderer<? extends CreasePatternTool> createRenderer() {
        return new AngleBisectorToolRenderer(getTransform(), this);
    }

    @Override
    public void update() {

    }
}
