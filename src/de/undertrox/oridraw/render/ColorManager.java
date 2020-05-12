package de.undertrox.oridraw.render;

import de.undertrox.oridraw.origami.Crease;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorManager {

    public static Paint EDGE_COLOR = Color.BLACK;
    public static Paint MOUNTAIN_COLOR = Color.RED;
    public static Paint VALLEY_COLOR = Color.BLUE;
    public static Paint AUX_COLOR = Color.GRAY;
    public static Paint UNKNOWN_COLOR = Color.GRAY;

    public static Paint CP_EDITOR_BACKGROUND_COLOR = Color.WHITE;

    public static Paint getPaintForCreaseType(Crease.Type type) {
        switch (type) {
            case EDGE:
                return EDGE_COLOR;
            case MOUNTAIN:
                return MOUNTAIN_COLOR;
            case VALLEY:
                return VALLEY_COLOR;
            case AUX:
                return AUX_COLOR;
            default:
                return UNKNOWN_COLOR;
        }
    }
}
