package gui.ava.html.renderer;

import gui.ava.html.util.FormatNameUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yoav Aharoni
 */
public class FormatNameUtilTest {

	@Test
	public void testGif() {
		final String format = FormatNameUtil.formatForFilename("test.file.gif");
		Assertions.assertEquals("gif", format);
	}

	@Test
	public void testPng() {
		final String format = FormatNameUtil.formatForFilename("test.file.png");
		Assertions.assertEquals("png", format);
	}

	@Test
	public void testJpg() {
		final String format = FormatNameUtil.formatForFilename("test.file.jpg");
		Assertions.assertEquals("jpg", format);
	}

	@Test
	public void testNoName() {
		final String format = FormatNameUtil.formatForFilename(".gif");
		Assertions.assertEquals("gif", format);
	}

	@Test
	public void testNoExtension() {
		final String format = FormatNameUtil.formatForFilename("name.");
		Assertions.assertEquals("png", format);
	}

	@Test
	public void testEmptyFilename() {
		final String format = FormatNameUtil.formatForFilename("");
		Assertions.assertEquals("png", format);
	}

}
