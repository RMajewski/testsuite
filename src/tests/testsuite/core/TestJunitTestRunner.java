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
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Library;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.JunitTestRunner}.
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
	 */
	private Config _config;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		_runner = new JunitTestRunner(_config);
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
	 */
	@Test
	public void testCreateHtmlColumn() throws Exception{
		String testName = "Test1";
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String resultSuite = "1";
		String console = "console";
		String error = "error";
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
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
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
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getId()).thenReturn(suiteId);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(14)).getTest(0);
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
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
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
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test, never()).getDurationTimeFormattedString();
		
		verify(suite, times(9)).getTest(0);
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
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite, never()).getId();
		order.verify(test, never()).getId();
		order.verify(test, never()).getIn();
		order.verify(test, never()).getError();
		order.verify(test, never()).isExecuted();
		order.verify(test, never()).getDurationTime();
		
		verify(suite, times(5)).getTest(0);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test suite id is passed.
	 */
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
	 */
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
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNullAsHtmlOut() throws Exception {
		Method method = 
				JunitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
	
	@Test
	public void testExec() {
		fail("Test not yet implemented.");
	}
	
	@Test
	public void testErrorEvaluation() {
		fail("Test not yet implemented.");
	}
}
