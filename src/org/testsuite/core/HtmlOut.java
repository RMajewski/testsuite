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
	 * Saves the HTML code for the tests
	 * 
	 * @deprecated
	 */
	private StringBuilder _tests;
	
	/**
	 * Saves the current ID of the test.
	 * 
	 * @deprecated
	 */
	private int _id;
	
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
	 * Generates the HTML code for a test
	 * 
	 * @param name Name of the test suite
	 * 
	 * @param right Number of correct tests
	 * 
	 * @param wrong Number of false tests
	 * 
	 * @param ignore Number of ignored tests
	 * 
	 * @param exception Number of Faulty tests
	 * 
	 * @param console Console output
	 * 
	 * @param error Output of the error
	 * 
	 * @param time Time that has taken the test
	 * 
	 * @param fit Specifies whether the links are to be created to fit result
	 * files or not.
	 * 
	 * @param path Specifies the directory where are the Fit-output files.
	 * 
	 * @throws IOExcetption
	 * 
	 * @deprecated
	 */
	public void test(String name, int right, int wrong, int ignore,
			int exception, long time, InputStream console, InputStream error,
			boolean fit, String path) 
					throws IOException {
		String rightColspan = new String();
		if (wrong == -1)
			rightColspan = " colspan=\"2\"";
		
		String exceptionColspan = new String();
		if (ignore == -1)
			exceptionColspan = " colspan=\"2\"";
		
		String nl = System.getProperty("line.separator");
		
		_tests.append("\t\t\t<tr>");
		_tests.append(nl);
		
		_tests.append("\t\t\t\t<td>");
		
		if (fit) {
			_tests.append("<a href=\"");
			_tests.append(path);
			_tests.append(File.separator);
			_tests.append(name);
			_tests.append(".html\">");
		}
		
		_tests.append(String.valueOf(name));
		
		if (fit)
			_tests.append("</a>");
		
		_tests.append("<div class=\"right\"><a href=\"");
		_tests.append("javascript:toogleDisplayId(");
		_tests.append(_id);
		_tests.append(")\">Ausgabe</a></div>");
		_tests.append("<div class=\"testoutInvisible\" id=\"id_");
		_tests.append(_id++);
		_tests.append("\">");
		
		String tmp = streamOut(console);
		if ((tmp != null) && !tmp.isEmpty()) {
			_tests.append("<div class=\"console\">");
			_tests.append(tmp);
			_tests.append("</div>");
		}

		tmp = streamOut(error);
		if ((tmp != null) && !tmp.isEmpty()) {
			_tests.append("<div class=\"error\"");
			_tests.append(tmp);
			_tests.append("</div>");
		}
		_tests.append("</div>");
		_tests.append("</td>");
		_tests.append(nl);
		
		_tests.append("\t\t\t\t<td");
		_tests.append(testClass(right, "pass"));
		_tests.append(rightColspan);
		_tests.append(">");
		_tests.append(String.valueOf(right));
		_tests.append("</td>");
		_tests.append(nl);
		
		if (wrong > -1) {
			_tests.append("\t\t\t\t<td");
			_tests.append(testClass(wrong, "wrong"));
			_tests.append(">");
			_tests.append(String.valueOf(wrong));
			_tests.append("</td>");
			_tests.append(nl);
		}
		
		if (ignore > -1) {
			_tests.append("\t\t\t\t<td");
			_tests.append(testClass(ignore, "ignore"));
			_tests.append(">");
			_tests.append(String.valueOf(ignore));
			_tests.append("</td>");
			_tests.append(nl);
		}
		
		_tests.append("\t\t\t\t<td");
		_tests.append(testClass(exception, "exception"));
		_tests.append(exceptionColspan);
		_tests.append(">");
		_tests.append(String.valueOf(exception));
		_tests.append("</td>");
		_tests.append(nl);
		
		_tests.append("\t\t\t\t<td>");
		_tests.append(time);
		_tests.append("</td>");
		_tests.append(nl);
		
		_tests.append("\t\t\t</tr>");
		_tests.append(nl);
	}
	
	/**
	 * Checks if the specified test is greater 0th. If this is the case, then
	 * the specified class is outputted.
	 * 
	 * @param test Test to be checked.
	 * 
	 * @param className Name of the class to be output.
	 * 
	 * @return Class if test > 0. Otherwise, an empty string.
	 * 
	 * @deprecated
	 */
	private String testClass(int test, String className) {
		String ret = new String();
		
		if (test > 0)
			ret = " class=\"" + className + "\"";
		
		return ret;
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

	/**
	 * Generates the HTML code for a test
	 * 
	 * @param name Name of the test suite
	 * 
	 * @param right Number of correct tests
	 * 
	 * @param exception Number of Faulty tests
	 * 
	 * @param time Time that has taken the test
	 * 
	 * @param console Console output
	 * 
	 * @param error Output of the error
	 * 
	 * @throws IOExcetption
	 * 
	 * @deprecated
	 */
	public void test(String name, int right, int exception, long time,
			InputStream console, InputStream error) throws IOException {
		test(name, right, -1, -1, exception, time, console, error, false, null);
	}

	/**
	 * Returns the beginning of a table. In the table are the results of the
	 * test suites.
	 * 
	 * @param name Name of the test suite
	 * 
	 * @param pack Name of the package
	 * 
	 * @param right Number of correct tests
	 * 
	 * @param wrong Number of false tests
	 * 
	 * @param ignore Number of ignored tests
	 * 
	 * @param exception Of Faulty tests
	 * 
	 * @param time Time, the suite has a total hand.
	 * 
	 * @deprecated
	 */
	public void suiteHtml(String name, String pack, int right, int wrong, 
			int ignore, int exception, long time) throws IOException {
		
		String rightColspan = new String();
		if (wrong == -1)
			rightColspan = " colspan=\"2\"";
		
		String exceptionColspan = new String();
		if (ignore == -1)
			exceptionColspan = " colspan=\"2\"";
		
		_bw.write("\t\t<table width=\"100%\" border=\"1\">"); _bw.newLine();
		_bw.write("\t\t\t<tr>"); _bw.newLine();
		
		_bw.write("\t\t\t\t<td>");
		_bw.write(name);
		_bw.write("</td>");
		_bw.newLine();
		
		_bw.write("\t\t\t\t<td");
		_bw.write(testClass(right, "pass"));
		_bw.write(rightColspan);
		_bw.write(">");
		_bw.write(String.valueOf(right));
		_bw.write("</td>");
		_bw.newLine();
		
		if (wrong > -1) {		
			_bw.write("\t\t\t\t<td");
			_bw.write(testClass(wrong, "wrong"));
			_bw.write(">");
			_bw.write(String.valueOf(wrong));
			_bw.write("</td>");
			_bw.newLine();
		}
		
		if (ignore > -1) {
			_bw.write("\t\t\t\t<td");
			_bw.write(testClass(ignore, "ignore"));
			_bw.write(">");
			_bw.write(String.valueOf(ignore));
			_bw.write("</td>");
			_bw.newLine();
		}
		
		_bw.write("\t\t\t\t<td");
		_bw.write(testClass(exception, "exception"));
		_bw.write(exceptionColspan);
		_bw.write(">");
		_bw.write(String.valueOf(exception));
		_bw.write("</td>"); _bw.newLine();
		
		// Zeit
		_bw.write("\t\t\t\t<td>");
		_bw.write(String.valueOf(time));
		_bw.write("</td>"); _bw.newLine();

		_bw.write("\t\t\t</tr>"); _bw.newLine();

		_bw.write("\t\t\t<tr>"); _bw.newLine();
		
		// Ausgabe des Package-Namen
		_bw.write("\t\t\t\t<td colspan=\"6\">Package: ");
		_bw.write(pack);
		_bw.write("</td>");
		_bw.newLine();

		_bw.write("\t\t\t</tr>"); _bw.newLine();
		
		// Tests ausgeben
		_bw.write(_tests.toString());
		_bw.newLine();
		
		// Tests neu initalisieren
		_tests = new StringBuilder();
		
		// Ende der Tabelle
		_bw.write("\t\t</table>");
		_bw.newLine();
	}
	
	/**
	 * Returns the beginning of a table. In the table are the results of the
	 * test of the Suites.
	 * 
	 * @param name Name of the test suite
	 * 
	 * @param pack Name of package
	 * 
	 * @param right Number of correct tests
	 * 
	 * @param exception Number of Faulty tests
	 * 
	 * @param time Time that the test suite has a total hand
	 * 
	 * @throws IOException
	 * 
	 * @deprecated
	 */
	public void suiteHtml(String name, String pack, int right, int exception,
			long time)
		throws IOException {
		suiteHtml(name, pack, right, -1, -1, exception, time);
	}
}
