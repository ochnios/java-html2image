package gui.ava.html.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URI;

import org.w3c.dom.Document;

import gui.ava.html.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yoav Aharoni
 */
public class HtmlParserImplTest extends BaseTest {

	private HtmlParserImpl parser;

	@BeforeEach
	public void createParser() {
		parser = new HtmlParserImpl();
	}

	@Test
	public void testLoadURI() throws Exception {
		parser.load(getTest1Url().toURI());
		assertTest1();
	}

	@Test
	public void testLoadExternalURL() throws Exception {
		parser.load(new URI("https://www.google.co.il").toURL());
		assertTrue(getDocument().getElementsByTagName("div").getLength() > 0);
	}

	@Test
	public void testLoadFile() throws Exception {
		parser.load(getTest1Url());
		assertTest1();
	}

	@Test
	public void testLoadReader() throws Exception {
		parser.load(new FileReader(getTest1File()));
		assertTest1();
	}

	@Test
	public void testLoadInputStream() throws Exception {
		parser.load(new FileInputStream(getTest1File()));
		assertTest1();
	}

	@Test
	public void testLoadHtml() throws Exception {
		parser.loadHtml("<b>Hello</b>");
		assertEquals(getDocument().getElementsByTagName("b").item(0).getTextContent(), "Hello");
	}

	private void assertTest1() {
		assertEquals(getDocument().getElementsByTagName("strong").item(0).getTextContent(), "Hello");
	}

	private Document getDocument() {
		return parser.getDocument();
	}

}
