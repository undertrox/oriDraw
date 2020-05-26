package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.origami.OriLine;
import de.undertrox.oridraw.ui.theme.ColorManager;

public class RenderSettings {
    private static RenderSettings instance;

    private double edgeWidth = 0.7;
    private double creaseWidth = 0.7;
    private double auxWidth = 0.7;
    private double defaultWidth = 0.7;

    private double pointSidelength = 4;

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
     * Returns the line width for the OriLine Type
     *
     * @param type: OriLine Type for which to get the width
     * @return Line Width for type
     */
    public static double getWidthForCreaseType(OriLine.Type type) {
        switch (type) {
            case MOUNTAIN:
            case VALLEY:
                return getInstance().creaseWidth;
            case EDGE:
                return getInstance().edgeWidth;
            case AUX:
                return getInstance().auxWidth;
            default:
                return getInstance().defaultWidth;
        }
    }

    public static double getPointSideLength() {
        return getInstance().pointSidelength;
    }
}
