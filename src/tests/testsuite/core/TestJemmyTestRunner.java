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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
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
	 * Tests if the will be canceled if the test file does not exist.
	 */
	@Test
	public void testRunWithNonExistingTestFile() {
		String suiteName = "suite";
		String packageName = "package";
		String testName = "test";
		

		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		_runner.addTestSuite(suite);
		
		_runner.run();
		
		verify(test).isExists();
		verify(test).setExitStatus(100);
		verify(test, never()).setExists(Matchers.anyBoolean());
		verify(test).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite).isExists();
	}
	
	/**
	 * Tests if the will be canceled when the directory of the test file does
	 * not exist.
	 */
	@Test
	public void testRunWithNonExistingPath() {
		String suiteName = "suite";
		String pathSrc = "src";
		String packageName = "package";
		String testName = "test";
		
		when(_config.getPathSrc()).thenReturn(pathSrc);

		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(false);
		_runner.addTestSuite(suite);
		
		_runner.run();
		
		verify(_config, never()).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(test, never()).isExists();
		verify(test).setExitStatus(100);
		verify(test, never()).setExists(Matchers.anyBoolean());
		verify(test).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite).isExists();
	}

	/**
	 * Tests if the test is performed correctly.
	 */
	@Test
	public void testRun() throws Exception {
		String suiteName = "suite";
		String packageName = "package";
		String testName = "test";
		String pathLib = "lib";
		String pathClass = "bin";
		String lib = "lib1.jar";
		String prop = "test1=\"1\"";

		when(_config.getPathLibrary()).thenReturn(pathLib);
		when(_config.propertyCount()).thenReturn(1);
		when(_config.getProperty(0)).thenReturn(prop);
		
		Library library = mock(Library.class);
		when(library.getFileName()).thenReturn(lib);
		when(library.getPath()).thenReturn(new String());
		_runner.addLibrary(library);
		
		_runner.addClassPath(pathClass);
		
		InputStream isConsole = mock(InputStream.class);
		InputStream isError = mock(InputStream.class);

		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		_runner.addTestSuite(suite);
		
		Process process = mock(Process.class);
		when(process.waitFor()).thenReturn(0);
		when(process.getInputStream()).thenReturn(isConsole);
		when(process.getErrorStream()).thenReturn(isError);
		
		Runtime runtime = mock(Runtime.class);
		when(runtime.exec(Matchers.anyString())).thenReturn(process);
		
		PowerMockito.mockStatic(Runtime.class);
		PowerMockito.when(Runtime.getRuntime()).thenReturn(runtime);
		
		InputStreamReader isrConsole = mock(InputStreamReader.class);
		InputStreamReader isrError = mock(InputStreamReader.class);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(isConsole)
			.thenReturn(isrConsole);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(isError)
			.thenReturn(isrError);
		
		BufferedReader console = mock(BufferedReader.class);
		when(console.readLine()).thenReturn(null);
		
		BufferedReader error = mock(BufferedReader.class);
		when(error.readLine()).thenReturn(null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(console);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(error);
		
		_runner.run();
		
		String exec = "java -cp " + pathClass + ":" + pathLib + "/" + lib +
				" -D" + prop + " " + packageName + "." + testName;
		verify(runtime).exec(exec);
		
		verify(process).waitFor();
		verify(process).getInputStream();
		verify(process).getErrorStream();
		
		verify(test).isExists();
		verify(test).setExitStatus(0);
		verify(test, never()).setExists(Matchers.anyBoolean());
		verify(test, atLeastOnce()).getName();
		verify(test).setStart(Matchers.anyLong());
		verify(test).setEnd(Matchers.anyLong());
		verify(test).getDurationTime();
		verify(test).setStringConsole(Matchers.anyString());
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, atLeastOnce()).getPackage();
		verify(suite).isExists();
	}
	
	/**
	 * Tests if the column headings are properly generated for the HTML output.
	 */
	@Test
	public void testCreateHtmlTableHead() throws Exception {
		String suiteName = "TestSuite";
		String packageName = "package";
		
		String ret = "\t\t\t\t\t\t<th>" + suiteName + "</th>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<th>Erfolgreich?</th>" +
				System.lineSeparator() + "\t\t\t\t\t\t<th>Zeit</th>" +
				System.lineSeparator() + "\t\t\t\t\t</tr>" + 
				System.lineSeparator() + "\t\t\t\t\t<tr>" + 
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
	 */
	@Test
	public void testCreateHtmlColumn() throws Exception {
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String console = "console";
		String error = "error";
		String testName = "Test1";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 0;
		
		String ret = "\t\t\t\t\t\t<td>" + testName + System.lineSeparator() + 
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() +
				"\t\t\t\t\t\t<td>Ja</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td>" + duration + "</td>" + 
				System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(7)).getTest(0);
	}
	
	/**
	 * Tests whether the HTML output has been generated correctly when the test
	 * fault.
	 */
	@Test
	public void testCreateHtmlColumnWithAFalseTest() throws Exception {
		String testOut = "\t\t\t\t\t\t<div class=\"right\"><a " +
				"href=\"javascript:togleDisplayId(0, 0)\"> Ausgabe</a></div>" +
				System.lineSeparator() + "\t\t\t\t\t\t<div " +
				"class=\"testoutInvisible\" id=\"id_0_0\">" + 
				System.lineSeparator() + "\t\t\t\t\t\t\t<div " +
				"class=\"console\">Console</div>" + System.lineSeparator() +
				"\t\t\t\t\t\t\t<div class=\"error\">Fehler</div>" +
				System.lineSeparator() + "\t\t\t\t\t\t</div>" + 
				System.lineSeparator();
		String console = "console";
		String error = "error";
		String testName = "Test1";
		int suiteId = 0;
		int testId = 0;
		String duration = "00:00:01.897";
		int exit = 100;
		
		String ret = "\t\t\t\t\t\t<td>" + testName + System.lineSeparator() + 
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() +
				"\t\t\t\t\t\t<td>Nein</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td>" + duration + "</td>" + 
				System.lineSeparator();
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		when(test.getExitStatus()).thenReturn(exit);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).getExitStatus();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(7)).getTest(0);
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
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td>" + srcName + File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"2\">Test existiert nicht</td>" + 
				System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(new String());
		
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.isExists()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		when(_config.getPathSrc()).thenReturn(srcName);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test);
		order.verify(test).isExists();
		order.verify(test).getName();
		
		verify(suite, times(2)).getTest(0);
		verify(suite).getPackage();
	}
	
	/**
	 * Tests whether the error IllegalArgumentException will be canceled if no
	 * valid test suite id is passed.
	 */
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
	 */
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
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateHtmlColumnWithNullAsHtmlOut() throws Exception {
		Method method = 
				JemmyTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
}
