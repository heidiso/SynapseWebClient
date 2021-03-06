package org.sagebionetworks.web.server.markdownparser;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.sagebionetworks.web.client.widget.entity.SharedMarkdownUtils;
import org.sagebionetworks.web.client.widget.entity.registration.WidgetConstants;
import org.sagebionetworks.web.shared.WebConstants;

public class SynapseMarkdownWidgetParser extends BasicMarkdownElementParser {
	Pattern p= Pattern.compile(MarkdownRegExConstants.SYNAPSE_MARKDOWN_WIDGET_REGEX, Pattern.CASE_INSENSITIVE);
	MarkdownExtractor extractor;
	
	@Override
	public void reset(List<MarkdownElementParser> simpleParsers) {
		extractor = new MarkdownExtractor();
	}
	
	private String getCurrentDivID() {
		return WebConstants.DIV_ID_WIDGET_SYNTAX_PREFIX + extractor.getCurrentContainerId() + SharedMarkdownUtils.getPreviewSuffix(isPreview);
	}

	@Override
	public void processLine(MarkdownElements line) {
		String markdown = line.getMarkdown();
		Matcher m = p.matcher(markdown);
		StringBuffer sb = new StringBuffer();
		while(m.find()) {				
			StringBuilder html = new StringBuilder();
			html.append(m.group(2));
			extractor.putContainerIdToContent(getCurrentDivID(), html.toString());
			
			String containerElement = extractor.getNewElementStart(getCurrentDivID()) + extractor.getContainerElementEnd();
			m.appendReplacement(sb, containerElement);
		}
		m.appendTail(sb);
		line.updateMarkdown(sb.toString());
	}
	
	@Override
	public void completeParse(Document doc) {
		Set<String> ids = extractor.getContainerIds();
		//For each widget syntax found, wrap with appropriate widget containers for renderers
		for(String key: ids) {
			boolean inlineWidget = false;
			Element el = doc.getElementById(key);
			Node childNode = (Node) el;
			if(el != null) {
				String content = extractor.getContent(key);
				int syntaxPrefixLen = WebConstants.DIV_ID_WIDGET_SYNTAX_PREFIX.length();
				//Extract just the id + suffix
				String id = key.substring(syntaxPrefixLen);
				String widgetHtml = SharedMarkdownUtils.getWidgetHTML(id, content);
				if(content.contains(WidgetConstants.INLINE_WIDGET_KEY)) {
					inlineWidget = true;
				}
				
				StringBuilder outerContainer = new StringBuilder();
				if(inlineWidget) {
					outerContainer.append("<span></span>");
				} else {
					outerContainer.append("<div></div>");
				}
				
				//Surround with widget holder and appropriate container
				childNode.wrap(outerContainer.toString()).wrap(widgetHtml);
				childNode.remove();
			}	
		}
	}

}
