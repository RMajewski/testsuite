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

package tests.testsuite.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.helper.HelperHtml;

/**
 * Tests the helper class {@link org.testsuite.helper.HelperHtml}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HelperHtml.class})
public class TestHelperHtml {

	/**
	 * Tests if HTML list of methods is created correctly.
	 */
	@Test
	public void testCreateListOfMethodsCallsWithoutFileNameToLinks() {
		String description = "Dies ist ein Test";
		
		String methodName1 = "Method1";
		String methodName2 = "Method2";
		String modifier1 = "private";
		String modifier2 = "public";
		String className1 = "Test1";
		String className2 = "Test2";
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.callsCount()).thenReturn(1);
		when(method1.getCall(0)).thenReturn(1);
		when(method1.getName()).thenReturn(methodName1);
		when(method1.getModifier()).thenReturn(modifier1);
		when(method1.getClassName()).thenReturn(className1);
		list.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getName()).thenReturn(methodName2);
		when(method2.getModifier()).thenReturn(modifier2);
		when(method2.getClassName()).thenReturn(className2);
		list.add(method2);
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li><a href=\"#");
		ret.append(className1);
		ret.append(".");
		ret.append(methodName1);
		ret.append("\">");
		ret.append(modifier1);
		ret.append(" ");
		ret.append(className1);
		ret.append(".");
		ret.append(methodName1);
		ret.append("</a></li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		assertEquals(ret.toString(), HelperHtml.createListOfMethods(description, 
				list, true, false));
		
		verify(method1).callsCount();
		verify(method1, times(2)).getClassName();
		verify(method1).getModifier();
		verify(method1, times(2)).getName();
		
		verify(method2).callsCount();
		verify(method2).getClassName();
		verify(method2, never()).getModifier();
		verify(method2).getName();
	}

	/**
	 * Tests if HTML list of methods is created correctly.
	 */
	@Test
	public void testCreateListOfMethodsCallsWithFileNameToLinks() {
		String description = "Dies ist ein Test";
		
		String methodName1 = "Method1";
		String methodName2 = "Method2";
		String modifier1 = "private";
		String modifier2 = "public";
		String className1 = "Test1";
		String className2 = "Test2";
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.callsCount()).thenReturn(1);
		when(method1.getCall(0)).thenReturn(1);
		when(method1.getName()).thenReturn(methodName1);
		when(method1.getModifier()).thenReturn(modifier1);
		when(method1.getClassName()).thenReturn(className1);
		list.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getName()).thenReturn(methodName2);
		when(method2.getModifier()).thenReturn(modifier2);
		when(method2.getClassName()).thenReturn(className2);
		list.add(method2);
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li><a href=\"");
		ret.append(ResourceBundle.getBundle(TestRunner.BUNDLE_FILE)
						.getString("result_checksoure"));
		ret.append("_Test");
		ret.append(className1);
		ret.append(".html#");
		ret.append(className1);
		ret.append(".");
		ret.append(methodName1);
		ret.append("\">");
		ret.append(modifier1);
		ret.append(" ");
		ret.append(className1);
		ret.append(".");
		ret.append(methodName1);
		ret.append("</a></li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		assertEquals(ret.toString(), HelperHtml.createListOfMethods(description, 
				list, true, true));
		
		verify(method1).callsCount();
		verify(method1, times(3)).getClassName();
		verify(method1).getModifier();
		verify(method1, times(2)).getName();
		
		verify(method2).callsCount();
		verify(method2, times(2)).getClassName();
		verify(method2, never()).getModifier();
		verify(method2).getName();
	}

	/**
	 * Tests if HTML list of methods is created correctly.
	 */
	@Test
	public void testCreateListOfMethodsNotCallsWithoutFileNameToLinks() {
		String description = "Dies ist ein Test";
		
		String methodName1 = "Method1";
		String methodName2 = "Method2";
		String modifier1 = "private";
		String modifier2 = "public";
		String className1 = "Test1";
		String className2 = "Test2";
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.callsCount()).thenReturn(1);
		when(method1.getCall(0)).thenReturn(1);
		when(method1.getName()).thenReturn(methodName1);
		when(method1.getModifier()).thenReturn(modifier1);
		when(method1.getClassName()).thenReturn(className1);
		list.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getName()).thenReturn(methodName2);
		when(method2.getModifier()).thenReturn(modifier2);
		when(method2.getClassName()).thenReturn(className2);
		list.add(method2);
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li><span style=\"background:#ffcfcf;\">");
		ret.append("<a href=\"#");
		ret.append(className2);
		ret.append(".");
		ret.append(methodName2);
		ret.append("\">");
		ret.append(modifier2);
		ret.append(" ");
		ret.append(className2);
		ret.append(".");
		ret.append(methodName2);
		ret.append("</a></span></li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		assertEquals(ret.toString(), HelperHtml.createListOfMethods(description, 
				list, false, false));
		
		verify(method1).callsCount();
		verify(method1).getClassName();
		verify(method1, never()).getModifier();
		verify(method1).getName();
		
		verify(method2).callsCount();
		verify(method2, times(2)).getClassName();
		verify(method2, times(2)).getModifier();
		verify(method2, times(2)).getName();
	}

	/**
	 * Tests if HTML list of methods is created correctly.
	 */
	@Test
	public void testCreateListOfMethodsNotCallsWithFileNameToLinks() {
		String description = "Dies ist ein Test";
		
		String methodName1 = "Method1";
		String methodName2 = "Method2";
		String modifier1 = "private";
		String modifier2 = "public";
		String className1 = "Test1";
		String className2 = "Test2";
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.callsCount()).thenReturn(1);
		when(method1.getCall(0)).thenReturn(1);
		when(method1.getName()).thenReturn(methodName1);
		when(method1.getModifier()).thenReturn(modifier1);
		when(method1.getClassName()).thenReturn(className1);
		list.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getName()).thenReturn(methodName2);
		when(method2.getModifier()).thenReturn(modifier2);
		when(method2.getClassName()).thenReturn(className2);
		list.add(method2);
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li><span style=\"background:#ffcfcf;\">");
		ret.append("<a href=\"");
		ret.append(ResourceBundle.getBundle(TestRunner.BUNDLE_FILE)
						.getString("result_checksoure"));
		ret.append("_Test");
		ret.append(className2);
		ret.append(".html#");
		ret.append(className2);
		ret.append(".");
		ret.append(methodName2);
		ret.append("\">");
		ret.append(modifier2);
		ret.append(" ");
		ret.append(className2);
		ret.append(".");
		ret.append(methodName2);
		ret.append("</a></span></li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		assertEquals(ret.toString(), HelperHtml.createListOfMethods(description, 
				list, false, true));
		
		verify(method1).callsCount();
		verify(method1, times(2)).getClassName();
		verify(method1, never()).getModifier();
		verify(method1).getName();
		
		verify(method2).callsCount();
		verify(method2, times(3)).getClassName();
		verify(method2, times(2)).getModifier();
		verify(method2, times(2)).getName();
	}

	/**
	 * Tests if the HTML footer is returned correctly.
	 */
	@Test
	public void testFooter() {
		StringBuilder ret = new StringBuilder("\t</body>");
		ret.append(System.lineSeparator());
		ret.append("</html>");
		ret.append(System.lineSeparator());
		assertEquals(ret.toString(), HelperHtml.footer());
	}

	/**
	 * Tests if the HTML header is returned correctly.
	 */
	@Test
	public void testHeadWithoutJavaScriptAnWithoutCss() {
		String head = "Test";
		String description = "Dies ist ein Test";
		StringBuilder ret = new StringBuilder();
		
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 ");
		ret.append("Transitional//EN\" \"http://www.w3.org/TR/html4/");
		ret.append("transitional.dtd\">");
		ret.append(System.lineSeparator());
		
		ret.append("<html>");
		ret.append(System.lineSeparator());
		ret.append("\t<head>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<title>");
		ret.append(head);
		ret.append("</title>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<meta http-equiv=\"content-type\" "
				+ "content=\"text/html; charset=UTF-8\">");
		ret.append(System.lineSeparator());

		ret.append("\t\t<style>");
		ret.append(System.lineSeparator());
		
		ret.append(System.lineSeparator());
		ret.append("\t\t</style>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t<script type=\"text/javascript\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t</script>");
		ret.append(System.lineSeparator());
		
		ret.append("\t</head>");
		ret.append(System.lineSeparator());
		ret.append("\t<body>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<h1>");
		ret.append(head);
		ret.append("</h1>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		assertEquals(ret.toString(), HelperHtml.head(head, description));
	}

	/**
	 * Tests if the HTML header is returned correctly.
	 */
	@Test
	public void testHeadWithJavaScript() throws Exception {
		String head = "Test";
		String description = "Dies ist ein Test";
		String line1 = "Zeile 1";
		String line2 = "Zeile 2";
		
		StringBuilder ret = new StringBuilder();
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 ");
		ret.append("Transitional//EN\" \"http://www.w3.org/TR/html4/");
		ret.append("transitional.dtd\">");
		ret.append(System.lineSeparator());
		
		ret.append("<html>");
		ret.append(System.lineSeparator());
		ret.append("\t<head>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<title>");
		ret.append(head);
		ret.append("</title>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<meta http-equiv=\"content-type\" "
				+ "content=\"text/html; charset=UTF-8\">");
		ret.append(System.lineSeparator());

		ret.append("\t\t<style>");
		ret.append(System.lineSeparator());
		
		ret.append(System.lineSeparator());
		ret.append("\t\t</style>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t<script type=\"text/javascript\">");
		ret.append(System.lineSeparator());
		ret.append(line1);
		ret.append(System.lineSeparator());
		ret.append(line2);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t</script>");
		ret.append(System.lineSeparator());
		
		ret.append("\t</head>");
		ret.append(System.lineSeparator());
		ret.append("\t<body>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<h1>");
		ret.append(head);
		ret.append("</h1>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		FileReader fr = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withAnyArguments()
			.thenReturn(fr);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn(line1, line2, null);
		when(br.ready()).thenReturn(true);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr)
			.thenReturn(br);
		
		Config.getInstance().addJavascriptFile("resources/html/out.js");
		
		assertEquals(ret.toString(), HelperHtml.head(head, description));
	}

	/**
	 *
	 */
	@Test
	public void testHeadWithCss() throws Exception {
		String head = "Test";
		String description = "Dies ist ein Test";
		String line1 = "Zeile 1";
		String line2 = "Zeile 2";
		
		StringBuilder ret = new StringBuilder();
		ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 ");
		ret.append("Transitional//EN\" \"http://www.w3.org/TR/html4/");
		ret.append("transitional.dtd\">");
		ret.append(System.lineSeparator());
		
		ret.append("<html>");
		ret.append(System.lineSeparator());
		ret.append("\t<head>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<title>");
		ret.append(head);
		ret.append("</title>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<meta http-equiv=\"content-type\" "
				+ "content=\"text/html; charset=UTF-8\">");
		ret.append(System.lineSeparator());

		ret.append("\t\t<style>");
		ret.append(System.lineSeparator());

		ret.append(line1);
		ret.append(System.lineSeparator());
		ret.append(line2);
		ret.append(System.lineSeparator());
		
		ret.append(System.lineSeparator());
		ret.append("\t\t</style>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t<script type=\"text/javascript\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t</script>");
		ret.append(System.lineSeparator());
		
		ret.append("\t</head>");
		ret.append(System.lineSeparator());
		ret.append("\t<body>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<h1>");
		ret.append(head);
		ret.append("</h1>");
		ret.append(System.lineSeparator());
		ret.append("\t\t<p>");
		ret.append(description);
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		FileReader fr = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withAnyArguments()
			.thenReturn(fr);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn(line1, line2, null);
		when(br.ready()).thenReturn(true);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr)
			.thenReturn(br);
		
		Config.getInstance().addStylesheetFile("resources/html/out.css");
		
		assertEquals(ret.toString(), HelperHtml.head(head, description));
	}

	/**
	 * Tests if the file is properly read and returned as a string.
	 */
	@Test
	public void testReadFile() throws Exception {
		String line1 = "Zeile 1";
		String line2 = "Zeile 2";
		
		FileReader fr = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withAnyArguments()
			.thenReturn(fr);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn(line1, line2, null);
		when(br.ready()).thenReturn(true);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr)
			.thenReturn(br);
		
		String ret = line1 + System.lineSeparator() + line2 + 
				System.lineSeparator();
		assertEquals(ret, HelperHtml.readFile("resources/html/out.css"));
	}

	/**
	 * Tests if HTML Entities are replaced.
	 */
	@Test
	public void testReplaceHtmlEntities() {
		String test = "<xml>Test</xml>" + System.lineSeparator() + 
				"mit HTML entities. (äÄöÖüÜ)";
		String result = "&lt;xml&gt;Test&lt;/xml&gt;<br/>mit HTML entities." +
				" (&auml;&Auml;&ouml;&Ouml;&uuml;&Uuml;)";
		assertEquals(result, HelperHtml.replaceHtmlEntities(test));
	}

	/**
	 * Tests if the tabs are replaced by forced blanks.
	 */
	@Test
	public void testReplaceTabWidthSpaces() {
		String test = "\tDies\tist\tein\t\tTest.";
		String result = "&nbsp;Dies&nbsp;ist&nbsp;ein&nbsp;&nbsp;Test.";
		
		assertEquals(result, HelperHtml.replaceTabWidthSpaces(test, 1));
	}
}