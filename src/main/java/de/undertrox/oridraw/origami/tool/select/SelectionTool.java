package de.undertrox.oridraw.origami.tool.select;

import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import de.undertrox.oridraw.origami.tool.CreasePatternToolFactory;
import de.undertrox.oridraw.ui.component.tab.CreasePatternTab;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class SelectionTool extends CreasePatternTool {



    private boolean addToSelection = false;

    public SelectionTool(CreasePatternTab tab, CreasePatternToolFactory<? extends CreasePatternTool> factory) {
        super(tab, factory);
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        addToSelection = e.isShiftDown();
    }

    public void onKeyDown(KeyEvent e) {
        super.onKeyDown(e);
        addToSelection = e.isShiftDown();
    }

    public void onKeyUp(KeyEvent e) {
        addToSelection = e.isShiftDown();
    }

    public void onClick(MouseEvent e) {
        super.onClick(e);
        addToSelection = e.isShiftDown();
    }

    public boolean getAddToSelection() {
        return addToSelection;
    }

    public void onDrag(MouseEvent e) {
        addToSelection = e.isShiftDown();
    }

    public void onMove(MouseEvent e) {
        addToSelection = e.isShiftDown();
    }
}
