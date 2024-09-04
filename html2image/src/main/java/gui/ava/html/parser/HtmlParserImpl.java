package gui.ava.html.parser;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import org.apache.xerces.parsers.DOMParser;
import org.cyberneko.html.HTMLConfiguration;

/**
 * @author Yoav Aharoni
 */
public class HtmlParserImpl implements HtmlParser {

	private DOMParser domParser;
	private Document document;

	public HtmlParserImpl() {
		try {
			domParser = new DOMParser(new HTMLConfiguration()); 	// HtmlUnit 1.9.x
			// domParser = new DOMParser(HTMLDocumentImpl.class); 	// HtmlUnit 4.x
			domParser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
		}
		catch (SAXNotRecognizedException | SAXNotSupportedException e) {
			throw new ParseException("Can't create HtmlParserImpl", e);
		}
    }

	@Override
	public DOMParser getDomParser() {
		return domParser;
	}

	@Override
	public void setDomParser(DOMParser domParser) {
		this.domParser = domParser;
	}

	@Override
	public Document getDocument() {
		return document;
	}

	@Override
	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public void load(Reader reader) {
        try (reader) {
            domParser.parse(new InputSource(reader));
            document = domParser.getDocument();
        } catch (SAXException e) {
            throw new ParseException("SAXException while parsing HTML.", e);
        } catch (IOException e) {
            throw new ParseException("IOException while parsing HTML.", e);
        }
	}

	@Override
	public void load(InputStream inputStream) {
        try (inputStream) {
            domParser.parse(new InputSource(inputStream));
            document = domParser.getDocument();
        } catch (SAXException e) {
            throw new ParseException("SAXException while parsing HTML.", e);
        } catch (IOException e) {
            throw new ParseException("IOException while parsing HTML.", e);
        }
	}

	@Override
	public void loadURI(String uri) {
		try {
			domParser.parse(new InputSource(uri));
			document = domParser.getDocument();
		} catch (SAXException | IOException e) {
			throw new ParseException(format("SAXException while parsing HTML from \"%s\".", uri), e);
		}
    }

	@Override
	public void load(File file) {
		load(file.toURI());
	}

	@Override
	public void load(URL url) {
		loadURI(url.toExternalForm());
	}

	@Override
	public void load(URI uri) {
		loadURI(uri.toString());
	}

	@Override
	public void loadHtml(String html) {
		load(new StringReader(html));
	}

}
