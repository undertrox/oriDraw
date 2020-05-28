package de.undertrox.oridraw.ui.render.settings;

import de.undertrox.oridraw.ui.theme.ColorManager;

public class RenderSettings {
    private static RenderSettings instance;

    public static RenderSettings getInstance() {
        if (instance == null) {
            instance = new RenderSettings();
        }
        return instance;
    }

    public static ColorManager getColorManager() {
        return ColorManager.getInstance();
    }
}
