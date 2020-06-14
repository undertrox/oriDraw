package de.undertrox.oridraw.util.io.export;

import de.undertrox.oridraw.ui.theme.LineStyle;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SVGHelper {
    private static Logger logger = LogManager.getLogger();

    public static String toSVGColor(Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public static String toSVGStyle(LineStyle style) {
        String color = "black";
        if (style.getPaint() instanceof Color) {
            color = toSVGColor((Color) style.getPaint());
        } else {
            logger.warn("Could not convert Paint {} into an SVG color string. Defaulting to black", style.getPaint());
        }
        String[] dashes = new String[style.getDashes().length];
        for (int i = 0; i < style.getDashes().length; i++) {
            dashes[i] = String.valueOf(style.getDashes()[i]);
        }
        return String.format("stroke:%s;stroke-width:%s;stroke-dasharray:%s", color, style.getWidth(), String.join(" ", dashes));
    }

    // Prevent instantiation
    private SVGHelper() {}
}
