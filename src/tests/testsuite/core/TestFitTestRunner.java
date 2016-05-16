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
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.data.Config;
import org.testsuite.data.Fit;
import org.testsuite.data.Library;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {org.testsuite.core.FitTestRunner}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitTestRunner.class})
public class TestFitTestRunner {

	/**
	 * Holds the instance of the FitTestRunner
	 */
	private FitTestRunner _runner;

	/**
	 * Save the mock of configuration
	 */
	private Config _config;
	
	/**
	 * Saves the count of run tests.
	 */
	private int _runCount;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		_runner = new FitTestRunner(_config);
		_runCount = 0;
	}

	/**
	 * Tests if FitTestRunner has been derived from the TestRunner class.
	 */
	@Test
	public void testDerivedFromTestRunner() {
		assertEquals(org.testsuite.core.TestRunner.class.getName(),
				FitTestRunner.class.getSuperclass().getName());
	}
	
	/**
	 * Tests if the will be canceled if the test file does not exist.
	 */
	@Test
	public void testRunWithNonExistingTestFile() {
		String suiteName = "suite";
		String pathSrc = "src";
		String packageName = "package";
		String testName = "test";
		
		when(_config.getPathSrc()).thenReturn(pathSrc);

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(false);
		when(fit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(fit);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		_runner.addTestSuite(suite);
		
		_runner.run();
		
		verify(_config).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(fit).isExists();
		verify(fit, never()).isExecuted();
		verify(fit).setExitStatus(100);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, times(2)).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, times(2)).getPackage();
		verify(suite).isExists();
	}
	
	/**
	 * Tests if the will be canceled if the test does not executed.
	 */
	@Test
	public void testRunWithNoneExecutedTest() {
		String suiteName = "suite";
		String pathSrc = "src";
		String packageName = "package";
		String testName = "test";
		
		when(_config.getPathSrc()).thenReturn(pathSrc);

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(true);
		when(fit.isExecuted()).thenReturn(false);
		when(fit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(fit);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		_runner.addTestSuite(suite);
		
		_runner.run();
		
		verify(_config).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(fit).isExists();
		verify(fit).isExecuted();
		verify(fit, never()).setExitStatus(100);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, times(2)).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, times(2)).getPackage();
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

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(true);
		when(fit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(fit);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(false);
		_runner.addTestSuite(suite);
		
		_runner.run();
		
		verify(_config).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(fit, never()).isExists();
		verify(fit, never()).isExecuted();
		verify(fit).setExitStatus(100);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, times(2)).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, times(2)).getPackage();
		verify(suite).isExists();
	}

	/**
	 * Tests if the test is performed correctly.
	 */
	@Test
	public void testRun() throws Exception {
		String suiteName = "suite";
		String pathSrc = "src";
		String packageName = "package.package2";
		String testName = "test";
		String pathResult = "result";
		String pathSuitesResult = "suites";
		String extension = "fit";
		String resultPath = pathResult + File.separator + pathSuitesResult +
				File.separator + packageName.replaceAll("\\.", File.separator);
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

		_runner.setFileExtension(extension);
		
		when(_config.getPathSrc()).thenReturn(pathSrc);
		when(_config.getPathResult()).thenReturn(pathResult);
		when(_config.getPathSuitesResult()).thenReturn(pathSuitesResult);
		
		File fResultPath = mock(File.class);
		when(fResultPath.exists()).thenReturn(true);
		PowerMockito.whenNew(File.class)
			.withArguments(resultPath)
			.thenReturn(fResultPath);
		
		InputStream isConsole = mock(InputStream.class);
		InputStream isError = mock(InputStream.class);

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(true);
		when(fit.isExecuted()).thenReturn(true);
		when(fit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(suiteName);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(fit);
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
		when(error.readLine())
			.thenReturn("1 right 2 wrong 3 ignore 4 exception", null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(console);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(error);
		
		_runner.addTestEventListener(new TestEventListener() {
			@Override
			public void testExecutedCompleted(TestEvent te) {
				_runCount++;
			}

			@Override
			public void testEnd(TestEvent te) {
			}
		});
		
		_runner.run();
		
		assertEquals(1, _runCount);
		
		PowerMockito.verifyNew(File.class).withArguments(resultPath);
		verify(fResultPath).exists();
		
		String exec = "java -cp " + pathClass + ":" + pathLib + "/" + lib +
				" -D" + prop + " fit.FileRunner " + pathSrc + File.separator +
				packageName.replaceAll("\\.", File.separator) + File.separator + 
				testName + "." + extension + " " + pathResult + File.separator +
				pathSuitesResult + File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + ".html";
		verify(runtime).exec(exec);
		
		verify(process).waitFor();
		
		verify(_config).getPathSrc();
		verify(_config).getPathResult();
		verify(_config).getPathSuitesResult();
		
		verify(fit).isExists();
		verify(fit).isExecuted();
		verify(fit).setExitStatus(0);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, atLeastOnce()).getName();
		verify(fit).setStart(Matchers.anyLong());
		verify(fit).setEnd(Matchers.anyLong());
		verify(fit).getDurationTime();
		verify(fit).setStringConsole(Matchers.anyString());
		verify(fit).setOk(1);
		verify(fit).setFail(2);
		verify(fit).setIgnore(3);
		verify(fit).setException(4);
		verify(fit).setError(Matchers.anyString());
		
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
		
		String ret = "\t\t\t\t\t\t<th style=\"width: 50%;\">" + suiteName +
				"</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Erfolgreich</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Falsch</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Ignoriert</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Fehlerhaft</th>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<th>Zeit</th>" + System.lineSeparator() + 
				"\t\t\t\t\t</tr>" + System.lineSeparator() + "\t\t\t\t\t<tr>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<th colspan=\"6\">" +
				packageName + "</th>" + System.lineSeparator();
		
		Method method = 
				FitTestRunner.class.getDeclaredMethod("createHtmlTableHead",
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
		String console="console";
		String error = "error";
		String resultSuite = "1";
		int ok = 1;
		int fail = 0;
		int ignore = 0;
		int exception = 0;
		String duration = "00:00:01.897";
		int suiteId = 0;
		int testId = 0;
		String packageName = "test.fit";
		
		String ret = "\t\t\t\t\t\t<td class=\"pass\"><a href=\"" + resultSuite + 
				File.separator + packageName.replaceAll("\\.", File.separator) +
				File.separator + testName + ".html\">" + testName + "</a>" +
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"pass\">" + ok + "</td>" + 
				System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"pass\">" + fail + "</td>" + 
				System.lineSeparator() +
				"\t\t\t\t\t\t<td class=\"pass\">" + ignore + "</td>" + 
				System.lineSeparator() +
				"\t\t\t\t\t\t<td class=\"pass\">" + exception + "</td>" + 
				System.lineSeparator() + "\t\t\t\t\t\t<td class=\"pass\">" + duration + 
				"</td>" + System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
		Method method = 
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Fit test = mock(Fit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(true);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getIgnore()).thenReturn(ignore);
		when(test.getException()).thenReturn(exception);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test, times(2)).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test).getOk();
		order.verify(test).getFail();
		order.verify(test).getIgnore();
		order.verify(test).getException();
		order.verify(test).getDurationTimeFormattedString();
		
		verify(suite, times(15)).getTest(0);
	}
	
	/**
	 * Tests if the line of HTML is generated correctly for a none executed
	 * test.
	 */
	@Test
	public void testCreateHtmlColumnWithNoneExecutedTest() throws Exception {
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
		String console="console";
		String error = "error";
		String resultSuite = "1";
		int ok = 1;
		int fail = 2;
		int ignore = 0;
		int exception = 4;
		String duration = "00:00:01.897";
		int suiteId = 0;
		int testId = 0;
		String packageName = "test.fit";
		
		String ret = "\t\t\t\t\t\t<td class=\"ignore\"><a href=\"" + resultSuite + 
				File.separator + packageName.replaceAll("\\.", File.separator) +
				File.separator + testName + ".html\">" + testName + "</a>" + 
				testOut + "\t\t\t\t\t\t</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td class=\"ignore\" " +
				"colspan=\"4\">wurde nicht ausgeführt</td>" +
				System.lineSeparator();
		
		when(_config.getPathSuitesResult()).thenReturn(resultSuite);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(testOut);
		
		Method method = 
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Fit test = mock(Fit.class);
		when(test.isExists()).thenReturn(true);
		when(test.isExecuted()).thenReturn(false);
		when(test.getName()).thenReturn(testName);
		when(test.getError()).thenReturn(error);
		when(test.getIn()).thenReturn(console);
		when(test.getOk()).thenReturn(ok);
		when(test.getFail()).thenReturn(fail);
		when(test.getIgnore()).thenReturn(ignore);
		when(test.getException()).thenReturn(exception);
		when(test.getDurationTimeFormattedString()).thenReturn(duration);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn(packageName);
		_runner.addTestSuite(suite);
		
		assertEquals(ret, method.invoke(_runner, 0, 0, html));
		
		InOrder order = inOrder(test, suite);
		order.verify(test).isExists();
		order.verify(test, times(2)).getName();
		order.verify(suite).getId();
		order.verify(test).getId();
		order.verify(test).getIn();
		order.verify(test).getError();
		order.verify(test).isExecuted();
		order.verify(test, never()).getOk();
		order.verify(test, never()).getFail();
		order.verify(test, never()).getIgnore();
		order.verify(test, never()).getException();
		order.verify(test, never()).getDurationTimeFormattedString();
		
		verify(suite, times(10)).getTest(0);
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
		String extension = "fit";
		String console = "console";
		String error = "error";
		int suiteId = 0;
		int testId = 0;
		
		String ret = "\t\t\t\t\t\t<td>" + srcName + File.separator + 
				packageName.replaceAll("\\.", File.separator) + File.separator +
				testName + "." + extension + "</td>" + System.lineSeparator() + 
				"\t\t\t\t\t\t<td colspan=\"5\">Test existiert nicht</td>" + 
				System.lineSeparator();
		
		_runner.setFileExtension(extension);
		
		HtmlOut html = mock(HtmlOut.class);
		when(html.generateTestOut(suiteId, testId, console, error))
			.thenReturn(new String());
		
		Method method = 
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		
		Fit test = mock(Fit.class);
		when(test.isExists()).thenReturn(false);
		when(test.isExecuted()).thenReturn(true);
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
		order.verify(test, never()).isExecuted();
		order.verify(test, never()).getOk();
		order.verify(test, never()).getFail();
		order.verify(test, never()).getIgnore();
		order.verify(test, never()).getException();
		order.verify(test, never()).getDurationTimeFormattedString();
		
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
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
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
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
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
				FitTestRunner.class.getDeclaredMethod("createHtmlColumn", 
						int.class, int.class, HtmlOut.class);
		method.setAccessible(true);
		method.invoke(_runner, 0, 0, null);
	}
}
