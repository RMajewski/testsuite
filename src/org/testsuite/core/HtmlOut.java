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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

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
	 * Initialize the class
	 * 
	 * @param fileName File for output
	 * 
	 * @throws IOException 
	 */
	public HtmlOut(String fileName) throws IOException {
		if ((fileName == null) || fileName.isEmpty())
			throw new IllegalArgumentException();
		
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
		_bw.write("\t\t</style>"); _bw.newLine();
	}
	
	/**
	 * Created the Java Script
	 * 
	 * @throws IOException
	 */
	private void javaScript() throws IOException {
		_bw.write("\t\t<script type=\"text/javascript\">"); _bw.newLine();
		_bw.write("function toogleDisplayId(id) {"); _bw.newLine();
		
		_bw.write("if (window.document.getElementById(\"id_\" + id ).className == ");
		_bw.write("\"testoutInvisible\")"); _bw.newLine();
		_bw.write("window.document.getElementById(\"id_\" + id).className = \"testoutVisible\";");
		_bw.newLine();
		_bw.write("else");
		_bw.newLine();
		_bw.write("window.document.getElementById(\"id_\" + id).className = \"testoutInvisible\";");
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
		_bw.write("\t\t<h1>Ergebniss der Tests vom ");
		_bw.write(date);
		_bw.write("</h1>"); _bw.newLine();
		_bw.write("\t\t<p>Dies ist eine automatisch erzeugte HTML-Datei mit ");
		_bw.write("dem Ergebnissen der ausgeführten Tests.</p>");
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
	
	public String generateTestOut(int suiteId, int testId, InputStream console,
			InputStream error) throws IOException {
		if ((suiteId < 0) || (testId <0))
			return new String();
		
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<div ");
		ret.append("class=\"right\"><a href=\"javascript:togleDisplayId(");
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
		
		String tmp = streamOut(console);
		if ((tmp == null) || tmp.isEmpty())
			ret.append("Keine Ausgabe auf der Konsole");
		else
			ret.append(tmp);
		
		ret.append("</div>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t\t<div class=\"error\">");
		
		tmp = streamOut(error);
		if ((tmp == null) || tmp.isEmpty())
			ret.append("Keine Fehler ausgegeben");
		else
			ret.append(tmp);
		
		ret.append("</div>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	/**
	 * Reads the given stream and returns it as a string. The characters "new
	 * line" will be replaced by a "<br/>".
	 * 
	 * @param is Stream, which is to be read.
	 * 
	 * @return Scraped Stream.
	 */
	private String streamOut(InputStream is) throws IOException {
		// Rückgabe vorbereiten
		StringBuilder buffer = new StringBuilder();
		
		// Stream auslesen
		if (is != null) {
			InputStreamReader isr = new InputStreamReader(is);
			if (isr.ready()) {
				BufferedReader br = new BufferedReader(isr);
				String line;
				boolean first = true;
				while ((line = br.readLine()) != null) {
					if (first)
						first = false;
					else
						buffer.append("<br/>");
					buffer.append(line);
				}
			}
		}
		
		// Rückgabe
		return buffer.toString();
	}
}
