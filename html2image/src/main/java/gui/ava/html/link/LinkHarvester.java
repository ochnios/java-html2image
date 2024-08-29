package gui.ava.html.link;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;

/**
 * @author Yoav Aharoni
 */
public class LinkHarvester {

    private final JTextComponent textComponent;
    private final List<LinkInfo> links = new ArrayList<>();

    public LinkHarvester(JEditorPane textComponent) {
        this.textComponent = textComponent;
        harvestElement(textComponent.getDocument().getDefaultRootElement());
    }

    public List<LinkInfo> getLinks() {
        return links;
    }

    private void harvestElement(Element element) {
        if (element == null) {
            return;
        }

        final AttributeSet attributes = element.getAttributes();
        final Enumeration<?> attributeNames = attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            final Object key = attributeNames.nextElement();
            if (HTML.Tag.A.equals(key)) {
                final Object value = attributes.getAttribute(key);
                if (value instanceof SimpleAttributeSet attributeSet) {
                    final String href = (String) attributeSet.getAttribute(HTML.Attribute.HREF);
                    final String title = (String) attributeSet.getAttribute(HTML.Attribute.TITLE);
                    final List<Rectangle2D> bounds = elementBounds(element);
                    links.add(new LinkInfo(href, title, bounds));
                }
            }
        }

        for (int i = 0; i < element.getElementCount(); i++) {
            final Element child = element.getElement(i);
            harvestElement(child);
        }
    }

    private List<Rectangle2D> elementBounds(Element element) {
        final List<Rectangle2D> bounds = new ArrayList<>();
        try {
            final int startOffset = element.getStartOffset();
            final int endOffset = element.getEndOffset();
            Rectangle2D rectangle = textComponent.modelToView2D(startOffset);
            for (int i = startOffset + 1; i <= endOffset; i++) {
                final Rectangle2D temp = textComponent.modelToView2D(i);
                if (temp.getY() == rectangle.getY()) {
                    Rectangle2D.union(rectangle, temp, rectangle);
                } else {
                    bounds.add(rectangle);
                    rectangle = null;
                }
            }
            if (rectangle != null) {
                bounds.add(rectangle);
            }
            return bounds;
        } catch (BadLocationException e) {
            throw new RuntimeException("Got BadLocationException", e);
        }
    }

}
