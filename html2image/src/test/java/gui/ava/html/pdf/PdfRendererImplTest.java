package gui.ava.html.pdf;

import gui.ava.html.BaseTest;
import gui.ava.html.Html2Image;
import org.junit.jupiter.api.Test;

/**
 * @author Yoav Aharoni
 */
public class PdfRendererImplTest extends BaseTest {

	@Test
	public void testSaveToPDF() {
		Html2Image.fromURL(getTest1Url()).getPdfRenderer().saveToPDF(getTestOutputFile("test.pdf"));
	}

}
