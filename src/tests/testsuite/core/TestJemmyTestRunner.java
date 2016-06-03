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
import org.testsuite.core.JemmyTestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.JemmyTestRunner}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JemmyTestRunner.class})
public class TestJemmyTestRunner {

	/**
	 * Holds the instance of the JunitTestRunner
	 */
	private JemmyTestRunner _runner;
	
	/**
	 * Save the mock of configuration
	 */
	private Config _config;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		_bundle = ResourceBundle.getBundle(JemmyTestRunner.BUNDLE_FILE);
		_runner = new JemmyTestRunner(_config);
	}

	/**
	 * Tests if JemmyTestRunner has been derived from the TestRunner class.
	 */
	@Test
	public void testDerivedFromTestRunner() {
		assertEquals(org.testsuite.core.TestRunner.class.getName(),
				JemmyTestRunner.class.getSuperclass().getName());
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
				"\t\t\t\t\t\t<th>Erfolgreich?</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Zeit</th>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() + "\t\t\t\t\t<tr>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<th colspan=\"3\">" +
				packageName + "</th>" + System.lineSeparator();
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlTableHead",
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
	@Ignore("Tests a deprecated method")
	@Test
	public void testCreateHtmlColumn() throws Exception {
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
		String console = "console";
		String error = "error";
		String testName = "Test1";
		String packageName = "tests";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 0;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td class=\"pass\">" + testName + 
				System.lineSeparator() + testOut + "\t\t\t\t\t\t</td>" +
				System.lineSeparator() + "\t\t\t\t\t\t<td class=\"pass\">Ja" +
				"</td>" + System.lineSeparator() + "\t\t\t\t\t\t<td " +
				"class=\"pass\">" + duration + "</td>" + System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(15)).getTest(0);
	}
	
	/**
	 * Tests whether the HTML output has been generated correctly when the test
	 * fault.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test
	public void testCreateHtmlColumnWithAFalseTest() throws Exception {
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
		String console = "console";
		String error = "error";
		String testName = "Test1";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 100;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td class=\"wrong\">" + testName + 
				System.lineSeparator() + testOut + "\t\t\t\t\t\t</td>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<td class=\"wrong\">" +
				"Nein</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + duration + "</td>" + 
				System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);

		when(_config.classPathsAsParameterJVM()).thenReturn(new String());

		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getExitStatus();
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(16)).getTest(0);
	}

	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test file does not exist.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test
	public void testCreateHtmlColumnWithNoneExistingTest() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int suiteId = 0;
		int testId = 0;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td>" + srcName + File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"2\">Test existiert nicht</td>" + 
				System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(new String());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(_config).getPathSrc();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		
		verify(suite, times(7)).getTest(0);
	}
	
	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test does not executed.
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test
	public void testCreateHtmlColumnWithNoneExecutedTest() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int suiteId = 0;
		int testId = 0;
		boolean executed = false; 
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + testName + 
				System.lineSeparator() + "\t\t\t\t\t\t</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"ignore\" colspan=\"2\">wurde nicht " +
				"ausgeführt</td>" + System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(new String());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		
		verify(suite, times(11)).getTest(0);
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
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, -1, 0, html);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test ID is passed. 
	 * 
	 * @deprecated
	 */
	@Ignore("Tests a deprecated method")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNoTestId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
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
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
	
	/**
	 * Tests if the line of HTML is generated correctly for a test.
	 */
	@Test
	public void testCreateHtmlRow() throws Exception {
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
		String console = "console";
		String error = "error";
		String testName = "Test1";
		String packageName = "tests";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 0;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td class=\"pass\">" + testName + 
				System.lineSeparator() + testOut + "\t\t\t\t\t\t</td>" +
				System.lineSeparator() + "\t\t\t\t\t\t<td class=\"pass\">Ja" +
				"</td>" + System.lineSeparator() + "\t\t\t\t\t\t<td " +
				"class=\"pass\">" + duration + "</td>" + System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(15)).getTest(0);
	}
	
	/**
	 * Tests whether the HTML output has been generated correctly when the test
	 * fault.
	 */
	@Test
	public void testCreateHtmlRowWithAFalseTest() throws Exception {
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
		String console = "console";
		String error = "error";
		String testName = "Test1";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 100;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td class=\"wrong\">" + testName + 
				System.lineSeparator() + testOut + "\t\t\t\t\t\t</td>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<td class=\"wrong\">" +
				"Nein</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"wrong\">" + duration + "</td>" + 
				System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);

		when(_config.classPathsAsParameterJVM()).thenReturn(new String());

		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).getExitStatus();
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(16)).getTest(0);
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
		int suiteId = 0;
		int testId = 0;
		boolean executed = true;
		
		String ret = "\t\t\t\t\t\t<td>" + srcName + File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"2\">Test existiert nicht</td>" + 
				System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(new String());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite, _config);
		order.verify(test).getExitStatus();
		order.verify(test).isExists();
		order.verify(test).isExecuted();
		order.verify(test).isTerminated();
		order.verify(test).isExists();
		order.verify(_config).getPathSrc();
		order.verify(suite).getPackage();
		order.verify(test).getName();
		
		verify(suite, times(7)).getTest(0);
	}
	
	/**
	 * Testing whether the line of HTML is generated correctly for a test when
	 * the test does not executed.
	 */
	@Test
	public void testCreateHtmlRowWithNoneExecutedTest() throws Exception {
		String testName = "Test1";
		String packageName = "tests.test";
		String srcName = "src";
		String extension = "java";
		String console = "console";
		String error = "error";
		int suiteId = 0;
		int testId = 0;
		boolean executed = false; 
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\">" + testName + 
				System.lineSeparator() + "\t\t\t\t\t\t</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"ignore\" colspan=\"2\">wurde nicht " +
				"ausgeführt</td>" + System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(eq(suiteId), eq(testId), eq(console), 
				eq(error), anyString())).thenReturn(new String());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getConsole()).thenReturn(console);
		when(test.isExecuted()).thenReturn(executed);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		when(_config.classPathsAsParameterJVM()).thenReturn(new String());
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getConsole();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		
		verify(suite, times(11)).getTest(0);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test suite id is passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlRowWithNoSuiteId() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
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
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
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
				JemmyTestRunner.class.getDeclaredMethod("createHtmlRow", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
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
				" -D" + propName + " " + name;
		
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
				JemmyTestRunner.class.getDeclaredMethod("exec", 
						String.class, TestSuite.class, 
						org.testsuite.data.Test.class);
		method.setAccessible(true);
		assertEquals(ret, method.invoke(_runner, name, suite, test));
	}
		
	/**
	 * Tests if the created html table footer correct.
	 */
	@Test
	public void testCreateHtmlTableFooter() throws Exception {
		StringBuilder result = new StringBuilder();
		String td = "\t\t\t\t\t\t<td>";
		int exit1 = 0;
		int exit2 = 100;
		long duration1 = 400;
		long duration2 = 600;
		int suite = 0;
		
		org.testsuite.data.Test test1 = mock(org.testsuite.data.Test.class);
		when(test1.getDurationTime()).thenReturn(duration1);
		when(test1.getExitStatus()).thenReturn(exit1);
		
		org.testsuite.data.Test test2 = mock(org.testsuite.data.Test.class);
		when(test2.getDurationTime()).thenReturn(duration2);
		when(test2.getExitStatus()).thenReturn(exit2);
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(2);
		when(suite1.getTest(0)).thenReturn(test1);
		when(suite1.getTest(1)).thenReturn(test2);
		_runner.addTestSuite(suite1);
		
		result.append(td);
		result.append("&nbsp;</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(_bundle.getString("createHtmlTableHead_ok"));
		result.append(": ");
		result.append(1);
		result.append("<br/>");
		result.append(_bundle.getString("createHtmlTableHead_exception"));
		result.append(": ");
		result.append(1);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append("00:00:01.000</td>");
		result.append(System.lineSeparator());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlTableFooter", 
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

		int exit1 = 0;
		int exit2 = 143;
		int exit3 = 0;
		int exit4 = 0;
		int exit5 = 10;
		boolean term1 = false;
		boolean term2 = true;
		boolean term3 = false;
		boolean term4 = false;
		boolean term5 = false;
		boolean exec1 = true;
		boolean exec2 = true;
		boolean exec3 = false;
		boolean exec4 = true;
		boolean exec5 = true;
		boolean exists1 = true;
		boolean exists2 = true;
		boolean exists3 = true;
		boolean exists4 = false;
		boolean exists5 = true;
		long duration1 = 400;
		long duration2 = 600;
		long duration3 = 0;
		long duration4 = 0;
		long duration5 = 100;
		String tr = "\t\t\t\t\t<tr>";
		String tr_end = "\t\t\t\t\t</tr>";
		String th = "\t\t\t\t\t\t<th>";
		String td = "\t\t\t\t\t\t<td>";
		
		org.testsuite.data.Test test1 = mock(org.testsuite.data.Test.class);
		when(test1.getDurationTime()).thenReturn(duration1);
		when(test1.isExists()).thenReturn(exists1);
		when(test1.isExecuted()).thenReturn(exec1);
		when(test1.isTerminated()).thenReturn(term1);
		when(test1.getExitStatus()).thenReturn(exit1);
		
		org.testsuite.data.Test test2 = mock(org.testsuite.data.Test.class);
		when(test2.getDurationTime()).thenReturn(duration2);
		when(test2.isExists()).thenReturn(exists2);
		when(test2.isExecuted()).thenReturn(exec2);
		when(test2.isTerminated()).thenReturn(term2);
		when(test2.getExitStatus()).thenReturn(exit2);
		
		org.testsuite.data.Test test3 = mock(org.testsuite.data.Test.class);
		when(test3.getDurationTime()).thenReturn(duration3);
		when(test3.isExists()).thenReturn(exists3);
		when(test3.isExecuted()).thenReturn(exec3);
		when(test3.isTerminated()).thenReturn(term3);
		when(test3.getExitStatus()).thenReturn(exit3);
		
		org.testsuite.data.Test test4 = mock(org.testsuite.data.Test.class);
		when(test4.getDurationTime()).thenReturn(duration4);
		when(test4.isExists()).thenReturn(exists4);
		when(test4.isExecuted()).thenReturn(exec4);
		when(test4.isTerminated()).thenReturn(term4);
		when(test4.getExitStatus()).thenReturn(exit4);
		
		org.testsuite.data.Test test5 = mock(org.testsuite.data.Test.class);
		when(test5.getDurationTime()).thenReturn(duration5);
		when(test5.isExists()).thenReturn(exists5);
		when(test5.isExecuted()).thenReturn(exec5);
		when(test5.isTerminated()).thenReturn(term5);
		when(test5.getExitStatus()).thenReturn(exit5);
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(2);
		when(suite1.getTest(0)).thenReturn(test1);
		when(suite1.getTest(1)).thenReturn(test2);
		_runner.addTestSuite(suite1);
		
		TestSuite suite2 = mock(TestSuite.class);
		when(suite2.testCount()).thenReturn(3);
		when(suite2.getTest(0)).thenReturn(test3);
		when(suite2.getTest(1)).thenReturn(test4);
		when(suite2.getTest(2)).thenReturn(test5);
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
		result.append(JemmyTestRunner.class.getName());
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
		result.append(5);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append(td);
		result.append(3);
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
		result.append(1);
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
		result.append(1);
		result.append("</td>");
		result.append(System.lineSeparator());
		
		result.append("\t\t\t\t\t\t<td colspan=\"2\">00:00:01.100</td>");
		result.append(System.lineSeparator());
		
		result.append(tr_end);
		result.append(System.lineSeparator());
		
		result.append("\t\t\t\t</table>");
		result.append(System.lineSeparator());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod(
						"createResultTestRunnerTable");
		method.setAccessible(true);
		assertEquals(result.toString(), method.invoke(_runner));
	}
}
