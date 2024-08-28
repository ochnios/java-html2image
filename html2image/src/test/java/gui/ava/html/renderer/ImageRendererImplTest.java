package gui.ava.html.renderer;

import java.io.FileOutputStream;

import gui.ava.html.BaseTest;
import gui.ava.html.parser.HtmlParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yoav Aharoni
 */
public class ImageRendererImplTest extends BaseTest {

	private HtmlParserImpl parser;
	private ImageRendererImpl renderer;

	@BeforeEach
	public void createParser() {
		parser = new HtmlParserImpl();
		renderer = new ImageRendererImpl(parser);
	}

	@Test
	public void testSaveStream() throws Exception {
		parser.load(getTest1Url());
		renderer.saveImage(new FileOutputStream(getTestOutputFile("file1.png")), true);
	}

	@Test
	public void testSaveFile() throws Exception {
		parser.load(getTest1Url());
		renderer.saveImage(getTestOutputFile("test.gif"));
		renderer.saveImage(getTestOutputFile("test.png"));
		renderer.saveImage(getTestOutputFile("test.jpg"));
		renderer.saveImage(getTestOutputFile("test.bmp"));
	}

}
