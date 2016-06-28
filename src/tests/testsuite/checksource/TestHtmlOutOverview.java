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

package tests.testsuite.checksource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.annotation.CheckSource;
import org.testsuite.checksource.html.HtmlOutOverview;
import org.testsuite.core.TestRunner;
import org.testsuite.helper.HelperHtmlCodeJava;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests for the class {@link org.testsuite.checksource.html.HtmlOutOverview}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlOutOverview.class})
public class TestHtmlOutOverview {
	/**
	 * Saves the instance of HtmlOutOverview
	 */
	private HtmlOutOverview _html;

	/**
	 * Initialize the tests. 
	 */
	@Before
	public void setUp() throws Exception {
		_html = HtmlOutOverview.getInstance();
		getResultFiles().clear();
		getNoneExists().clear();
		getMethods().clear();
		getSourceLines().clear();
		
		Field field = HtmlOutOverview.class
				.getSuperclass()
				.getDeclaredField("_resultFile");
		field.setAccessible(true);
		field.set(_html, null);
	}
	
	/**
	 * Retrieves the list of result files.
	 * 
	 * @return The list of result files.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getResultFiles() throws Exception {
		Field field = HtmlOutOverview.class.getDeclaredField("_resultFiles");
		field.setAccessible(true);
		return (List<String>)field.get(_html);
	}
	
	/**
	 * Retrieves the list of none exists files.
	 * 
	 * @return The list of none exists files.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getNoneExists() throws Exception {
		Field field = HtmlOutOverview.class.getDeclaredField("_noneExists");
		field.setAccessible(true);
		return (List<String>)field.get(_html);
	}
	
	/**
	 * Retrieves the list of methods files.
	 * 
	 * @return The list of methods files.
	 */
	@SuppressWarnings("unchecked")
	private List<CSMethod> getMethods() throws Exception {
		Field field = HtmlOutOverview.class.getDeclaredField("_methods");
		field.setAccessible(true);
		return (List<CSMethod>)field.get(_html);
	}
	
	/**
	 * Retrieves the list of source lines files.
	 * 
	 * @return The list of source lines files.
	 */
	@SuppressWarnings("unchecked")
	private List<SourceLine> getSourceLines() throws Exception {
		Field field = HtmlOutOverview.class.getDeclaredField("_sources");
		field.setAccessible(true);
		return (List<SourceLine>)field.get(_html);
	}

	/**
	 * Tests whether the instance is returned.
	 */
	@CheckSource(methodList={"getInstance", "HtmlOutOverview"})
	@Test
	public void testGetInstance() throws Exception {
		assertNotNull(HtmlOutOverview.getInstance());
		
		assertEquals(new ArrayList<String>(), getResultFiles());
		assertEquals(new ArrayList<CSMethod>(), getMethods());
		assertEquals(new ArrayList<SourceLine>(), getSourceLines());
		assertEquals(new ArrayList<String>(), getNoneExists());
	}

	/**
	 * Tests if a result file name can be added to the list of result file
	 * names.
	 */
	@Test
	public void testAddResultFile() throws Exception {
		assertEquals(0, getResultFiles().size());
		
		String name = "Test.java";
		_html.addResultFile(name);
		
		assertEquals(1, getResultFiles().size());
		assertEquals(name, getResultFiles().get(0));
	}

	/**
	 * Tests if a list of methods can be added to the list of methods.
	 */
	@Test
	public void testAddMethods() throws Exception {
		assertEquals(0, getMethods().size());

		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		methods.add(method2);
		
		CSMethod method3 = mock(CSMethod.class);
		methods.add(method3);
		
		_html.addMethods(methods);
		
		assertEquals(3, getMethods().size());
		assertEquals(method1, getMethods().get(0));
		assertEquals(method2, getMethods().get(1));
		assertEquals(method3, getMethods().get(2));
	}

	/**
	 * Tests if a list of source lines can be added to the list of source lines.
	 */
	@Test
	public void testAddSourceLines() throws Exception {
		assertEquals(0, getSourceLines().size());
		
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		list.add(line2);
		
		_html.addSourceLines(list);
		
		assertEquals(2, getSourceLines().size());
		assertEquals(line1, getSourceLines().get(0));
		assertEquals(line2, getSourceLines().get(1));
	}

