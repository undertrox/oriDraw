package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.origami.Crease;

public class RenderSettings {
    private static RenderSettings instance;

    private double EDGE_WIDTH = 0.7;
    private double FOLD_WIDTH = 0.7;
    private double AUX_WIDTH = 0.7;
    private double DEFAULT_WIDTH = 0.7;

    private double POINT_SIDELENGTH = 4;

    public static RenderSettings getInstance() {
        if (instance == null) {
            instance = new RenderSettings();
        }
        return instance;
    }

    public static ColorManager getColorManager() {
        return ColorManager.getInstance();
    }

    /**
     * Returns the line width for the Crease Type
     *
     * @param type: Crease Type for which to get the width
     * @return Line Width for type
     */
    public static double getWidthForCreaseType(Crease.Type type) {
        switch (type) {
            case MOUNTAIN:
            case VALLEY:
                return getInstance().FOLD_WIDTH;
            case EDGE:
                return getInstance().EDGE_WIDTH;
            case AUX:
                return getInstance().AUX_WIDTH;
            default:
                return getInstance().DEFAULT_WIDTH;
        }
    }

    public static double getPointSideLength() {
        return getInstance().POINT_SIDELENGTH;
    }
}
