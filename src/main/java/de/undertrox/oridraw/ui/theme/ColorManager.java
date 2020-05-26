package de.undertrox.oridraw.ui.theme;

import de.undertrox.oridraw.origami.OriLine;

public class ColorManager {
    private static ColorManager instance;

    private Theme activeTheme = Theme.DARK;

    public LineStyle getEdgeStyle() {
        return getActiveTheme().getEdge();
    }

    public Theme getActiveTheme() {
        return getInstance().activeTheme;
    }

    public void setActiveTheme(Theme theme) {
        getInstance().activeTheme = theme;
    }

    /**
     * Returns Paint to use for the OriLine type
     *
     * @param type: OriLine type
     * @return Paint to use for type
     */
    public LineStyle getLineStyleForCreaseType(OriLine.Type type) {
        switch (type) {
            case EDGE:
                return getActiveTheme().getEdge();
            case MOUNTAIN:
                return getActiveTheme().getMountain();
            case VALLEY:
                return getActiveTheme().getValley();
            case AUX:
                return getActiveTheme().getAux();
            default:
                return getActiveTheme().getUnknown();
        }
    }

    public static ColorManager getInstance() {
        if (instance == null) {
            instance = new ColorManager();;
        }
        return instance;
    }

}
