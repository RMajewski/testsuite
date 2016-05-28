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
	 * Saves the name of resource bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.core.HtmlOut";
	
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
		
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		
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

	/**
	 * Creates of the transferred data, the HTML output.
	 * 
	 * @param suiteId Id from the TestSuite
	 * 
	 * @param testId Id from the Test
	 * 
	 * @param console Console output
	 * 
	 * @param error Error output
	 * 
	 * @param exec Command for the command line
	 * 
	 * @return Created HTML output
	 */
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

		ret.append("\t\t\t\t\t\t\t<div class=\"command_line\">");

		if ((exec == null) || exec.isEmpty())
			ret.append(_bundle.getString("generateTestOut_noExecOut"));
		else {
			ret.append(System.lineSeparator());
			ret.append("\t\t\t\t\t\t\t\t<code>");
			ret.append(exec);
			ret.append("</code>");
			ret.append(System.lineSeparator());
			ret.append("\t\t\t\t\t\t\t");
		}

		ret.append("</div>");
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
