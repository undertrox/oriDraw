package de.undertrox.oridraw.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
    private static ResourceBundle bundle;

    public static void setBundle(ResourceBundle bundle) {
        LocalizationHelper.bundle = bundle;
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static String getString(String key) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        return key;
    }

    public static Locale getLocale() {
        return bundle.getLocale();
    }
}
