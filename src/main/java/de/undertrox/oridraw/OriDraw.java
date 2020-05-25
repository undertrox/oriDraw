package de.undertrox.oridraw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OriDraw {
    public static final String VERSION = "0.0.2";
    private static Logger logger = LogManager.getLogger(OriDraw.class);

    public static Logger getLogger() {
        return logger;
    }

    private OriDraw() {}
}
