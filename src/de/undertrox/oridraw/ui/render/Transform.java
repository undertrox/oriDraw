package de.undertrox.oridraw.ui.render;

import de.undertrox.oridraw.util.math.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Translate;


public class Transform {
    private Vector move;
    private double scale;
    private double rotation;

    public Transform(Vector move, double scale, double rotation) {
        this.move = move;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Vector getMove() {
        return move;
    }

    public void setMove(Vector move) {
        this.move = move;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Applies transformation (translation, rotation, scaling) to the Point p
     *
     * @param p: Point to be transformed
     * @return transformed Point
     */
    public Vector apply(Vector p) {
        return p.scale(scale).add(move);
    }

    public Vector applyInverted(Vector p) {
        return p.add(move.invertSign()).scale(1 / scale);
    }

    /**
     * Applies transformation (translation, rotation, scaling) to the GraphicsContext gc
     *
     * @param gc: GraphicsContext to apply the transformation to
     */
    public void apply(GraphicsContext gc) {
        Translate t = new Translate(move.getX(), move.getY());
        gc.setTransform(t.getMxx(), t.getMyx(), t.getMxy(), t.getMyy(), t.getTx(), t.getTy());
        gc.rotate(rotation);
        gc.scale(scale, scale);
    }

    /**
     * zooms the Transform in/out.
     *
     * @param center: Point to zoom in on
     * @param delta   : scale delta. negative to zoom out, positive to zoom in
     */
    public void zoom(Vector center, double delta) {
        double oldScale = scale;
        if (delta < 0) {
            scale /= 1 - delta;
        } else {
            scale *= 1 + delta;
        }
        double scalechange = oldScale - scale;
        double offsetX = (center.getX() * scalechange);
        double offsetY = (center.getY() * scalechange);

        move = move.add(new Vector(offsetX, offsetY));
    }
}
