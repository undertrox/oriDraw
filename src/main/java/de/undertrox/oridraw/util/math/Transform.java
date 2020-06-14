package de.undertrox.oridraw.util.math;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Translate;


public class Transform {
    private Vector translation;
    private Vector scale;
    private double rotation;

    public Transform(Vector translation, Vector scale, double rotation) {
        this.translation = translation;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Transform(Vector translation, double scale, double rotation) {
        this(translation, new Vector(scale), rotation);
    }

    public Vector getTranslation() {
        return translation;
    }

    public void setTranslation(Vector translation) {
        this.translation = translation;
    }

    public Vector getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
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
        return p.scale(scale).add(translation);
    }

    public Vector applyInverted(Vector p) {
        return p.add(translation.invertSign()).scale(scale.inverse());
    }

    /**
     * Applies transformation (translation, rotation, scaling) to the GraphicsContext gc
     *
     * @param gc: GraphicsContext to apply the transformation to
     */
    public void apply(GraphicsContext gc) {
        Translate t = new Translate(translation.getX(), translation.getY());
        gc.setTransform(t.getMxx(), t.getMyx(), t.getMxy(), t.getMyy(), t.getTx(), t.getTy());
        gc.rotate(rotation);
        gc.scale(scale.getX(), scale.getY());
    }

    /**
     * zooms the Transform in/out.
     *
     * @param center: Point to zoom in on
     * @param delta   : scale delta. negative to zoom out, positive to zoom in
     */
    public void zoom(Vector center, double delta) {
        Vector oldScale = scale;
        if (delta < 0) {
            scale = scale.scale(1/(1 - delta));
        } else {
            scale = scale.scale(1 + delta);
        }
        Vector scaleChange = oldScale.sub(scale);
        double offsetX = (center.getX() * scaleChange.getX());
        double offsetY = (center.getY() * scaleChange.getY());

        translation = translation.add(new Vector(offsetX, offsetY));
    }
}
