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

import javax.xml.stream.XMLInputFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.ConfigParser;
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.JemmyTestRunner;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.ConfigParser}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigParser.class, XMLInputFactory.class})
public class TestConfigParser {

	/**
	 * Hold an instance of the ConfigParser
	 */
	private ConfigParser _parser;
	
	/**
	 * Holds the mock object for configuration.
	 */
	private Config _config;
	
	/**
	 * Holds the name of configuration file.
	 */
	private String _configFile;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		_configFile = "test.xml";
		
		_parser = new ConfigParser(_config, _configFile);
	}

	/**
	 * Tests if the configuration is returned.
	 */
	@Test
	public void testGetConfig() {
		assertEquals(_config, _parser.getConfig());
	}
	
	/**
	 * Tests if the configuration can be set.
	 */
	@Test
	public void testSetConfig() {
		_config = mock(Config.class);
		_parser.setConfig(_config);
		assertEquals(_config, _parser.getConfig());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as a
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigWithNullAsParameter() {
		_parser.setConfig(null);
	}
	
	@Test
	public void testGetConfigFile() {
		assertEquals(_configFile, _parser.getConfigFile());
	}
	
	@Test
	public void testSetConfigFile() {
		_configFile = "neu.xml";
		_parser.setConfigFile(_configFile);
		assertEquals(_configFile, _parser.getConfigFile());
	}
	
	@Test
	public void testGetTestRunnerList() {
		assertEquals(0, _parser.getTestRunnerList().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigFileWithNullAsParameter() {
		_parser.setConfigFile(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigFileWithEmptyStringAsParameter() {
		_parser.setConfigFile(new String());
	}

	@Test
	public void testParseWithNonExistingFileReturnFalse() throws Exception {
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(_configFile)
			.thenReturn(file);
		
		assertFalse(_parser.parse());
		
		PowerMockito.verifyNew(File.class).withArguments(_configFile);
		verify(file).exists();
	}

	@Test
	public void testParse() throws Exception {
		_parser.setConfigFile("src/tests/test_parser.xml");
		
		assertTrue(_parser.parse());
		
		verify(_config).setPathResult("result");
		verify(_config).setPathSrc("src");
		verify(_config).setPathLibrary("lib");
		verify(_config).setMaxDuration(30000);
		verify(_config).setCreateHtml(true);
		verify(_config).addProperty("testing=\"true\"");
		verify(_config).addClassPath("classpath1");
		verify(_config).addJavascriptFile("out.js");
		verify(_config).addStylesheetFile("out.css");
		
		List<TestRunner> list = _parser.getTestRunnerList();
		
		int suiteId = 0;
		int testId = 0;
		
		assertEquals(3, list.size());
		
		TestRunner runner = list.get(0);
		assertEquals(JemmyTestRunner.class.getName(),
				runner.getClass().getName());
		assertEquals("java", runner.getFileExtension());
		assertEquals("[h2]Test[/h2][p]This is a test.[/p]", 
				runner.getDescription());
		assertEquals(0, runner.libraryCount());
		
		
		runner = list.get(1);
		assertEquals(JunitTestRunner.class.getName(),
				runner.getClass().getName());
		assertEquals("java", runner.getFileExtension());
		assertEquals("[h2]Test[/h2][p]This is a test.[/p]", 
				runner.getDescription());
		assertEquals(2, runner.libraryCount());
		
		Library lib = runner.getLibrary(0);
		assertEquals("0.1", lib.getVersion());
		assertEquals("name1", lib.getName());
		assertEquals("path", lib.getPath());
		assertEquals("lib1", lib.getFileName());
		
		assertEquals(2, runner.testSuiteCount());
		
		Field field = TestRunner.class.getDeclaredField("_suites");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestSuite> suites = (List<TestSuite>)field.get(runner);
		
		TestSuite suite = suites.get(0);
		assertEquals(suiteId++, suite.getId());
		assertEquals("Test 1", suite.getName());
		assertEquals("test1", suite.getPackage());
		assertEquals(1, suite.testCount());
		
		org.testsuite.data.Test test = suite.getTest(0);
		assertEquals(testId++, test.getId());
		assertEquals("Test1Class", test.getName());
		assertFalse(test.isExecuted());
		
		suite = suites.get(1);
		assertEquals(suiteId++, suite.getId());
		assertEquals("Test 2", suite.getName());
		assertEquals("test2", suite.getPackage());
		assertEquals(2, suite.testCount());
		
		testId = 0;
		test = suite.getTest(0);
		assertEquals(testId++, test.getId());
		assertEquals("Test2Class", test.getName());
		assertTrue(test.isExecuted());
		
		test = suite.getTest(1);
		assertEquals(testId++, test.getId());
		assertEquals("Test3Class", test.getName());
		assertEquals("Test3", test.getCheckSource());
		
		
		lib = runner.getLibrary(1);
		assertEquals("0.2", lib.getVersion());
		assertEquals("name2", lib.getName());
		assertEquals(new String(), lib.getPath());
		assertEquals("lib2", lib.getFileName());
		
		runner = list.get(2);
		assertEquals(FitTestRunner.class.getName(),
				runner.getClass().getName());
		assertEquals("fit", runner.getFileExtension());
		assertEquals("[h2]Test[/h2][p]This is a test.[/p]", 
				runner.getDescription());
		assertEquals(0, runner.libraryCount());
		assertEquals(1, runner.classPathCount());
	}
}
