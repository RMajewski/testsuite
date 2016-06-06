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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.JunitTestRunner}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JunitTestRunner.class})
public class TestJunitTestRunner {

	/**
	 * Holds the instance of the JunitTestRunner
	 */
	private JunitTestRunner _runner;
	
	/**
	 * Save the mock of configuration
	 * 
	 * @deprecated
	 */
	private Config _config;
	
	/**
	 * Save the instance of resource bundle
	 */
	private ResourceBundle _bundle;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_bundle = ResourceBundle.getBundle(JunitTestRunner.BUNDLE_FILE);
		_runner = new JunitTestRunner();
		Config.getInstance().clearAll();
	}

	/**
	 * Tests if JunitTestRunner has been derived from the TestRunner class.
	 */
	@Test
	public void testDerivedFromTestRunner() {
		assertEquals(org.testsuite.core.TestRunner.class.getName(),
				JunitTestRunner.class.getSuperclass().getName());
	}
	
	/**
	 * Tests if the column headings are properly generated for the HTML output.
	 */
	@Test
	public void testCreateHtmlTableHead() throws Exception {
		String suiteName = "TestSuite";
		String packageName = "package";
		
		String ret = "\t\t\t\t\t\t<th style=\"width: 50%;\">" + suiteName + 
				"</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Erfolgreich</th>" +
				System.lineSeparator() + "\t\t\t\t\t\t<th>Fehlerhaft</th>" +
				System.lineSeparator() + "\t\t\t\t\t\t<th>Zeit</th>" +
				System.lineSeparator() + "\t\t\t\t\t</tr>" + 
				System.lineSeparator() + "\t\t\t\t\t<tr>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<th colspan=\"4\">" +
				packageName + "</th>" + System.lineSeparator();
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlTableHead",
						int.class);
		method.setAccessible(true);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0));
		
		verify(suite).getName();
		verify(suite).getPackage();
	}
	
	/**
	 * Tests if the line of HTML is generated correctly for a test.
	 * 
	 * @deprecated
	 */
	@Ignore("The method is deprecated")
	@Test
	public void testCreateHtmlColumnOld() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" +
				System.lineSeparator() + " class=\"" +
				"command_line\">" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t\t<code>exec</code>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t</div>" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t<div class=\"console\">Console</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t\t<div class=\"error\">" +
				"Fehler</div>" + System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		String packageName = "package";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"wrong\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + ok + "</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + fail + "</td>" + 
				System.lineSeparator() +
				"\t\t\t\t\t\t<td class=\"wrong\">" + duration + "</td>" + 
				System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(15)).getTest(0);
	}
	
	/**
	 * Tests if the line of HTML is generated correctly for a test.
	 */
	@Test
	public void testCreateHtmlColumn() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" +
				System.lineSeparator() + " class=\"" +
				"command_line\">" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t\t<code>exec</code>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t</div>" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t<div class=\"console\">Console</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t\t<div class=\"error\">" +
				"Fehler</div>" + System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		String packageName = "package";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"wrong\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + ok + "</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + fail + "</td>" + 
				System.lineSeparator() +
				"\t\t\t\t\t\t<td class=\"wrong\">" + duration + "</td>" + 
				System.lineSeparator();
		
		Config.getInstance().setPathSuitesResult(resultSuite);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(16)).getTest(0);
	}

	/**
	 * Tests if the line of HTML is generated correctly for a none executed test.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	public void testCreateHtmlColumnWithNoneExecutedTestOld() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\">Keine Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Keine Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">" +
				"wurde nicht ausgeführt</td>" + System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(false);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		
		verify(suite, times(11)).getTest(0);
	}

	/**
	 * Tests if the line of HTML is generated correctly for a none executed test.
	 */
	@Test
	public void testCreateHtmlColumnWithNoneExecutedTest() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\">Keine Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Keine Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">" +
				"wurde nicht ausgeführt</td>" + System.lineSeparator();
		
		Config.getInstance().setPathSuitesResult(resultSuite);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(false);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		
		verify(suite, times(11)).getTest(0);
	}
	
	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test file does not exist.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test
	public void testCreateHtmlColumnWithNoneExistingTestOld() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + srcName +
				File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">Test " +
				"existiert nicht</td>" + System.lineSeparator();

		_runner.setFileExtension(extension);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		
		HtmlOut html = mock(HtmlOut.class);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(_config).getPathSrc();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		
		verify(suite, times(5)).getTest(0);
	}
	
	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test file does not exist.
	 */
	@Test
	public void testCreateHtmlColumnWithNoneExistingTest() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + srcName +
				File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">Test " +
				"existiert nicht</td>" + System.lineSeparator();

		_runner.setFileExtension(extension);
		
		Config.getInstance().setPathSrc(srcName);
		
		HtmlOut html = mock(HtmlOut.class);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		
		verify(suite, times(5)).getTest(0);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test suite id is passed.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNoSuiteId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, -1, 0, html);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test ID is passed.
	 * 
	 *  @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNoTestId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, -1, html);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * instance is passed by HtmlOut.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNullAsHtmlOut() throws Exception {
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
	
	/**
	 * Tests if the line of HTML is generated correctly for a test.
	 */
	@Test
	public void testCreateHtmlRow() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" +
				System.lineSeparator() + " class=\"" +
				"command_line\">" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t\t<code>exec</code>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t</div>" + System.lineSeparator() + 
				"\t\t\t\t\t\t\t<div class=\"console\">Console</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t\t<div class=\"error\">" +
				"Fehler</div>" + System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		String packageName = "package";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"wrong\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + ok + "</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + fail + "</td>" + 
				System.lineSeparator() +
				"\t\t\t\t\t\t<td class=\"wrong\">" + duration + "</td>" + 
				System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.isJvm()).thenReturn(true);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).isJvm();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(17)).getTest(0);
	}
	/**
	 * Tests if the line of HTML is generated correctly for a none executed test.
	 */
	@Test
	public void testCreateHtmlRowWithNoneExecutedTest() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\">Keine Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Keine Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + testName +
				System.lineSeparator() +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">" +
				"wurde nicht ausgeführt</td>" + System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(false);
		when(test.getId()).thenReturn(testId);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		
		verify(suite, times(10)).getTest(0);
	}
	
	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test file does not exist.
	 */
	@Test
	public void testCreateHtmlRowWithNoneExistingTest() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int ok = 1;
		int fail = 2;
		String duration = "00:00:01.897";
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + srcName +
				File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">Test " +
				"existiert nicht</td>" + System.lineSeparator();

		_runner.setFileExtension(extension);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		
		HtmlOut html = mock(HtmlOut.class);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Junit test = mock(Junit.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).isExecuted();
		order.verify(test).isExists();
		order.verify(_config).getPathSrc();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		
		verify(suite, times(5)).getTest(0);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test suite id is passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlRowWithNoSuiteId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, -1, 0, html);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test ID is passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlRowWithNoTestId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, -1, html);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * instance is passed by HtmlOut.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlRowWithNullAsHtmlOut() throws Exception {
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
	
	/**
	 * Tests if the correct command is created.
	 * 
	 * @deprecated
	 */
	@Ignore("The method is deprecated")
	@Test
	public void testExecOld() throws Exception{
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		
		String name = "test";
		String libName1 = "lib1.jar";
		String libName2 = "lib2.jar";
		String propName = "testing=\"true\"";
		String pathLib = "lib";
		String classPath1 = "bin";
		String classPath2 = "classpath";
		String ret = "java -cp " + classPath2 + File.pathSeparator + classPath1 +
				File.pathSeparator + pathLib + File.separator + libName1 + 
				File.pathSeparator + pathLib + File.separator + libName2 + 
				" -D" + propName + " org.junit.runner.JUnitCore " + name;
		
		_runner.addClassPath(classPath1);
		
		Library lib1 = mock(Library.class);
		when(lib1.getFileName()).thenReturn(libName1);
		when(lib1.getPath()).thenReturn(new String());
		when(lib1.getName()).thenReturn(new String());
		when(lib1.getVersion()).thenReturn(new String());
		_runner.addLibrary(lib1);
		
		Library lib2 = mock(Library.class);
		when(lib2.getFileName()).thenReturn(libName2);
		when(lib2.getPath()).thenReturn(new String());
		when(lib2.getName()).thenReturn(new String());
		when(lib2.getVersion()).thenReturn(new String());
		_runner.addLibrary(lib2);
		
		when(_config.getPathLibrary()).thenReturn(pathLib);
		when(_config.propertyCount()).thenReturn(1);
		when(_config.getProperty(0)).thenReturn(propName);
		when(_config.classPathsAsParameterJVM()).thenReturn(classPath2);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("exec", 
						String.class, TestSuite.class, 
						org.testsuite.data.Test.class);
		method.setAccessible(true);
		assertEquals(ret, method.invoke(_runner, name, suite, test));
	}
	
	/**
	 * Tests if the correct command is created.
	 */
	@Test
	public void testExec() throws Exception{
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		
		String name = "test";
		String libName1 = "lib1.jar";
		String libName2 = "lib2.jar";
		String propName = "testing=\"true\"";
		String pathLib = "lib";
		String classPath1 = "bin";
		String classPath2 = "classpath";
		String ret = "java -cp " + classPath2 + File.pathSeparator + classPath1 +
				File.pathSeparator + pathLib + File.separator + libName1 + 
				File.pathSeparator + pathLib + File.separator + libName2 + 
				" -D" + propName + " org.junit.runner.JUnitCore " + name;
		
		_runner.addClassPath(classPath1);
		
		Library lib1 = mock(Library.class);
		when(lib1.getFileName()).thenReturn(libName1);
		when(lib1.getPath()).thenReturn(new String());
		when(lib1.getName()).thenReturn(new String());
		when(lib1.getVersion()).thenReturn(new String());
		_runner.addLibrary(lib1);
		
		Library lib2 = mock(Library.class);
		when(lib2.getFileName()).thenReturn(libName2);
		when(lib2.getPath()).thenReturn(new String());
		when(lib2.getName()).thenReturn(new String());
		when(lib2.getVersion()).thenReturn(new String());
		_runner.addLibrary(lib2);
		
		Config.getInstance().setPathLibrary(pathLib);
		Config.getInstance().addProperty(propName);
		Config.getInstance().addClassPath(classPath2);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("exec", 
						String.class, TestSuite.class, 
						org.testsuite.data.Test.class);
		method.setAccessible(true);
		assertEquals(ret, method.invoke(_runner, name, suite, test));
	}
	
	/**
	 * Tests if the evaluation correct.
	 */
	@Test
	public void testEvaluation() throws Exception{
		int ok = 10;
		
		Junit test = mock(Junit.class);
		when(test.getConsole()).thenReturn("OK (" + ok + " tests)");
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("evaluation", 
						org.testsuite.data.Test.class);
		method.setAccessible(true);
		method.invoke(_runner, test);
		
		verify(test).getConsole();
		verify(test).setOk(ok);
	}
	
	/**
	 * Tests if the evaluation correct.
	 */
	@Test
	public void testEvaluationFail() throws Exception{
		int ok = 10;
		int failures = 20;
		
		Junit test = mock(Junit.class);
		when(test.getConsole()).thenReturn("Tests run: " + ok + ",  Failures: " +
				failures);
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("evaluation", 
						org.testsuite.data.Test.class);
		method.setAccessible(true);
		method.invoke(_runner, test);
		
		verify(test).getConsole();
		verify(test).setOk(ok);
		verify(test).setFail(failures);
	}
	
	/**
	 * Tests if the created html table footer correct.
	 */
	@Test
	public void testCreateHtmlTableFooter() throws Exception {
		StringBuilder result = new StringBuilder();
		String td = "\t\t\t\t\t\t<td>";
		int right1 = 10;
		int right2 = 1;
		int fail1 = 0;
		int fail2 = 1;
		long duration1 = 400;
		long duration2 = 600;
		int suite = 0;
		
		Junit test1 = mock(Junit.class);
		when(test1.getOk()).thenReturn(right1);
		when(test1.getFail()).thenReturn(fail1);
		when(test1.getDurationTime()).thenReturn(duration1);
		
		Junit test2 = mock(Junit.class);
		when(test2.getOk()).thenReturn(right2);
		when(test2.getFail()).thenReturn(fail2);
		when(test2.getDurationTime()).thenReturn(duration2);
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(2);
		when(suite1.getTest(0)).thenReturn(test1);
		when(suite1.getTest(1)).thenReturn(test2);
		_runner.addTestSuite(suite1);
		
		result.append(td);
		result.append("&nbsp;</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(right1 + right2);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(fail1 + fail2);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append("00:00:01.000</td>");
		result.append(System.lineSeparator());
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlTableFooter", 
						int.class);
		method.setAccessible(true);
		assertEquals(result.toString(), method.invoke(_runner, suite));
	}
	
	/**
	 * Tests if the created html table over all tests in test this runner
	 * correct.
	 */
	@Test
	public void testCreateResultTestRunnerTable() throws Exception {
		StringBuilder result = new StringBuilder("\t\t\t\t<table>");
		result.append(System.lineSeparator());
		
		int right1 = 10;
		int right2 = 1;
		int right3 = 0;
		int right4 = 0;
		int fail1 = 0;
		int fail2 = 1;
		int fail3 = 0;
		int fail4 = 0;
		boolean term1 = false;
		boolean term2 = true;
		boolean term3 = false;
		boolean term4 = false;
		boolean exec1 = true;
		boolean exec2 = true;
		boolean exec3 = false;
		boolean exec4 = false;
		boolean exists1 = true;
		boolean exists2 = true;
		boolean exists3 = true;
		boolean exists4 = false;
		long duration1 = 400;
		long duration2 = 600;
		long duration3 = 0;
		long duration4 = 0;
		String tr = "\t\t\t\t\t<tr>";
		String tr_end = "\t\t\t\t\t</tr>";
		String th = "\t\t\t\t\t\t<th>";
		String td = "\t\t\t\t\t\t<td>";
		
		Junit test1 = mock(Junit.class);
		when(test1.getOk()).thenReturn(right1);
		when(test1.getFail()).thenReturn(fail1);
		when(test1.getDurationTime()).thenReturn(duration1);
		when(test1.isExists()).thenReturn(exists1);
		when(test1.isExecuted()).thenReturn(exec1);
		when(test1.isTerminated()).thenReturn(term1);
		
		Junit test2 = mock(Junit.class);
		when(test2.getOk()).thenReturn(right2);
		when(test2.getFail()).thenReturn(fail2);
		when(test2.getDurationTime()).thenReturn(duration2);
		when(test2.isExists()).thenReturn(exists2);
		when(test2.isExecuted()).thenReturn(exec2);
		when(test2.isTerminated()).thenReturn(term2);
		
		Junit test3 = mock(Junit.class);
		when(test3.getOk()).thenReturn(right3);
		when(test3.getFail()).thenReturn(fail3);
		when(test3.getDurationTime()).thenReturn(duration3);
		when(test3.isExists()).thenReturn(exists3);
		when(test3.isExecuted()).thenReturn(exec3);
		when(test3.isTerminated()).thenReturn(term3);
		
		Junit test4 = mock(Junit.class);
		when(test4.getOk()).thenReturn(right4);
		when(test4.getFail()).thenReturn(fail4);
		when(test4.getDurationTime()).thenReturn(duration4);
		when(test4.isExists()).thenReturn(exists4);
		when(test4.isExecuted()).thenReturn(exec4);
		when(test4.isTerminated()).thenReturn(term4);
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(2);
		when(suite1.getTest(0)).thenReturn(test1);
		when(suite1.getTest(1)).thenReturn(test2);
		_runner.addTestSuite(suite1);
		
		TestSuite suite2 = mock(TestSuite.class);
		when(suite2.testCount()).thenReturn(2);
		when(suite2.getTest(0)).thenReturn(test3);
		when(suite2.getTest(1)).thenReturn(test4);
		_runner.addTestSuite(suite2);
		
		result.append(tr);
		result.append(System.lineSeparator());
		
		result.append("\t\t\t\t\t\t<th colspan=\"3\">");
		result.append(_bundle.getString("test_runner_result_name"));
		result.append("</th>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());
		result.append("\t\t\t\t\t\t<td colspan=\"3\">");
		result.append(JunitTestRunner.class.getName());
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_tests"));
		result.append("</th>");
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_tests_executed"));
		result.append("</th>");
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_tests_terminated"));
		result.append("</th>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(4);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(2);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(1);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_tests_ignored"));
		result.append("</th>");
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_tests_not_exists"));
		result.append("</th>");
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_right"));
		result.append("</th>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(1);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(1);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(right1 + right2 + right3 + right4);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());

		result.append(th);
		result.append(_bundle.getString("test_runner_result_wrong"));
		result.append("</th>");
		result.append(System.lineSeparator());

		result.append("\t\t\t\t\t\t<th colspan=\"2\">");
		result.append(_bundle.getString("test_runner_result_duration"));
		result.append("</th>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append(tr);
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(fail1 + fail2 + fail3 + fail4);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append("\t\t\t\t\t\t<td colspan=\"2\">00:00:01.000</td>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append("\t\t\t\t</table>");
		result.append(System.lineSeparator());
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod(
						"createResultTestRunnerTable");
		method.setAccessible(true);
		assertEquals(result.toString(), method.invoke(_runner));
	}
	
	/**
	 * Tests whether runWithoutJvm returns false.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test
	public void testRunWithoutJvmOld() throws Exception {
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		String name = "Test";
		int exit = TestRunner.EXIT_NO_TEST;
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("runWithoutJvm", 
						String.class, org.testsuite.data.Test.class, int.class);
		method.setAccessible(true);
		
		assertFalse((boolean)method.invoke(_runner, name, test, exit));
	}
	
	/**
	 * Tests whether runWithoutJvm returns false.
	 * 
	 * FIXME Create test correctly.
	 */
	@Test
	public void testRunWithoutJvm() throws Exception {
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		String name = "Test";
		
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("runWithoutJvm", 
						String.class, org.testsuite.data.Test.class);
		method.setAccessible(true);
		
		assertFalse((boolean)method.invoke(_runner, name, test));
	}
}
