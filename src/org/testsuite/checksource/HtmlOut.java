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

package org.testsuite.checksource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.testsuite.helper.HelperCalendar;
import org.testsuite.helper.HelperHtml;
import org.testsuite.helper.HelperHtmlCodeJava;

/**
 * Creates the HTML output
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlOut extends Html {

	/**
	 * Initialize the class
	 * 
	 * @param result The name of result file
	 */
	public HtmlOut(String result) {
		super(result);
	}

	/**
	 * Create the HTML output
	 */
	public void createHtml(List<SourceLine> source, List<CSMethod> methods) {
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(_resultFile));

			String date = HelperCalendar.datetimeToString(new Date().getTime());
			
			String sourceClass = new String();
			if (methods.size() > 0)
				sourceClass = methods.get(0).getClassName();
			
			bw.write(HelperHtml.head(_bundle.getString("htmlHead_head")
					.replace("?", sourceClass) + " (" + date  +")", 
					_bundle.getString("htmlHead_description").replace("?", 
							sourceClass)));
			
			bw.write("\t\t<div class=\"checksource\">");
			bw.write(System.lineSeparator());
			
			// List of method calls
			if (methods.size() > 0)
				bw.write(HelperHtml.createListOfMethods(
						_bundle.getString("methods_calls"), methods, true, 
						false));
			
			// List of method without calls
			if (methods.size() > 0)
				bw.write(HelperHtml.createListOfMethods(
						_bundle.getString("methods_without_calls"), methods, 
						false, false));
			
			// Source code
			if (source.size() > 0)
				bw.write(sourceCode(source, methods));
			
			bw.write("\t\t</div>");
			bw.write(System.lineSeparator());
			
			bw.write(HelperHtml.footer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
	 * @return The list of methods.
	 * 
	 * @deprecated Use {@link org.testsuite.helper.HelperHtml#createListOfMethods(String, List, boolean)}
	 */
	@SuppressWarnings("unused")
	private String listMethod(String description, List<CSMethod> methods, 
			boolean calls) {
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		for (int method = 0; method < methods.size(); method++) {
			if (calls && (methods.get(method).callsCount() > 0)) {
				ret.append("\t\t\t\t\t<li>");
				ret.append(methods.get(method).getClassName());
				ret.append(".");
				ret.append(methods.get(method).getName());
				ret.append("</li>");
				ret.append(System.lineSeparator());
			} else if (!calls && (methods.get(method).callsCount() == 0)) {
				ret.append("\t\t\t\t\t<li>");
				ret.append(methods.get(method).getClassName());
				ret.append(".");
				ret.append(methods.get(method).getName());
				ret.append("</li>");
				ret.append(System.lineSeparator());
			}
		}
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Creates the HTML output for the source code.
	 * 
	 * @param lines The list of source lines.
	 * 
	 * @return The HTML output for the source code.
	 */
	private String sourceCode(List<SourceLine> lines, List<CSMethod> methods) {
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceSource\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th style=\"width:100px;\"></th>");
		ret.append(System.lineSeparator());
		ret.append("\t\t\t\t\t\t<th style=\"width:70%;\"></th>");
		ret.append(System.lineSeparator());
		ret.append("\t\t\t\t\t\t<th style=\"width:*;\"></th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		
		for (int i = 0; i < lines.size(); i++) {
			ret.append("\t\t\t\t\t<tr>");
			ret.append(System.lineSeparator());
			
			String background = new String();
			if (lines.get(i).isLineTested()) {
				background = " style=\"background: " + 
						HelperHtmlCodeJava.getInstance()
						.formatColor(COLOR_PASS) + ";\" ";
			} else if (lines.get(i).messageCount() > 0) {
				background = " style=\"background: " +
						HelperHtmlCodeJava.getInstance()
						.formatColor(lines.get(i).getMessage(0).getColor()) + 
						";\" ";
			}

			String ankerBegin = new String();
			String ankerEnd = new String();
			if (lines.get(i).isBeginMethod()) {
				String ankerName = new String();
				for (int j = 0; j < methods.size(); j++) {
					if (lines.get(i).getLineNumber() == methods.get(j).getLine()) {
						ankerName = methods.get(j).getClassName() + "." +
								methods.get(j).getName();
						break;
					}
				}
				ankerBegin = "<a name=\"" + ankerName + "\">";
				ankerEnd = "</a>";
			}
			
			ret.append("\t\t\t\t\t\t<td");
			ret.append(background);
			ret.append(">");
			ret.append(ankerBegin);
			ret.append(lines.get(i).getLineNumber());
			ret.append(ankerEnd);
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			String line = HelperHtml.replaceHtmlEntities(lines.get(i).getLine());
			line = HelperHtmlCodeJava.formatString(line,
					lines.get(i).isMultiLineComment(),
					lines.get(i).isJavadoc());
			line = HelperHtml.replaceTabWidthSpaces(line, 4);

			ret.append("\t\t\t\t\t\t<td");
			ret.append(background);
			ret.append(">");
			ret.append(line);
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td");
			ret.append(background);
			ret.append(">");
			
			for (int j = 0; j < lines.get(i).messageCount(); j++) {
				if (j > 0)
					ret.append(", ");
				ret.append(lines.get(i).getMessage(j).getMessage());
			}
			
			ret.append("</td>");
			ret.append(System.lineSeparator());

			ret.append("\t\t\t\t\t</tr>");
			ret.append(System.lineSeparator());
		}
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
}
