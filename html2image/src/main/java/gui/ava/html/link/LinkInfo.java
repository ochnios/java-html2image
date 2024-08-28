package gui.ava.html.link;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public class LinkInfo {

    private final String href;
    private final String title;
    private final List<Rectangle2D> bounds;

    public LinkInfo(String href, String title, List<Rectangle2D> bounds) {
        this.href = href;
        this.title = title;
        this.bounds = bounds;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public List<Rectangle2D> getBounds() {
        return bounds;
    }

}
