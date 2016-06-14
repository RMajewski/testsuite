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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.testsuite.checksource.CSMethod;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;

/**
 * Implements methods that can assist in managing the HTML output.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class HelperHtml {
	/**
	 * Load the text file and returned as string.
	 * 
	 * @param name Name of the file
	 * 
	 * @return Loaded file as string
	 */
	public static String readFile(String name) {
		StringBuilder ret = new StringBuilder();
		
		BufferedReader br = null;
		try {
			URL url = HelperHtml.class.getClassLoader().getResource(name);
			br = new BufferedReader(new FileReader(url.getFile()));
			if (br.ready()) {
				String line;
				while ((line = br.readLine()) != null) {
					ret.append(line);
					ret.append(System.lineSeparator());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret.toString();
	}
	
	/**
	 * Generates the head of HTML output file
	 * 
	 * @param head Headline
	 * 
	 * @param description Description of the HTML file.
	 * 
	 * @return Head of HTML output file
	 */
	public static String head(String head, String description) {
		StringBuilder ret = new StringBuilder();
		
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 ");
		ret.append("Transitional//EN\" \"http://www.w3.org/TR/html4/");
		ret.append("transitional.dtd\">");
		ret.append(System.lineSeparator());
		
		ret.append("<html>"); ret.append(System.lineSeparator());
		ret.append("\t<head>"); ret.append(System.lineSeparator());
		ret.append("\t\t<title>");
		ret.append(head);
		ret.append("</title>"); ret.append(System.lineSeparator());
		ret.append("\t\t<meta http-equiv=\"content-type\" "
				+ "content=\"text/html; charset=UTF-8\">");
		ret.append(System.lineSeparator());

		ret.append("\t\t<style>"); ret.append(System.lineSeparator());
		
		if (Config.getInstance().javascriptFileCount() > 0)
			for (int i = 0; i < Config.getInstance().stylesheetFileCount(); i++)
				ret.append(HelperHtml.readFile(
						Config.getInstance().getStylesheetFile(i)));
		
		ret.append(System.lineSeparator());
		ret.append("\t\t</style>"); ret.append(System.lineSeparator());
		
		ret.append("\t\t<script type=\"text/javascript\">"); ret.append(System.lineSeparator());
		
		if (Config.getInstance().javascriptFileCount() > 0)
			for (int i = 0; i < Config.getInstance().javascriptFileCount(); i++)
				ret.append(HelperHtml.readFile(
						Config.getInstance().getJavascriptFile(i)));
		
		ret.append("\t\t</script>"); ret.append(System.lineSeparator());
		
		ret.append("\t</head>"); ret.append(System.lineSeparator());
		ret.append("\t<body>"); ret.append(System.lineSeparator());
		ret.append("\t\t<h1>");
		ret.append(head);
		ret.append("</h1>"); ret.append(System.lineSeparator());
		ret.append("\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Generates the footer of HTML output file
	 * 
	 * @return Generates the footer of HTML output file
	 */
	public static String footer() {
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t</body>");
		ret.append(System.lineSeparator());
		ret.append("</html>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Replaces the HTML entities for &lt; and &gt; by their names.
	 * 
	 * @param source String in the HTML entities should be replaced.
	 * 
	 * @return String in the HTML entities were replaced.
	 */
	public static String replaceHtmlEntities(String source) {
		return source
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll(System.lineSeparator(), "<br/>")
				.replaceAll("ä", "&auml;")
				.replaceAll("Ä", "&Auml;")
				.replaceAll("ö", "&ouml;")
				.replaceAll("Ö", "&Ouml;")
				.replaceAll("ü", "&uuml;")
				.replaceAll("Ü", "&Uuml;");
	}

	/**
	 * Replaces tab with spaces
	 * 
	 * @param source String in the tab should be replaced.
	 * 
	 * @param space Number of spaces.
	 * 
	 * @return String in the tab should were replaced.
	 */
	public static String replaceTabWidthSpaces(String source, int space) {
		String replace = new String(); 
		for (int i = 0; i < space; i++)
			replace += "&nbsp;";
		return source.replaceAll("\\t",replace);
	}
	
	/**
	 * Create the list of methods.
	 * 
	 * @param description The description for the list.
	 * 
	 * @param methods List of methods
	 * 
	 * @param calls If the list of tested methods (true) or untested methods 
	 * (false) are created?
	 * 
	 * @param file 
	 * 
	 * @return The list of methods.
	 */
	public static String createListOfMethods(String description, 
			List<CSMethod> methods, boolean calls, boolean file) {
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		List<String> list = new ArrayList<String>();
		
		for (int method = 0; method < methods.size(); method++) {
			if (methods.get(method).isDeprecated())
				continue;
			
			String fileName = new String();
			
			if (file) {
				fileName = ResourceBundle.getBundle(TestRunner.BUNDLE_FILE)
						.getString("result_checksoure") + "_Test" + 
						methods.get(method).getClassName() + ".html";
			}
			
			String linkSrc = "<a href=\"" + fileName + "#" + 
					methods.get(method).getClassName() + "." + 
					methods.get(method).getName() + "\">";
			String linkEnd = "</a>";
			
			String listItem = new String();
			
			if (calls && (methods.get(method).callsCount() > 0)) {
				listItem = createListEntry(methods.get(method), linkSrc, 
						linkEnd, true);
			} else if (!calls && (methods.get(method).callsCount() == 0)) {
				listItem = createListEntry(methods.get(method), linkSrc,
						linkEnd, false);
			}
			
			list.add(listItem);
		}
		
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++)
			ret.append(list.get(i));
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Create a HTML list entry of a specified method.
	 * 
	 * @param method The method, which is to be output in the list.
	 * 
	 * @param linkSrc Begin HTML link tag to the check source HTML result file.
	 * 
	 * @param linkEnd End HTML link tag to the check source HTML result file.
	 * 
	 * @param calls If the list of tested methods (true) or untested methods 
	 * (false) are created?
	 * 
	 * @return The created HTML list entry of the specified method.
	 */
	private static String createListEntry(CSMethod method, String linkSrc, 
			String linkEnd, boolean calls) {
		
		String colorBegin = new String();
		String colorEnd = new String();
		if (!calls) {
			if (method.getModifier().equals("public")) {
				colorBegin = "<span style=\"background:" + 
						HelperHtmlCodeJava.getInstance()
						.formatColor(HelperUsedColor.ERROR) + ";\">";
				colorEnd = "</span>";
			} else if (method.getModifier().equals("private") ||
					 method.getModifier().equals("protected")) {
				colorBegin = "<span style=\"background:" + 
						HelperHtmlCodeJava.getInstance()
						.formatColor(HelperUsedColor.WARNING) + ";\">";
				colorEnd = "</span>";
			}
		}

		StringBuilder ret = new StringBuilder();
		ret.append("\t\t\t\t\t<li>");
		ret.append(colorBegin);
		ret.append(linkSrc);
		ret.append(method.getModifier());
		ret.append(" ");
		ret.append(method.getClassName());
		ret.append(".");
		ret.append(method.getName());
		ret.append(linkEnd);
		ret.append(colorEnd);
		ret.append("</li>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
}
