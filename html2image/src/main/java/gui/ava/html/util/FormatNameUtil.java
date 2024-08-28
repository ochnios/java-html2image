
package gui.ava.html.util;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yoav Aharoni
 */
public enum FormatNameUtil { ;

    public static final String DEFAULT_FORMAT = "png";

    public static final Map<String,String> types = Map.of(
            "gif", "gif",
            "jpg", "jpg",
            "jpeg", "jpg",
            "png", "png",
            "bmp", "bmp"
    );

    public static String formatForExtension(String extension) {
        Objects.requireNonNull(extension);
        final String type = types.get(extension);
        if (type == null) {
            return DEFAULT_FORMAT;
        }
        return type;
    }

    public static String formatForFilename(String fileName) {
        if (fileName == null) return DEFAULT_FORMAT;
        final int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return DEFAULT_FORMAT;
        }
        final String ext = fileName.substring(dotIndex + 1);
        return formatForExtension(ext);
    }

}
