package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.ui.theme.LineStyle;
import de.undertrox.oridraw.ui.theme.PointStyle;
import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Transform;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.canvas.GraphicsContext;

public class RenderHelper {
    /**
     * Draw a line in the GraphicsContext gc
     *
     * @param line:  line to draw
     * @param st: Line Style
     * @param gc:    GraphicsContext in which to draw the line
     */
    public static void drawLine(Line line, LineStyle st, GraphicsContext gc, Transform transform) {
        gc.setStroke(st.getPaint());
        gc.setLineWidth(st.getWidth() / transform.getScale());
        double[] dashes = st.getDashes().clone();
        for (int i = 0; i < dashes.length; i++) {
            dashes[i] /= transform.getScale();
        }
        gc.setLineDashes(dashes);
        Vector s = line.getStartPoint();
        Vector e = line.getEndPoint();
        gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    /**
     * Draw a square in the GraphicsContext gc
     *
     * @param center:     Center of the Square
     * @param style : Style of the point
     * @param gc:         GraphicsContext in which to draw the square
     */
    public static void drawPoint(Vector center, PointStyle style, GraphicsContext gc, Transform transform) {
        gc.setFill(style.getColor());
        double sideLength = 2*style.getRadius() / transform.getScale();
        switch (style.getShape()) {
            case SQUARE:
                gc.fillRect(center.getX() - sideLength / 2, center.getY() - sideLength / 2, sideLength, sideLength);
                break;
            case CIRCLE:
                gc.fillOval(center.getX() - sideLength / 2, center.getY() - sideLength / 2, sideLength, sideLength);
        }
    }

    // Prevent instantiation
    private RenderHelper()  {}
}
