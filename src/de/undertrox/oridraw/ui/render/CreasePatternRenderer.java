package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.math.Line;
import de.undertrox.oridraw.math.Vector;
import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.CreasePattern;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class CreasePatternRenderer extends Renderer {
    private CreasePattern cp;

    public CreasePatternRenderer(Transform t, CreasePattern cp) {
        super(t);
        this.cp = cp;
    }

    @Override
    protected void draw() {
        getGc().fillRect(0, 0, getWidth(), getHeight());
        for (Crease crease : cp.getCreases()) {
            Paint p = ColorManager.getPaintForCreaseType(crease.getType());
            drawLine(crease.getLine(), p);
        }
    }

    public void drawLine(Line line, Paint color) {
        GraphicsContext gc = getGc();
        gc.setStroke(color);
        gc.setLineWidth(1);
        Vector s = line.getStartPoint();
        Vector e = line.getEndPoint();
        gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

}
