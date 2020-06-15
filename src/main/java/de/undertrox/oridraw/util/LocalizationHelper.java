package de.undertrox.oridraw.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
    private static ResourceBundle bundle;
    private static Logger logger = LogManager.getLogger();

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
        logger.warn("Missing Localization Value! {}", key);
        return key;
    }

    public static Locale getLocale() {
        return bundle.getLocale();
    }
}
