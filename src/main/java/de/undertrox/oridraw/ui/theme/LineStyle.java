package de.undertrox.oridraw.ui.theme;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class LineStyle {
    private Paint paint;
    private double width;
    private double[] dashes;

    public LineStyle(Paint paint, double width, double... dashes) {
        this.paint = paint;
        this.width = width;
        this.dashes = dashes;
    }

    public Paint getPaint() {
        return paint;
    }

    public double getWidth() {
        return width;
    }

    public double[] getDashes() {
        return dashes;
    }

    public void apply(GraphicsContext gc) {
        gc.setStroke(getPaint());
        gc.setLineWidth(getWidth());
        gc.setLineDashes(dashes);
    }

    public LineStyle previewStyle() {
        Paint p = paint;
        if (p instanceof Color) {
            Color c = (Color) p;
            p = Color.color(c.getRed(), c.getGreen(), c.getBlue(), 0.5);
        }
        return new LineStyle(p, getWidth(), getDashes());
    }
}
