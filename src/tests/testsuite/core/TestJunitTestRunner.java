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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Fit;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.JunitTestRunner}.
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
	 * Save the file extension of test file.
	 */
	private String _fileExtension;
	
	/**
	 * Save the mock of configuration
	 */
	private Config _config;
	

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_fileExtension = "test";
		_config = mock(Config.class);
		_runner = new JunitTestRunner(_fileExtension, _config);
	}

	/**
	 * Tests if FitTestRunner has been derived from the TestRunner class.
	 */
	@Test
	public void testDerivedFromTestRunner() {
		assertEquals(org.testsuite.core.TestRunner.class.getName(),
				FitTestRunner.class.getSuperclass().getName());
	}
	
	@Test
	public void testRunWithNonExistingTestFile() {
		String suiteName = "suite";
		String packageName = "package";
		String testName = "test";
		

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
		
		verify(fit).isExists();
		verify(fit).setExitStatus(100);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite).isExists();
	}
	
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
		
		verify(_config, never()).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(fit, never()).isExists();
		verify(fit).setExitStatus(100);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit).getName();
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite).isExists();
	}

	@Test
	public void testRun() throws Exception {
		String suiteName = "suite";
		String packageName = "package";
		String testName = "test";
		int inputAvailable = 100;
		
		InputStream is = mock(InputStream.class);
		when(is.available()).thenReturn(inputAvailable);

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(true);
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
		when(process.getInputStream()).thenReturn(is);
		
		Runtime runtime = mock(Runtime.class);
		when(runtime.exec(Matchers.anyString())).thenReturn(process);
		
		PowerMockito.mockStatic(Runtime.class);
		PowerMockito.when(Runtime.getRuntime()).thenReturn(runtime);
		
		InputStreamReader isr = mock(InputStreamReader.class);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withAnyArguments()
			.thenReturn(isr);
		
		BufferedReader console = mock(BufferedReader.class);
		when(console.readLine())
			.thenReturn("OK (2 tests)", null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withParameterTypes(Reader.class)
			.withArguments(isr)
			.thenReturn(console);
		
		_runner.run();
		
		// FIXME exec überprüfen
		verify(runtime).exec(Matchers.anyString());
		
		verify(process).waitFor();
		verify(process, times(2)).getInputStream();
		verify(process).getErrorStream();
		
		verify(fit).isExists();
		verify(fit).setExitStatus(0);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, atLeastOnce()).getName();
		verify(fit).setStart(Matchers.anyLong());
		verify(fit).setEnd(Matchers.anyLong());
		verify(fit).getDurationTime();
		verify(fit).setIn(Matchers.any(InputStream.class));
		verify(fit).setOk(2);
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, atLeastOnce()).getPackage();
		verify(suite).isExists();
	}

	@Test
	public void testRunWithFailure() throws Exception {
		String suiteName = "suite";
		String packageName = "package";
		String testName = "test";
		int inputAvailable = 100;
		
		InputStream is = mock(InputStream.class);
		when(is.available()).thenReturn(inputAvailable);

		Fit fit = mock(Fit.class);
		when(fit.isExists()).thenReturn(true);
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
		when(process.getInputStream()).thenReturn(is);
		
		Runtime runtime = mock(Runtime.class);
		when(runtime.exec(Matchers.anyString())).thenReturn(process);
		
		PowerMockito.mockStatic(Runtime.class);
		PowerMockito.when(Runtime.getRuntime()).thenReturn(runtime);
		
		InputStreamReader isr = mock(InputStreamReader.class);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withAnyArguments()
			.thenReturn(isr);
		
		BufferedReader console = mock(BufferedReader.class);
		when(console.readLine())
			.thenReturn("Tests run: 1,  Failures: 2", null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withParameterTypes(Reader.class)
			.withArguments(isr)
			.thenReturn(console);
		
		_runner.run();
		
		verify(is).mark(inputAvailable);
		verify(is).available();
		verify(is).reset();
		
		// FIXME exec überprüfen
		verify(runtime).exec(Matchers.anyString());
		
		verify(process).waitFor();
		verify(process, times(2)).getInputStream();
		verify(process).getErrorStream();
		
		verify(fit).isExists();
		verify(fit).setExitStatus(0);
		verify(fit, never()).setExists(Matchers.anyBoolean());
		verify(fit, atLeastOnce()).getName();
		verify(fit).setStart(Matchers.anyLong());
		verify(fit).setEnd(Matchers.anyLong());
		verify(fit).getDurationTime();
		verify(fit).setIn(Matchers.any(InputStream.class));
		verify(fit).setOk(1);
		verify(fit).setFail(2);
		
		verify(suite).getName();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, atLeastOnce()).getPackage();
		verify(suite).isExists();
	}
}
