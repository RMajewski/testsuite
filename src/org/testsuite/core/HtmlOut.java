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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
	 * Load the text file and returned as string.
	 * 
	 * @param name Name of the file
	 * 
	 * @return Loaded file as string
	 */
	private String readFile(String name) {
		StringBuilder ret = new StringBuilder();
		
		try {
			URL url = getClass().getClassLoader().getResource(name);
			System.out.println(url);
			BufferedReader bf = new BufferedReader(new FileReader(
					url.getFile()));
			if (bf.ready()) {
				String line;
				while ((line = bf.readLine()) != null) {
					ret.append(line);
				}
				bf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}
	
	// OPT Delete stylesheets method
	/**
	 * Created the stylesheets
	 * 
	 * @throws IOException
	 * 
	 * @deprecated since version 0.3. Save the stylesheet into a text file and
	 * use the {@link #readFile(String)} method.
	 */
	private void stylesheets() throws IOException {
		_bw.write("\t\t<style>"); _bw.newLine();
		_bw.write(".pass {background-color: #CFFFCF;}"); _bw.newLine();
		_bw.write(".wrong {background-color: #FFCFCF;}"); _bw.newLine();
		_bw.write(".ignore {background-color: #CFCFCF;}"); _bw.newLine();
		_bw.write(".exception {background-color: #FFFFCF;}"); _bw.newLine();
		_bw.write(".testoutInvisible {display: none;}"); _bw.newLine();
		_bw.write(".testoutVisible {display: block; border: 1px solid #000}");
		_bw.newLine();
		_bw.write(".right {float: right;}"); _bw.newLine();
		_bw.write(".testsuite {margin: 25px 0;}"); _bw.newLine();
		_bw.write(".testgroup {border: 1px solid #000; margin-bottom: 25px; ");
		_bw.write("padding: 10px; }"); _bw.newLine();
		_bw.write(".testdescription {border: 1px dashed #808080; padding: ");
		_bw.write("10px; } "); _bw.newLine();
		_bw.write(".libraries {border: 1px dashed #808080; padding: 10px; ");
		_bw.write("margin-top: 5px;}"); _bw.newLine();
		_bw.write(".nonexists {border: 1px dashed #808080; padding: 10px; ");
		_bw.write("margin-top: 5px;}"); _bw.newLine();
		_bw.write(".command_line {margin: 10px; color: #FFF; background: #000; ");
		_bw.write("padding: 5px;}"); _bw.newLine();
		_bw.write(".console {border: 1px dashed #808080; margin: 0 10px; ");
		_bw.write("padding: 5px;}"); _bw.newLine();
		_bw.write(".error {border: 1px dashed #F00; margin: 10px; padding: ");
		_bw.write("5px;}"); _bw.newLine();
		_bw.write("padding: 5px;}"); _bw.newLine();
		_bw.write("table {width: 100%; border-collapse: collapse; ");
		_bw.write("border: 2px solid #000; table-layout:fixed;}"); 
		_bw.newLine();
		_bw.write("td {text-align: left; vertical-align: text-top;}");
		_bw.newLine();
		_bw.write("th {text-align: left;}"); _bw.newLine();
		_bw.write("tr {border: 1px solid #000;}"); _bw.newLine();
		_bw.write("a:link {text-decoration: none; font-weight: bold;");
		_bw.write("color: #505050;}"); _bw.newLine();
		_bw.write("a:visited {text-decoration: none; color: #505050;}");
		_bw.newLine();
		_bw.write("a:hover {text-decoration: underline; font-weight: bold;");
		_bw.write("color: #505050;}"); _bw.newLine();
		_bw.write("a:active {text-decoration: underline; font-weight: bold;");
		_bw.write("color: #505050;}"); _bw.newLine();
		_bw.write("a:focus {text-decoration: underline; font-weight: bold;");
		_bw.write("color: #505050;}"); _bw.newLine();
		_bw.write("\t\t</style>"); _bw.newLine();
	}
	
	// OPT Delete the javaScript method
	/**
	 * Created the Java Script
	 * 
	 * @throws IOException
	 * 
	 * @deprecated since version 0.3 Save the javascript into a text file and
	 * use the {@link #readFile(String)} method.
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
		String date = HelperCalendar.datetimeToString(new Date().getTime());
		
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
		
		_bw.write("\t\t<style>"); _bw.newLine();
		_bw.write(readFile("resources" + File.separator + "html" +
				File.separator + "out.css")); _bw.newLine();
		_bw.write("\t\t</style>"); _bw.newLine();
		
		_bw.write("\t\t<script type=\"text/javascript\">"); _bw.newLine();
		_bw.write(readFile("resources" + File.separator + "html" +
				File.separator + "out.js")); _bw.newLine();
		_bw.write("\t\t</script>"); _bw.newLine();
		
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
			String error, String exec) throws IOException {
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

		ret.append("\t\t\t\t\t\t\t<div class=\"command line\">");

		if ((exec == null) || exec.isEmpty())
			ret.append(_bundle.getString("generateTestOut_noExecOut"));
		else {
			ret.append("\t\t\t\t\t\t\t\t<code>");
			ret.append(exec);
			ret.append("</code>");
		}

		ret.append("\t\t\t\t\t\t\t</div>");
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
