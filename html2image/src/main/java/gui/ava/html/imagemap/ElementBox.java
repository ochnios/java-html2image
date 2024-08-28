package gui.ava.html.imagemap;

import java.util.Collection;

import org.w3c.dom.Element;

/**
 * @author Yoav Aharoni
 */
public class ElementBox {

	private final Element element;
	private final int left;
	private final int top;
	private final int width;
	private final int height;

	public ElementBox(Element element, int left, int top, int width, int height) {
		this.element = element;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	public Element getElement() {
		return element;
	}

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRight() {
		return left + width;
	}

	public int getBottom() {
		return top + height;
	}

	public boolean isEmpty() {
		return width <= 0 || height <= 0;
	}

	public boolean containedIn(ElementBox box) {
		return containedIn(this, box);
	}

	public boolean containedIn(Collection<ElementBox> elementBoxes) {
		return elementBoxes.stream().anyMatch(this::containedIn);
	}

	/**
	 * @param box box to test
	 * @param other the other {@link ElementBox}
	 * @return true if box is contained inside the other {@link ElementBox}
	 */
	public static boolean containedIn(ElementBox box, ElementBox other) {
		return box.getTop() >= other.getTop() && box.getLeft() >= other.getTop()
				&& box.getBottom() <= other.getBottom() && box.getRight() <= other.getRight();
	}

}
