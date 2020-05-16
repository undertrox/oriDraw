package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


/**
 * This is a CreasePatternTool that has a specific Crease Type assigned to it.
 */
public abstract class TypedCreasePatternTool extends CreasePatternTool {
    Crease.Type type;
    boolean flipped;


    public TypedCreasePatternTool(Document doc, Transform cpTransform, Crease.Type type) {
        super(doc, cpTransform);
        this.type = type;
        flipped = false;
    }

    public Crease.Type getType() {
        return flipped ? type.flip() : type;
    }

    public void setType(Crease.Type type) {
        this.type = type;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        flipped = e.isAltDown();
    }

    public void onKeyDown(KeyEvent e) {
        flipped = e.isAltDown();
    }

    public void onKeyUp(KeyEvent e) {
        flipped = e.isAltDown();
    }

    public void onClick(MouseEvent e) {
        flipped = e.isAltDown();
    }

    public void onDrag(MouseEvent e) {
        flipped = e.isAltDown();
    }

    public void onMove(MouseEvent e) {
        flipped = e.isAltDown();
    }
}
