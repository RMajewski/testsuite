/* 
* Copyright 2016 René Majewski
*  
* Lizenziert unter der EUPL, Version 1.1 oder - sobald diese von der
* Europäischen Kommission genehmigt wurden - Folgeversionen der EUPL
* ("Lizenz"); Sie dürfen dieses Werk ausschließlich gemäß dieser Lizenz
* nutzen. 
* 
* Eine Kopie der Lizenz finden Sie hier: 
* https://joinup.ec.europa.eu/software/page/eupl
*  
* Sofern nicht durch anwendbare Rechtsvorschriften gefordert oder in 
* schriftlicher Form vereinbart, wird die unter der Lizenz verbreitete 
* Software "so wie sie ist", OHNE JEGLICHE GEWÄHRLEISTUNG ODER BEDINGUNGEN -
* ausdrücklich oder stillschweigend - verbreitet.
* Die sprachspezifischen Genehmigungen und Beschränkungen unter der Lizenz
* sind dem Lizenztext zu entnehmen.
*/ 

package org.testsuite.helper;

import java.awt.Color;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testsuite.checksource.html.HtmlTodo;
import org.testsuite.data.Config;
import org.testsuite.data.TodoData;

/**
 * Formats the Java source code for the HTML output.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HelperHtmlCodeJava {
	/**
	 * Saves the HashMap for keywords and their colors.
	 */
	private HashMap<String, Color> _keywords;
	
	/**
	 * Specifies the color for comments
	 */
	public static final Color COLOR_COMMENTS = new Color(0x3f7f5f); 
	
	/**
	 * Specifies the color for Javadoc comments
	 */
	public static final Color COLOR_JAVADOC = new Color(0x3f5fbf); 
	
	/**
	 * Specifies the color for keywords
	 */
	public static final Color COLOR_KEYWORDS = new Color(0x7f007e);
	
	/**
	 * Saves the instance of this class
	 */
	private static HelperHtmlCodeJava _instance;

	/**
	 * Initialize the data
	 */
	private HelperHtmlCodeJava() {
		_keywords = new HashMap<String, Color>();
		String[] words = HelperJavaKeywords.getAllKeywords();
		for (int i = 0; i < words.length; i++)
			_keywords.put(words[i], COLOR_KEYWORDS);
	}
	
	/**
	 * Formatted the source code line
	 * 
	 * @param The line of code that is to be formatted.
	 * 
	 * @param multilineComment It is the line to a multiline comment?
	 * 
	 * @param javadoc It is the line to a Javadoc comment?
	 * 
	 * @return The formatted source code line
	 * 
	 * @deprecated Use
	 * {@link #formatString(String, boolean, boolean, int, String)}
	 */
	public static String formatString(String source, boolean multilineComment,
			boolean javadoc) {
		return formatString(source, multilineComment, javadoc, -1, null);
	}
	
	/**
	 * Formatted the source code line
	 * 
	 * @param The line of code that is to be formatted.
	 * 
	 * @param multilineComment It is the line to a multiline comment?
	 * 
	 * @param javadoc It is the line to a Javadoc comment?
	 * 
	 * @param lineNumber The number of source code line.
	 * 
	 * @param fileName The name of source file.
	 * 
	 * @return The formatted source code line.
	 */
	public static String formatString(String source, boolean multilineComment,
			boolean javadoc, int lineNumber, String fileName) {
		getInstance();
		
		if ((source == null) || source.isEmpty())
			return new String();
		
		StringBuilder ret = new StringBuilder();
		
		if (multilineComment)
			ret.append(_instance.formatSpan(source, COLOR_COMMENTS));
		else if (javadoc)
			ret.append(_instance.formatSpan(source, COLOR_JAVADOC));
		else {
			ret.append(_instance.formatKeywords(source, lineNumber, fileName));
		}
		
		return ret.toString();
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static HelperHtmlCodeJava getInstance() {
		if (_instance == null)
			_instance = new HelperHtmlCodeJava();
		return _instance;
	}
	
	/**
	 * Search for keywords and formats.
	 * 
	 * @param source The source code line
	 * 
	 * @return Formatted source code line
	 * 
	 * @deprecated use {@link #formatKeywords(String, int, String)}
	 */
	@SuppressWarnings("unused")
	private String formatKeywords(String source) {
		return formatKeywords(source, -1, null);
	}

	private String formatKeywords(String source, int lineNumber, 
			String fileName) {
		StringBuilder ret = new StringBuilder();
		
		int start = source.indexOf("//");

		String comment = new String();
		String src = new String();
		if (start >= 0) {
			String tmp = source.substring(start);
			if (Config.getInstance().toDoWordsCount() > 0) {
				int end = 0;
				for (int i = 0; i < Config.getInstance().toDoWordsCount(); i++) {
					int s = tmp.indexOf(Config.getInstance().getToDoWord(i), 
							end);
					if (s > 0) {
						if ((fileName != null) && (lineNumber > 0))
							HtmlTodo.getInstance().addTodo(new TodoData(source,
									lineNumber, fileName));
						if (end > 0)
							src += tmp.substring(end, s);
						else
							src = tmp.substring(0, s);

						src += "<span style=\"color: " + 
								formatColor(COLOR_JAVADOC) + ";\"><b>" +
								Config.getInstance().getToDoWord(i) + 
								"</b></span>";
						end = s + Config.getInstance().getToDoWord(i).length();
					}
				}
				src += source.substring(end);
			} else {
				src = source.substring(start);
			}
			comment = formatSpan(src, COLOR_COMMENTS);
			src = source.substring(0, start);
			start = 0;
		} else {
			src = source;
			start = 0;
		}
	
		Matcher matcher = Pattern.compile("\\b").matcher(src);
		while (matcher.find()) {
			int end = matcher.start();
			String sub = src.substring(start, end);
			start = matcher.end();
			
			Color color = _instance._keywords.get(sub);
			if (color != null)
				ret.append(formatSpan(sub, color));
			else
				ret.append(sub);
		}
		
		if (start < src.length())
			ret.append(src.substring(start));

		if (comment.length() > 0)
			ret.append(comment);
		
		return ret.toString();
	}
	
	/**
	 * Create a div with the specified text color for HTML output.
	 * 
	 * @param source Text to be formatted.
	 * 
	 * @param color The text color.
	 * 
	 * @return Formatted text for HTML output.
	 */
	private String formatSpan(String source, Color color) {
		StringBuilder ret = new StringBuilder("<span style=\"color: ");
		
		ret.append(_instance.formatColor(color));
		ret.append(";\">");
		ret.append(source);
		ret.append("</span>");
		
		return ret.toString();
	}
	
	/**
	 * Converts the color as a string for the HTML output.
	 * 
	 * @param color The color that want to convert.
	 * 
	 * @return The converted color as a string for the HTML output.
	 */
	public String formatColor(Color color) {
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(),
				color.getBlue());
	}
}
