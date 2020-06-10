package de.undertrox.oridraw.origami.tool.deleteline;

import de.undertrox.oridraw.Constants;
import de.undertrox.oridraw.origami.CreasePatternSelection;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import de.undertrox.oridraw.ui.render.tool.DeleteLineRenderer;
import de.undertrox.oridraw.ui.render.tool.ToolRenderer;
import de.undertrox.oridraw.util.registry.Registries;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class DeleteLineTool extends CreasePatternTool {
    public DeleteLineTool(CreasePatternTab tab, CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        super(tab, factory);
    }

    @Override
    protected void enable() {
        getSelection().clear();
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
        return new DeleteLineRenderer(getTransform(), this);
    }

    @Override
    public void onKeyPressed(KeyEvent e) {

    }

    @Override
    public void onKeyUp(KeyEvent e) {

    }

    @Override
    public void onDrag(MouseEvent e) {

    }

    @Override
    public void onMove(MouseEvent e) {

    }

    @Override
    public void onClick(MouseEvent e) {
        super.onClick(e);
        getSelection().selectToBeSelectedLines();
        Registries.ACTION_REGISTRY.getItem(Constants.REGISTRY_DOMAIN, "delete_selected_lines").run();
    }
}
