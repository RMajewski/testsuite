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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.HtmlOut;

/**
 * Test for the class {@link org.testsuite.core.HtmlOut}.
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
	 */
	@Test
	public void testHtmlHead() throws IOException {
		_html.htmlHead();
		
		verify(_bufferedWriter, times(9 + 9 + 7)).newLine();
		verify(_bufferedWriter, times(14 + 9 + 9)).write(Matchers.anyString());
	}

	/**
	 * Tests if the HTML end is correctly written in the BufferedWriter.
	 */
	@Test
	public void testHtmlEnd() throws IOException {
		_html.htmlEnd();
		
		verify(_bufferedWriter, times(2)).newLine();
		verify(_bufferedWriter, times(2)).write(Matchers.anyString());
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
	public void testGenerateTestOutWithMinusOneAsId() throws Exception {
		InputStream console = mock(InputStream.class);
		InputStream error = mock(InputStream.class);

		InputStreamReader isrConsole = mock(InputStreamReader.class);
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(console)
			.thenReturn(isrConsole);
		
		InputStreamReader isrError = mock(InputStreamReader.class);
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(console)
			.thenReturn(isrError);

		assertEquals(new String(), _html.generateTestOut(-1, console, error));
		
		PowerMockito.verifyNew(InputStreamReader.class, never())
			.withArguments(console);
		PowerMockito.verifyNew(InputStreamReader.class, never())
		.withArguments(error);
	}
	
	@Test
	public void testGenerateTestOutWithNullAsConsoleStream()
			throws Exception {
		InputStream console = mock(InputStream.class);
		InputStream error = mock(InputStream.class);
		
		InputStreamReader isrConsole = mock(InputStreamReader.class);
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(console)
			.thenReturn(isrConsole);
		
		BufferedReader brConsole = mock(BufferedReader.class);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(brConsole);
		
		InputStreamReader isrError = mock(InputStreamReader.class);
		when(isrError.ready()).thenReturn(true);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(error)
			.thenReturn(isrError);
		
		String errorMessage = "Fehler";
		BufferedReader brError = mock(BufferedReader.class);
		when(brError.readLine()).thenReturn(errorMessage, null);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(brError);
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:togleDisplayId(1)\">Ausgabe</a>");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append("Keine Ausgabe auf der Konsole");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append(errorMessage);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, null, error));
		
		PowerMockito.verifyNew(InputStreamReader.class, never())
			.withArguments(console);
		PowerMockito.verifyNew(BufferedReader.class, never())
			.withArguments(isrConsole);
		
		PowerMockito.verifyNew(InputStreamReader.class)
			.withArguments(error);
		PowerMockito.verifyNew(BufferedReader.class)
		.withArguments(isrError);
		verify(isrError).ready();
	}
	
	@Test
	public void testGenerateTestOutWithNullAsErrorStream()
			throws Exception {
		InputStream console = mock(InputStream.class);
		
		InputStreamReader isrConsole = mock(InputStreamReader.class);
		when(isrConsole.ready()).thenReturn(true);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(console)
			.thenReturn(isrConsole);
		
		String consoleMessage = "Ausgabe der Console";
		BufferedReader brConsole = mock(BufferedReader.class);
		when(brConsole.readLine()).thenReturn(consoleMessage, null);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(brConsole);
		
		InputStream error = mock(InputStream.class);

		InputStreamReader isrError = mock(InputStreamReader.class);
		when(isrError.ready()).thenReturn(true);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(error)
			.thenReturn(isrError);
		
		String errorMessage = "Fehler";
		BufferedReader brError = mock(BufferedReader.class);
		when(brError.readLine()).thenReturn(errorMessage, null);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(brError);
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:togleDisplayId(1)\">Ausgabe</a>");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append(consoleMessage);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append("Keine Fehler ausgegeben");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, console, null));
		
		PowerMockito.verifyNew(InputStreamReader.class)
			.withArguments(console);
		PowerMockito.verifyNew(BufferedReader.class)
			.withArguments(isrConsole);
		verify(isrConsole).ready();
		
		PowerMockito.verifyNew(InputStreamReader.class, never())
			.withArguments(error);
		PowerMockito.verifyNew(BufferedReader.class, never())
		.withArguments(isrError);
		verify(isrError, never()).ready();
	}
	
	@Test
	public void testGenerateTestOut() throws Exception {
		InputStream console = mock(InputStream.class);
		
		InputStreamReader isrConsole = mock(InputStreamReader.class);
		when(isrConsole.ready()).thenReturn(true);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(console)
			.thenReturn(isrConsole);
		
		String consoleMessage = "Ausgabe der Console";
		BufferedReader brConsole = mock(BufferedReader.class);
		when(brConsole.readLine()).thenReturn(consoleMessage, null);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(brConsole);
		
		InputStream error = mock(InputStream.class);

		InputStreamReader isrError = mock(InputStreamReader.class);
		when(isrError.ready()).thenReturn(true);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(error)
			.thenReturn(isrError);
		
		String errorMessage = "Fehler";
		BufferedReader brError = mock(BufferedReader.class);
		when(brError.readLine()).thenReturn(errorMessage, null);
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(brError);
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t\t\t\t\t\t<div class=\"right\">");
		builder.append("<a href=\"javascript:togleDisplayId(1)\">Ausgabe</a>");
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t<div class=\"testoutInvisible\"");
		builder.append(" id=\"id_1\">");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"console\">");
		builder.append(consoleMessage);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t\t<div class=\"error\">");
		builder.append(errorMessage);
		builder.append("</div>");
		builder.append(System.lineSeparator());
		builder.append("\t\t\t\t\t\t</div>");
		builder.append(System.lineSeparator());

		assertEquals(builder.toString(), 
				_html.generateTestOut(1, console, error));
		
		PowerMockito.verifyNew(InputStreamReader.class)
			.withArguments(console);
		PowerMockito.verifyNew(BufferedReader.class)
			.withArguments(isrConsole);
		verify(isrConsole).ready();
		
		PowerMockito.verifyNew(InputStreamReader.class)
			.withArguments(error);
		PowerMockito.verifyNew(BufferedReader.class)
		.withArguments(isrError);
		verify(isrError).ready();
	}
}
