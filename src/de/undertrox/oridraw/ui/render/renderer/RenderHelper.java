package de.undertrox.oridraw.ui.render.renderer;

import de.undertrox.oridraw.util.math.Line;
import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class RenderHelper {
    /**
     * Draw a line in the GraphicsContext gc
     *
     * @param line:  line to draw
     * @param color: Color of the line
     * @param width: Stroke width of the line
     * @param gc:    GraphicsContext in which to draw the line
     */
    public static void drawLine(Line line, Paint color, double width, GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        Vector s = line.getStartPoint();
        Vector e = line.getEndPoint();
        gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    /**
     * Draw a square in the GraphicsContext gc
     *
     * @param center:     Center of the Square
     * @param color:      Color of the Square
     * @param sideLength: Side length of the square
     * @param gc:         GraphicsContext in which to draw the square
     */
    public static void drawSquare(Vector center, Paint color, double sideLength, GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(center.getX() - sideLength / 2, center.getY() - sideLength / 2, sideLength, sideLength);
    }
}
