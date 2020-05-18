package de.undertrox.oridraw.origami.tool;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.origami.Document;
import de.undertrox.oridraw.ui.render.Transform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


/**
 * This is a CreasePatternTool that has a specific OriLine Type assigned to it.
 */
public abstract class TypedCreasePatternTool extends CreasePatternTool {
    OriLine.Type type;
    boolean flipped;


    public TypedCreasePatternTool(Document doc, Transform cpTransform, OriLine.Type type) {
        super(doc, cpTransform);
        this.type = type;
        flipped = false;
    }

    public OriLine.Type getType() {
        return flipped ? type.flip() : type;
    }

    public void setType(OriLine.Type type) {
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
