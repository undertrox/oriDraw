package de.undertrox.oridraw.ui.render.tool;

import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.ui.render.Renderer;

public abstract class ToolRenderer<T extends CreasePatternTool> extends Renderer {

    private T tool;

    public ToolRenderer(Transform t, T tool) {
        super(t);
        this.tool = tool;
    }

    protected T getTool() {
        return tool;
    }

    @Override
    public boolean getEnabled() {
        return tool.isEnabled();
    }
}
