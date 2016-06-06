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

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.ConfigParser;
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.core.TestCore;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.TestCore}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TestCore.class})
public class TestTestCore {

	/**
	 * Save the TestCore class
	 */
	private TestCore _core;
	
	/**
	 * Save the mock object of Config
	 * 
	 * @deprecated
	 */
	private Config _config;
	
	/**
	 * Initialize the tests
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Config.getInstance().clearAll();
		_core = new TestCore();
	}

	/**
	 * Tests if the initialization of the class TestCore is correct.
	 * @throws Exception 
	 * 
	 * @see org.testsuite.core.TestCore#TestCore()
	 */
	@Test
	public void testTestCore() throws Exception {
		assertNotNull(Config.getInstance().getPathSuitesResult());
		assertFalse(Config.getInstance().getPathSuitesResult().isEmpty());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the configuration file.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadConfigNullAsConfigFile() throws Exception{
		assertFalse(_core.readConfig(null));
	}

	/**
	 * Tests whether the error IllegalArgumentException occurs when an empty
	 * string is passed as the configuration file.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadConfigEmptyStringAsConfigFile() throws Exception {
		assertFalse(_core.readConfig(new String()));
	}
	
	/**
	 * Tests if the configuration file is read correctly.
	 */
	@Test
	public void testReadConfig() throws Exception {
		String name = "test.xml";
		
		ConfigParser parser = mock(ConfigParser.class);
		when(parser.parse()).thenReturn(true);
		
		PowerMockito.whenNew(ConfigParser.class)
			.withArguments(name)
			.thenReturn(parser);
		
		assertTrue(_core.readConfig(name));
		
		PowerMockito.verifyNew(ConfigParser.class)
			.withArguments(name);
		
		verify(parser).parse();
		verify(parser).getTestRunnerList();
	}
	
	/**
	 * Tests if false is returned if an error occurs while parsing occurred.
	 */
	@Test
	public void testReadConfigReturnFalse() throws Exception {
		String name = "test.xml";
		
		ConfigParser parser = mock(ConfigParser.class);
		when(parser.parse()).thenReturn(false);
		
		PowerMockito.whenNew(ConfigParser.class)
			.withArguments(name)
			.thenReturn(parser);
		
		assertFalse(_core.readConfig(name));
		
		PowerMockito.verifyNew(ConfigParser.class)
			.withArguments(name);
		
		verify(parser).parse();
		verify(parser, never()).getTestRunnerList();
	}
	
	/**
	 * Tests whether all text files exist.
	 */
	@Test
	public void testCheckFilesExists() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JunitTestRunner runner1 = mock(JunitTestRunner.class);
		FitTestRunner runner2 = mock(FitTestRunner.class);
		
		Field field = TestCore.class.getDeclaredField("_testRunner");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestRunner> list = (List<TestRunner>)field.get(_core);
		list.add(runner1);
		list.add(runner2);
		
		_core.checkFileExists();
		
		verify(runner1).checkFileExists();
		verify(runner2).checkFileExists();
	}
	
	/**
	 * Tests if the tests are run.
	 */
	@Test
	public void testRun() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		org.testsuite.data.Test test1 = mock(org.testsuite.data.Test.class);
		org.testsuite.data.Test test2 = mock(org.testsuite.data.Test.class);
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(1);
		when(suite1.getTest(0)).thenReturn(test1);
		
		TestSuite suite2 = mock(TestSuite.class);
		when(suite2.testCount()).thenReturn(1);
		when(suite2.getTest(0)).thenReturn(test2);
		
		JunitTestRunner runner1 = mock(JunitTestRunner.class);
		when(runner1.testSuiteCount()).thenReturn(1);
		when(runner1.getTestSuite(0)).thenReturn(suite1);
		
		FitTestRunner runner2 = mock(FitTestRunner.class);
		when(runner2.testSuiteCount()).thenReturn(1);
		when(runner2.getTestSuite(0)).thenReturn(suite2);
		
		Field field = TestCore.class.getDeclaredField("_testRunner");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestRunner> list = (List<TestRunner>)field.get(_core);
		list.add(runner1);
		list.add(runner2);
		
		_core.run();
		
		verify(runner1).run(eq(suite1), eq(test1), isNull(Thread.class));
		verify(runner2).run(eq(suite2), eq(test2), isNull(Thread.class));
	}

	/**
	 * Tests whether the HTML file was created.
	 */
	@Test
	public void testCreateResultHtml() throws Exception{
		String pathResult = "result";
		
		Config.getInstance().setPathResult(pathResult);
		
		HtmlOut html = mock(HtmlOut.class);
		
		PowerMockito.whenNew(HtmlOut.class)
			.withAnyArguments()
			.thenReturn(html);
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		
		PowerMockito.whenNew(File.class)
			.withAnyArguments()
			.thenReturn(file);
		
		Config.getInstance().setCreateHtml(true);
		
		Field field = TestCore.class.getDeclaredField("_testRunner");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestRunner> list = (List<TestRunner>)field.get(_core);
		
		TestRunner runner = mock(TestRunner.class);
		list.add(runner);
		
		runner = mock(TestRunner.class);
		list.add(runner);
		
		_core.createResultHtml();
		
		PowerMockito.verifyNew(HtmlOut.class)
			.withArguments(Matchers.anyString());
		
		verify(html).htmlHead();
		verify(html, times(3)).writeHtml(Matchers.anyString());
		verify(html).htmlEnd();
	}

	/**
	 * Tests whether the HTML output is not generated when the configuration
	 * that is not specified.
	 */
	@Test
	public void testCreateResultHtmlWithNoCreateAtConfig() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		
		PowerMockito.whenNew(HtmlOut.class)
			.withAnyArguments().thenReturn(html);
		
		Config.getInstance().setCreateHtml(false);
		
		Field field = TestCore.class.getDeclaredField("_testRunner");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestRunner> list = (List<TestRunner>)field.get(_core);
		
		TestRunner runner = mock(TestRunner.class);
		list.add(runner);
		
		runner = mock(TestRunner.class);
		list.add(runner);
		
		_core.createResultHtml();
		
		PowerMockito.verifyNew(HtmlOut.class, never())
			.withArguments(Matchers.anyString());
		
		verify(html, never()).htmlHead();
		verify(html, never()).writeHtml(Matchers.anyString());
		verify(html, never()).htmlEnd();
	}
}
