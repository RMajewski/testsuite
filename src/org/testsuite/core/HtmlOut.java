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

package org.testsuite.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import org.testsuite.helper.HelperCalendar;

/**
 * Creates from the test results an HTML file.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlOut {
	/**
	 * Saves the output file
	 */
	private String _htmlFile;
	
	/**
	 * Saves the Writer for file
	 */
	private FileWriter _writer;
	
	/**
	 * Saves the BufferedWriter
	 */
	private BufferedWriter _bw;
	
	/**
	 * Saves the object of the resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Initialize the class
	 * 
	 * @param fileName File for output
	 * 
	 * @throws IOException 
	 */
	public HtmlOut(String fileName) throws IOException {
		if ((fileName == null) || fileName.isEmpty())
			throw new IllegalArgumentException();
		
		_bundle = ResourceBundle.getBundle(
				"resources.lang.org.testsuite.core.HtmlOut");
		
		// Daten speichern
		_htmlFile = fileName;
		
		// Writer zum ausgeben öffnen.
		_writer = new FileWriter(new File(_htmlFile));
		_bw = new BufferedWriter(_writer);
	}
	
	/**
	 * Created the stylesheets
	 * 
	 * @throws IOException
	 */
	private void stylesheets() throws IOException {
		_bw.write("\t\t<style>"); _bw.newLine();
		_bw.write(".pass {background-color: #0F0;}"); _bw.newLine();
		_bw.write(".wrong {background-color: #F00;}"); _bw.newLine();
		_bw.write(".ignore {background-color: #AAAAAA;}"); _bw.newLine();
		_bw.write(".exception {background-color: #FF0;}"); _bw.newLine();
		_bw.write(".testoutInvisible {display: none;}"); _bw.newLine();
		_bw.write(".testoutVisible {display: block;}"); _bw.newLine();
		_bw.write(".right {float: right;}"); _bw.newLine();
		_bw.write(".testsuite {margin-bottom: 25px;}"); _bw.newLine();
		_bw.write("table {width: 100%; border-collapse: collapse; ");
		_bw.write("border: 2px solid #000; table-layout:fixed;}"); 
		_bw.newLine();
		_bw.write("td {text-align: left; vertical-align: text-top;}");
		_bw.newLine();
		_bw.write("th {text-align: left;}"); _bw.newLine();
		_bw.write("tr {border: 1px solid #000;}"); _bw.newLine();
		_bw.write("\t\t</style>"); _bw.newLine();
	}
	
	/**
	 * Created the Java Script
	 * 
	 * @throws IOException
	 */
	private void javaScript() throws IOException {
		_bw.write("\t\t<script type=\"text/javascript\">"); _bw.newLine();
		_bw.write("function toogleDisplayId(suite,test) {"); _bw.newLine();
		
		_bw.write("if (window.document.getElementById(\"id_\" + suite + \"_\" + test).className == ");
		_bw.write("\"testoutInvisible\")"); _bw.newLine();
		_bw.write("window.document.getElementById(\"id_\" + suite + \"_\" + test).className = \"testoutVisible\";");
		_bw.newLine();
		_bw.write("else");
		_bw.newLine();
		_bw.write("window.document.getElementById(\"id_\" + suite + \"_\" + test).className = \"testoutInvisible\";");
		_bw.write("}"); _bw.newLine();
		_bw.write("\t\t</script>"); _bw.newLine();
	}
	
	/**
	 * Returns the HTML head
	 * 
	 * @throws IOException 
	 */
	public void htmlHead() throws IOException {
		String date = HelperCalendar.dateToString(new Date().getTime());
		
		_bw.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 "
				+ "Transitional//EN\" \"http://www.w3.org/TR/html4/"
				+ "transitional.dtd\">");
		_bw.newLine();
		_bw.write("<html>"); _bw.newLine();
		_bw.write("\t<head>"); _bw.newLine();
		_bw.write("\t\t<title>Ergebnisse der Tests vom ");
		_bw.write(date);
		_bw.write("</title>"); _bw.newLine();
		_bw.write("\t\t<meta http-equiv=\"content-type\" "
				+ "content=\"text/html; charset=UTF-8\">");
		_bw.newLine();
		stylesheets();
		javaScript();
		_bw.write("\t</head>"); _bw.newLine();
		_bw.write("\t<body>"); _bw.newLine();
		_bw.write("\t\t<h1>");
		_bw.write(_bundle.getString("htmlHead_head"));
		_bw.write(" ");
		_bw.write(date);
		_bw.write("</h1>"); _bw.newLine();
		_bw.write("\t\t<p>");
		_bw.write(_bundle.getString("htmlHead_description"));
		_bw.write("</p>");
		_bw.newLine();
	}
	
	/**
	 * Returns the HTML feet out
	 * 
	 * @throws IOException 
	 */
	public void htmlEnd() throws IOException {
		_bw.write("\t</body>");
		_bw.newLine();
		_bw.write("</html>");
		_bw.newLine();
		_bw.close();
	}
	
	/**
	 * Write a string to BufferedWriter.
	 * 
	 * @param html String to be written into the BufferedWriter.
	 * 
	 * @throws IOException
	 */
	public void writeHtml(String html) throws IOException {
		if ((html == null) || html.isEmpty())
			throw new IllegalArgumentException();
		_bw.write(html);
	}
	
	public String generateTestOut(int suiteId, int testId, String console,
			String error) throws IOException {
		if ((suiteId < 0) || (testId <0))
			return new String();
		
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<div ");
		ret.append("class=\"right\"><a href=\"javascript:toogleDisplayId(");
		ret.append(suiteId);
		ret.append(", ");
		ret.append(testId);
		ret.append(")\">Ausgabe</a></div>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<div class=\"testoutInvisible\" id=\"id_");
		ret.append(suiteId);
		ret.append("_");
		ret.append(testId);
		ret.append("\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t\t<div class=\"console\">");
		
		if ((console == null) || console.isEmpty())
			ret.append(_bundle.getString("generateTestOut_noConsoleOut"));
		else
			ret.append(console);
		
		ret.append("</div>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t\t<div class=\"error\">");
		
		if ((error == null) || error.isEmpty())
			ret.append(_bundle.getString("generateTestOut_noErrorOut"));
		else
			ret.append(error);
		
		ret.append("</div>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
}
