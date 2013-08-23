package org.sagebionetworks.web.unitserver.markdownparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.sagebionetworks.web.client.widget.entity.registration.WidgetEncodingUtil;
import org.sagebionetworks.web.server.ServerMarkdownUtils;
import org.sagebionetworks.web.server.markdownparser.LinkParser;
import org.sagebionetworks.web.server.markdownparser.MarkdownElements;

public class LinkParserTest {
	LinkParser parser;
	
	@Before
	public void setup(){
		parser = new LinkParser();
		parser.reset();
	}
	
	@Test
	public void testLink(){
		String text = "This Is A Test";
		String href = "http://example.com";
		String line = "[" + text + "](" + href +")";
		MarkdownElements elements = new MarkdownElements(line);
		parser.processLine(elements, null);
		String result = elements.getHtml();
		assertTrue(!result.contains("http://example.com"));
		assertTrue(result.contains(ServerMarkdownUtils.START_CONTAINER));
		assertTrue(result.contains(ServerMarkdownUtils.END_CONTAINER));
		
		Document doc = Jsoup.parse(result);
		parser.completeParse(doc);
		assertTrue(doc.html().contains("http://example.com"));
		assertTrue(doc.html().contains("<a"));
		assertTrue(doc.html().contains("</a>"));
	}
	
	@Test
	public void testForCompleteness() {
		String text = "Test";
		String href = "example.com";
		String line = "[" + text + "](" + href +")";
		MarkdownElements elements = new MarkdownElements(line);
		parser.processLine(elements, null);
		String result = elements.getHtml();
		Document doc = Jsoup.parse(result);
		parser.completeParse(doc);
		assertTrue(doc.html().contains("http://example.com"));
		
		String text2 = "Test";
		String href2 = "ftp://ftp.example";
		String line2 = "[" + text2 + "](" + href2 +")";
		MarkdownElements elements2 = new MarkdownElements(line2);
		parser.processLine(elements2, null);
		String result2 = elements2.getHtml();
		Document doc2 = Jsoup.parse(result2);
		parser.completeParse(doc2);
		assertTrue(doc2.html().contains("ftp://ftp.example"));
		
	}
	
	@Test
	public void testBookmarkAndLink() {
		String line = "I want to refer to [this](#Bookmark:subject1). To see official page, go [here](http://example.com).";
		MarkdownElements elements = new MarkdownElements(line);
		parser.processLine(elements, null);
		String result = elements.getMarkdown();
		assertTrue(result.contains("${bookmark?text=this&inlineWidget=true&bookmarkID=subject1}"));
		assertTrue(!result.contains("http://example.com"));
		assertTrue(result.contains(ServerMarkdownUtils.START_CONTAINER));
		assertTrue(result.contains(ServerMarkdownUtils.END_CONTAINER));
	}
	
}