	/**
	 * Tests if a none exists file name can be added to the list of none exists
	 * file names.
	 */
	@Test
	public void testAddNoneExistsFileName() throws Exception {
		assertEquals(0, getNoneExists().size());
		
		String name = "Test.java";
		_html.addNoneExistsFileName(name);
		
		assertEquals(1, getNoneExists().size());
		assertEquals(name, getNoneExists().get(0));
	}

	/**
	 * Tests if the HTML file is created.
	 */
	@Test
	public void testCreateHtml() throws Exception {
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withAnyArguments()
			.thenReturn(file);
		
		FileWriter fw = mock(FileWriter.class);
		PowerMockito.whenNew(FileWriter.class)
			.withAnyArguments()
			.thenReturn(fw);
		
		BufferedWriter bw = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(fw)
			.thenReturn(bw);
		
		_html.createHtml();
		
		verify(file).mkdirs();
		
		verify(bw).close();
		verify(bw, times(5)).write(Matchers.anyString());
	}

	/**
	 * Tests if it is determined from the list of result file name is the name
	 * with the stated test.
	 */
	@Test
	public void testGetResultFileFromTestName() {
		String name1 = "CeckSource_Test1.html";
		_html.addResultFile(name1);
		
		String name2 = "CeckSource_Test2.html";
		_html.addResultFile(name2);

		String name3 = "CeckSource_Test3.html";
		_html.addResultFile(name3);

		String name4 = "CeckSource_Test4.html";
		_html.addResultFile(name4);

		String name5 = "CeckSource_Test5.html";
		_html.addResultFile(name5);
		
		assertEquals(name1, _html.getResultFileFromTestName("Test1"));
		assertEquals(name2, _html.getResultFileFromTestName("Test2"));
		assertEquals(name3, _html.getResultFileFromTestName("Test3"));
		assertEquals(name4, _html.getResultFileFromTestName("Test4"));
		assertEquals(name5, _html.getResultFileFromTestName("Test5"));
	}

	/**
	 * Tests whether the result file is returned.
	 */
	@Test
	public void testGetResultFile() {
		assertNull(_html.getResultFile());
	}

