package gui.ava.html.imagemap;

import java.net.URL;

import gui.ava.html.BaseTest;
import gui.ava.html.Html2Image;
import org.junit.jupiter.api.Test;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageMapImplTest extends BaseTest {

	@Test
	public void test1ImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromURL(getTest1Url());
		html2Image.getImageRenderer().saveImage(getTestOutputFile("test1.png"));
		html2Image.getHtmlImageMap().saveImageMapDocument(getTestOutputFile("test1.html"), "test1.png");
	}

	@Test
	public void googleImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromURL(new URL("https://www.google.com"));
		html2Image.getImageRenderer().saveImage(getTestOutputFile("google.png"));
		html2Image.getHtmlImageMap().saveImageMapDocument(getTestOutputFile("google.html"), "google.png");
	}

	@Test
	public void hebImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromHtml("<div>text<div style='text-align: right'><a onclick='alert(1)'>שלום!</a></div></div>");
		html2Image.getImageRenderer().saveImage(getTestOutputFile("heb.png"));
		html2Image.getHtmlImageMap().saveImageMapDocument(getTestOutputFile("heb.html"), "heb.png");
	}

	@Test
	public void imageImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromHtml("<div>HELLO!<a href='javascript: alert(1);'><img src='https://www.google.co.il/intl/en_com/images/srpr/logo1w.png'/></a></div>");
		html2Image.getImageRenderer().saveImage(getTestOutputFile("image.png"));
		html2Image.getHtmlImageMap().saveImageMapDocument(getTestOutputFile("image.html"), "heb.png");
	}

}
