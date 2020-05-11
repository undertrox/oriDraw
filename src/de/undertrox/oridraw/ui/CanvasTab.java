package de.undertrox.oridraw.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CanvasTab extends Tab {
    private Canvas canvas;

    public CanvasTab(String title, Canvas canvas, TabPane tabPane) {
        super(title, canvas);
        this.canvas = canvas;
        canvas.widthProperty().bind(tabPane.widthProperty());
        canvas.heightProperty().bind(tabPane.heightProperty());
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
