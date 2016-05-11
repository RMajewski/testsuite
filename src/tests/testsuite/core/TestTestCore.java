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
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

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

import junit.framework.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.TestCore}
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
	 */
	private Config _config;
	
	/**
	 * Initialize the tests
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		PowerMockito.whenNew(Config.class).withNoArguments().thenReturn(_config);
		
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
		PowerMockito.verifyNew(Config.class).withNoArguments();
		verify(_config).setPathSuitesResult(Matchers.anyString());
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
			.withArguments(_config, name)
			.thenReturn(parser);
		
		assertTrue(_core.readConfig(name));
		
		PowerMockito.verifyNew(ConfigParser.class)
			.withArguments(_config, name);
		
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
			.withArguments(_config, name)
			.thenReturn(parser);
		
		assertFalse(_core.readConfig(name));
		
		PowerMockito.verifyNew(ConfigParser.class)
			.withArguments(_config, name);
		
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
		JunitTestRunner runner1 = mock(JunitTestRunner.class);
		FitTestRunner runner2 = mock(FitTestRunner.class);
		
		Field field = TestCore.class.getDeclaredField("_testRunner");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<TestRunner> list = (List<TestRunner>)field.get(_core);
		list.add(runner1);
		list.add(runner2);
		
		_core.run();
		
		verify(runner1).run();
		verify(runner2).run();
	}

	/**
	 * Tests whether the HTML file was created.
	 */
	@Test
	public void testCreateResultHtml() throws Exception{
		fail("Test not yet implemented.");
	}

}
