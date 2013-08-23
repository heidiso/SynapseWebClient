package org.sagebionetworks.web.server.markdownparser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BacktickParser extends BasicMarkdownElementParser {
	Pattern p = Pattern.compile(MarkdownRegExConstants.BACKTICK_ESCAPED_REGEX);
	
	@Override
	public void processLine(MarkdownElements line, List<MarkdownElementParser> simpleParsers) {
		Matcher m = p.matcher(line.getMarkdown());
		line.updateMarkdown(m.replaceAll("&#96;"));
	}

}
