package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.origami.OriLine;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorManager {
    private static ColorManager instance;

    private Paint edgeColor = Color.BLACK;
    private Paint mountainColor = Color.RED;
    private Paint valleyColor = Color.BLUE;
    private Paint auxColor = Color.GRAY;
    private Paint unknownColor = Color.YELLOW;

    private Paint pointColor = Color.BLACK;
    private Paint toBeSelectedColor = Color.LIMEGREEN;
    private Paint selectedColor = Color.LIMEGREEN;

    private Paint cpEditorBackgroundColor = Color.WHITE;

    public Paint getEdgeColor() {
        return edgeColor;
    }

    public Paint getMountainColor() {
        return mountainColor;
    }

    public Paint getValleyColor() {
        return valleyColor;
    }

    public Paint getAuxColor() {
        return auxColor;
    }

    public Paint getUnknownColor() {
        return unknownColor;
    }

    public Paint getPointColor() {
        return pointColor;
    }

    public Paint getToBeSelectedColor() {
        return toBeSelectedColor;
    }

    public Paint getSelectedColor() {
        return selectedColor;
    }

    public Paint getCpEditorBackgroundColor() {
        return cpEditorBackgroundColor;
    }

    /**
     * Returns Paint to use for the OriLine type
     *
     * @param type: OriLine type
     * @return Paint to use for type
     */
    public Paint getPaintForCreaseType(OriLine.Type type) {
        switch (type) {
            case EDGE:
                return getInstance().edgeColor;
            case MOUNTAIN:
                return getInstance().mountainColor;
            case VALLEY:
                return getInstance().valleyColor;
            case AUX:
                return getInstance().auxColor;
            default:
                return getInstance().unknownColor;
        }
    }

    static ColorManager getInstance() {
        if (instance == null) {
            instance = new ColorManager();
        }
        return instance;
    }

}
