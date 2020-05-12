package de.undertrox.oridraw.render;

import de.undertrox.oridraw.math.Line;
import de.undertrox.oridraw.math.Vector;
import de.undertrox.oridraw.origami.Crease;
import de.undertrox.oridraw.origami.CreasePattern;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CreasePatternRenderer implements Renderer {
    private Canvas canvas;
    private GraphicsContext gc;
    private CreasePattern cp;
    private double height;
    private double width;
    private double scale;
    private Vector translate;
    private Paint background;

    public CreasePatternRenderer(Canvas canvas, CreasePattern cp) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        this.cp = cp;
        scale = 1;
        background = ColorManager.CP_EDITOR_BACKGROUND_COLOR;
        translate = new Vector(300, 250);
    }

    @Override
    public void draw(Vector mousePos) {
        height = canvas.getHeight() - 32;
        width = canvas.getWidth();
        gc.setFill(background);
        gc.fillRect(0, 0, width, height);
        for (Crease crease : cp.getCreases()) {
            Paint p = ColorManager.getPaintForCreaseType(crease.getType());
            drawLine(crease.getLine(), p);
        }
    }

    public void drawLine(Line line, Paint color) {
        gc.setStroke(color);
        gc.setLineWidth(1);
        Vector s = transformPoint(line.getStartPoint());
        Vector e = transformPoint(line.getEndPoint());
        gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    private Vector transformPoint(Vector p) {
        return p.scale(scale).add(translate);
    }
}
