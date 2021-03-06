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

package tests.testsuite.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.html.HtmlOutOverview;
import org.testsuite.core.HtmlOut;
import org.testsuite.data.Config;

/**
 * Test for the class {@link org.testsuite.core.HtmlOut}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlOut.class})
public class TestHtmlOut {
	/**
	 * Saves the instance of class HtmlOut.
	 */
	private HtmlOut _html;
	
	/**
	 * The file name for the HTML output.
	 */
	private String _resultFile;
	
	/**
	 * Saves the mock object for FileWriter
	 */
	private FileWriter _fileWriter;
	
	/**
	 * Saves the mock object for BufferedWriter
	 */
	private BufferedWriter _bufferedWriter;

	/**
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		_fileWriter = mock(FileWriter.class);
		PowerMockito.whenNew(FileWriter.class)
			.withAnyArguments()
			.thenReturn(_fileWriter);
		
		_bufferedWriter = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(_fileWriter)
			.thenReturn(_bufferedWriter);
		
		_resultFile = "test.html";
		_html = new HtmlOut(_resultFile);
		
		Config.getInstance().clearAll();
	}

	/**
	 * Tests if the initialization right.
	 * @throws Exception 
	 */
	@Test
	public void testHtmlOut() throws Exception {
		PowerMockito.whenNew(FileWriter.class)
			.withParameterTypes(File.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(_fileWriter);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as a
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHtmlOutWithNullAsParameter() throws IOException
	{
		_html = new HtmlOut(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHtmlOutWithEmptyStringAsParameter() throws IOException
	{
		_html = new HtmlOut(new String());
	}

	/**
	 * Tests if the HTML header is correctly written in the BufferedWriter.
	 * @deprecated
	 */
	@Ignore("The method is deprecated")
	@Test
	public void testHtmlHeadOld() throws IOException {
		Config config = mock(Config.class);
		when(config.javascriptFileCount()).thenReturn(0);
		when(config.stylesheetFileCount()).thenReturn(0);
		
		_html.htmlHead(config);
		
		verify(_bufferedWriter, atLeastOnce()).newLine();
		verify(_bufferedWriter, atLeastOnce()).write(Matchers.anyString());
	}

	/**
	 * Tests if the HTML header is correctly written in the BufferedWriter.
	 */
	@Test
	public void testHtmlHead() throws IOException {
		Config.getInstance().setPathResult("result");
		Config.getInstance().setPathSuitesResult("0000000");
		
		HtmlOutOverview.getInstance().createHtml();
		_html.htmlHead();
		
		verify(_bufferedWriter, atLeastOnce()).newLine();
		verify(_bufferedWriter, atLeastOnce()).write(Matchers.anyString());
	}

	/**
	 * Tests if the HTML end is correctly written in the BufferedWriter.
	 */
	@Test
	public void testHtmlEnd() throws IOException {
		_html.htmlEnd();
		
		verify(_bufferedWriter).write(Matchers.anyString());
		verify(_bufferedWriter).close();
	}
	
	/**
	 * Tests if the HTML string is correctly written in the BufferedWriter.
	 */
	@Test
	public void testwriteHtml() throws IOException {
		String html = "<p>Dies ist ein Test</p>";
		_html.writeHtml(html);
		
		verify(_bufferedWriter).write(html);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as a
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testwriteHtmlWithNullAsParameter() throws IOException {
		_html.writeHtml(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testwriteHtmlWithEmptyStringAsParameter() throws IOException {
		_html.writeHtml(new String());
	}
	
	@Test
	public void testGenerateTestOutWithMinusOneAsSuiteId() throws Exception {
		String console = "console";
		String error = "error";
		String exec = "exec";

		assertEquals(new String(), _html.generateTestOut(-1, 0, console, error,
				exec, null));
	}
	
	@Test
	public void testGenerateTestOutWithMinusOneAsTestId() throws Exception {
		String console = "console";
		String error = "error";
		String exec = "exec";

		assertEquals(new String(), _html.generateTestOut(0, -1, console, error,
				exec, null));
	}
	
	@Test
	public void testGenerateTestOutWithNullAsConsole()
			throws Exception {
		String error = "Fehler";
		String exec = "exec";
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:toogleDisplayId(1, 1)\">");
		builder.append("Ausgabe</a></div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"command_line\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t\t<code>");
		builder.append(exec);
		builder.append("</code>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append("Keine Ausgabe auf der Konsole");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append(error);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, 1, null, error, exec, null));
	}
	
	@Test
	public void testGenerateTestOutWithNullAsExec()
			throws Exception {
		String console = "console";
		String error = "Fehler";
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:toogleDisplayId(1, 1)\">");
		builder.append("Ausgabe</a></div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"command_line\">Kein Befehl");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append(console);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append(error);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, 1, console, error, null, null));
	}
	
	@Test
	public void testGenerateTestOutWithNullAsError()
			throws Exception {
		String console = "console";
		String exec = "exec";
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:toogleDisplayId(1, 1)\">");
		builder.append("Ausgabe</a></div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"command_line\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t\t<code>");
		builder.append(exec);
		builder.append("</code>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append(console);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append("Keine Fehler ausgegeben");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, 1, console, null, exec, null));
	}
	
	@Test
	public void testGenerateTestOut() throws Exception {
		String console = "console";
		String error = "error";
		String exec = "exec";
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:toogleDisplayId(1, 1)\">");
		builder.append("Ausgabe</a></div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"command_line\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t\t<code>");
		builder.append(exec);
		builder.append("</code>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append(console);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append(error);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, 1, console, error, exec, null));
	}
}
