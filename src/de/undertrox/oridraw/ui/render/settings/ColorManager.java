package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.origami.Crease;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorManager {
    private static ColorManager instance;

    public Paint EDGE_COLOR = Color.BLACK;
    public Paint MOUNTAIN_COLOR = Color.RED;
    public Paint VALLEY_COLOR = Color.BLUE;
    public Paint AUX_COLOR = Color.GRAY;
    public Paint UNKNOWN_COLOR = Color.GRAY;

    public Paint POINT_COLOR = Color.BLACK;
    public Paint TO_BE_SELECTED_POINT_COLOR = Color.LIMEGREEN;
    public Paint SELECTED_POINT_COLOR = Color.GREEN;

    public Paint CP_EDITOR_BACKGROUND_COLOR = Color.WHITE;

    /**
     * Returns Paint to use for the Crease type
     *
     * @param type: Crease type
     * @return Paint to use for type
     */
    public Paint getPaintForCreaseType(Crease.Type type) {
        switch (type) {
            case EDGE:
                return getInstance().EDGE_COLOR;
            case MOUNTAIN:
                return getInstance().MOUNTAIN_COLOR;
            case VALLEY:
                return getInstance().VALLEY_COLOR;
            case AUX:
                return getInstance().AUX_COLOR;
            default:
                return getInstance().UNKNOWN_COLOR;
        }
    }

    static ColorManager getInstance() {
        if (instance == null) {
            instance = new ColorManager();
        }
        return instance;
    }
}
