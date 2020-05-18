package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.origami.OriLine;
import javafx.scene.paint.Color;

public class ColorManager {
    private static ColorManager instance;

    public Color EDGE_COLOR = Color.BLACK;
    public Color MOUNTAIN_COLOR = Color.RED;
    public Color VALLEY_COLOR = Color.BLUE;
    public Color AUX_COLOR = Color.GRAY;
    public Color UNKNOWN_COLOR = Color.YELLOW;

    public Color POINT_COLOR = Color.BLACK;
    public Color TO_BE_SELECTED_COLOR = Color.LIMEGREEN;
    public Color SELECTED_COLOR = Color.GREEN;

    public Color CP_EDITOR_BACKGROUND_COLOR = Color.WHITE;

    /**
     * Returns Paint to use for the OriLine type
     *
     * @param type: OriLine type
     * @return Paint to use for type
     */
    public Color getPaintForCreaseType(OriLine.Type type) {
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