	/**
	 * Tests if the HTML list of non-existing files will be created correctly.
	 */
	@CheckSource(method="createNoneExistsList")
	@Test
	public void testCreateNoneExistsList() throws Exception {
		String name1 = "Test1.java";
		String name2 = "Test2.java";
		String name3 = "Test3.java";
		
		_html.addNoneExistsFileName(name1);
		_html.addNoneExistsFileName(name2);
		_html.addNoneExistsFileName(name3);
		
		StringBuilder ret = new StringBuilder(
				"\t\t\t<div class=\"checksourceList\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<p>");
		ret.append(ResourceBundle.getBundle(HtmlOutOverview.BUNDLE_FILE)
				.getString("overview_none_exists_files"));
		ret.append("</p>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li>");
		ret.append(name1);
		ret.append("</li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li>");
		ret.append(name2);
		ret.append("</li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<li>");
		ret.append(name3);
		ret.append("</li>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createNoneExistsList");
		method.setAccessible(true);
		
		assertEquals(ret.toString(), (String)method.invoke(_html));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@CheckSource(method="createNoneExistsList")
	@Test
	public void testCreateNoneExistsListWithNoFileNames() throws Exception {
		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createNoneExistsList");
		method.setAccessible(true);
		
		assertEquals(new String(), (String)method.invoke(_html));
	}

	/**
	 * Tests if the HTML table is created correctly with the links.
	 */
	@CheckSource(method="createHtmlLink")
	@Test
	public void testCreateHtmlLink() throws Exception{
		String fileName1 = "CheckSource_TestClass1.html";
		String fileName2 = "CheckSource_TestClass2.html";
		
		_html.addResultFile(fileName1);
		_html.addResultFile(fileName2);
		
		File file1 = mock(File.class);
		when(file1.exists()).thenReturn(true);
		when(file1.getName()).thenReturn(fileName1);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName1)
			.thenReturn(file1);
		
		File file2 = mock(File.class);
		when(file2.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName2)
			.thenReturn(file2);
		
		StringBuilder ret = new StringBuilder(
				"\t\t\t<div class=\"checksourceLinks\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(ResourceBundle.getBundle(HtmlOutOverview.BUNDLE_FILE)
				.getString("overview_links"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(ResourceBundle.getBundle(HtmlOutOverview.BUNDLE_FILE)
				.getString("overview_class"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(ResourceBundle.getBundle(HtmlOutOverview.BUNDLE_FILE)
				.getString("overview_messages"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());
				
		ret.append("\t\t\t\t\t\t<td>");
		ret.append("<a href=\"");
		ret.append(fileName1);
		ret.append("\">TestClass1.html</a></td>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<td>Class1</td>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<td></td>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createHtmlLink");
		method.setAccessible(true);
		
		assertEquals(ret.toString(), (String)method.invoke(_html));
	}

	/**
	 * Tests if the messages are created correctly.
	 */
	@CheckSource(method="createMessages")
	@Test
	public void testCreateMessages() throws Exception {
		String className1 = "Class1";
		String className2 = "Class2";
		String messageText1 = "Error";
		String messageText2 = "Warning";
		String methodName = "method";
		int lineNumber = 2;
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getClassName()).thenReturn(className1);
		
		MessageColor message1 = mock(MessageColor.class);
		when(message1.getMessage()).thenReturn(messageText1);
		when(message1.getColor()).thenReturn(HelperUsedColor.ERROR);
		
		MessageColor message2 = mock(MessageColor.class);
		when(message2.getMessage()).thenReturn(messageText2);
		when(message2.getColor()).thenReturn(HelperUsedColor.WARNING);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getClassName()).thenReturn(className2);
		when(line2.messageCount()).thenReturn(2);
		when(line2.getMessage(0)).thenReturn(message1);
		when(line2.getMessage(1)).thenReturn(message2);
		when(line2.getLineNumber()).thenReturn(lineNumber);
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLine()).thenReturn(lineNumber);
		when(method.getClassName()).thenReturn(className2);
		when(method.getName()).thenReturn(methodName);
		
		List<SourceLine> sources = new ArrayList<SourceLine>();
		sources.add(line1);
		sources.add(line2);
		_html.addSourceLines(sources);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		methods.add(method);
		_html.addMethods(methods);
		
		StringBuilder result = new StringBuilder();
		result.append("<span style=\"background: ");
		result.append(HelperHtmlCodeJava.getInstance().formatColor(
				HelperUsedColor.ERROR));
		result.append(";\" >");
		result.append("<a href=\"");
		result.append(ResourceBundle.getBundle(
				TestRunner.BUNDLE_FILE).getString("result_checksoure"));
		result.append("_Test");
		result.append(className2);
		result.append(".html#");
		result.append(methodName);
		result.append("\">");
		result.append(messageText1);
		result.append("</a></span>");
		
		result.append("<br/><span style=\"background: ");
		result.append(HelperHtmlCodeJava.getInstance().formatColor(
				HelperUsedColor.WARNING));
		result.append(";\" >");
		result.append("<a href=\"");		
		result.append(ResourceBundle.getBundle(
				TestRunner.BUNDLE_FILE).getString("result_checksoure"));
		result.append("_Test");
		result.append(className2);
		result.append(".html#");
		result.append(methodName);
		result.append("\">");
		result.append(messageText2);
		result.append("</a></span>");
		
		Method createMessages = HtmlOutOverview.class.getDeclaredMethod(
				"createMessages", String.class);
		createMessages.setAccessible(true);
		
		assertEquals(result.toString(), 
				(String)createMessages.invoke(_html, className2));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@CheckSource(method="createMessages")
	@Test
	public void testCreateMessagesWithoutSourceLines() throws Exception {
		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createMessages", String.class);
		method.setAccessible(true);
		
		assertEquals(new String(), (String)method.invoke(_html, new String()));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@CheckSource(method="createMessages")
	@Test
	public void testCreateMessagesWithoutMessages() throws Exception {
		String className = "Test";
		
		SourceLine line = mock(SourceLine.class);
		when(line.messageCount()).thenReturn(0);
		when(line.getClassName()).thenReturn(className);
		
		List<SourceLine> list = new ArrayList<SourceLine>();
		list.add(line);
		_html.addSourceLines(list);

		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createMessages", String.class);
		method.setAccessible(true);
		
		assertEquals(new String(), (String)method.invoke(_html, className));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@CheckSource(method="createMessages")
	@Test
	public void testCreateMessagesWithEmptyStringAsParameter()
			throws Exception {
		SourceLine line = mock(SourceLine.class);
		
		List<SourceLine> list = new ArrayList<SourceLine>();
		list.add(line);
		_html.addSourceLines(list);
		
		Method method = HtmlOutOverview.class.getDeclaredMethod(
				"createMessages", String.class);
		method.setAccessible(true);
		
		assertEquals(new String(), (String)method.invoke(_html, new String()));
	}

}
