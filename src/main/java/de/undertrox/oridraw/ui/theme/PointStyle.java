package de.undertrox.oridraw.ui.theme;

import javafx.scene.paint.Paint;

public class PointStyle {
    private double radius;
    private Paint color;
    private Shape shape;

    public PointStyle(Paint color, double radius, Shape shape) {
        this.radius = radius;
        this.color = color;
        this.shape = shape;
    }

    public double getRadius() {
        return radius;
    }

    public Paint getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    public enum Shape {
        CIRCLE, SQUARE
    }
}
