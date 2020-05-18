package de.undertrox.oridraw.ui.handler;

import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.origami.tool.CreasePatternTool;
import javafx.scene.input.KeyEvent;

public class KeyboardHandler implements KeyboardHandlerInterface {
    Document doc;
    private CreasePatternTool activeTool;

    public KeyboardHandler(Document doc) {
        this.doc = doc;
    }

    public void setActiveTool(CreasePatternTool activeTool) {
        this.activeTool = activeTool;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        activeTool.onKeyPressed(e);
    }


    @Override
    public void onKeyDown(KeyEvent e) {
        activeTool.onKeyDown(e);
    }

    @Override
    public void onKeyUp(KeyEvent e) {
        activeTool.onKeyUp(e);
    }
}
