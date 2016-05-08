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
import org.testsuite.core.TestRunner;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.TestRunner}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TestRunner.class})
public class TestTestRunner {

	/**
	 * Hold an instance of the TestRunner
	 */
	private TestRunnerImplementation _runner;
	
	/**
	 * Save the file extension of test file.
	 */
	private String _fileExtension;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_fileExtension = "test";
		_runner = new TestRunnerImplementation(_fileExtension);
	}
	
	/**
	 * Verifies that the datas has been initialized.
	 */
	@Test
	public void testTestRunner() {
		assertEquals(0, _runner.testSuiteCount());
		assertEquals(_fileExtension, _runner.getFileExtension());
	}
	
	/**
	 * Verifies that the correct number of test suites is returned.
	 */
	@Test
	public void testTestSuiteCount() {
		assertEquals(0, _runner.testSuiteCount());
	}
	
	/**
	 * Checks whether a test suite can be added to the list.
	 */
	@Test
	public void testAddTestSuite() {
		TestSuite suite = mock(TestSuite.class);
		_runner.addTestSuite(suite);
		assertEquals(1, _runner.testSuiteCount());
	}
	
	/**
	 * Tests if a non-existent directory is detected.
	 */
	@Test
	public void testCheckFileExistsWithNonExistsPath() throws Exception {
		String path = "test";
		String testName = "test";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(Matchers.anyString())
			.thenReturn(file);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.testCount()).thenReturn(1);
		when(suite.getPackage()).thenReturn(path);
		_runner.addTestSuite(suite);
		
		_runner.checkFileExists();
		
		PowerMockito.verifyNew(File.class).withArguments(path);
		verify(file, times(1)).exists();
		verify(test, times(0)).getName();
		verify(test, times(1)).setExists(false);
		verify(suite, times(1)).getTest(0);
		verify(suite, times(1)).getPackage();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, times(1)).setExists(false);
	}
	
	/**
	 * Tests if a non-existent file is detected.
	 */
	@Test
	public void testCheckFileExistsWithNonExistsFile() throws Exception {
		String path = "test";
		String testName = "testen";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true, false);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(Matchers.anyString())
			.thenReturn(file);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.testCount()).thenReturn(1);
		when(suite.getPackage()).thenReturn(path, path);
		_runner.addTestSuite(suite);
		
		_runner.checkFileExists();
		
		PowerMockito.verifyNew(File.class).withArguments(path);
		PowerMockito.verifyNew(File.class).withArguments(path + File.separator +
				testName + "." + _fileExtension);
		verify(file, times(2)).exists();
		verify(test, times(1)).getName();
		verify(test, times(1)).setExists(false);
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, times(2)).getPackage();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, times(1)).setExists(true);
	}
	
	/**
	 * Tests are marked if file and directory as exists.
	 */
	@Test
	public void testCheckFileExists() throws Exception{
		String path = "test";
		String testName = "testen";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true, true);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(Matchers.anyString())
			.thenReturn(file);
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.testCount()).thenReturn(1);
		when(suite.getPackage()).thenReturn(path, path);
		_runner.addTestSuite(suite);
		
		_runner.checkFileExists();
		
		PowerMockito.verifyNew(File.class).withArguments(path);
		PowerMockito.verifyNew(File.class).withArguments(path + File.separator +
				testName + "." + _fileExtension);
		verify(file, times(2)).exists();
		verify(test, times(1)).getName();
		verify(test, times(1)).setExists(true);
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite, times(2)).getPackage();
		verify(suite, atLeastOnce()).testCount();
		verify(suite, times(1)).setExists(true);
	}
	
	/**
	 * Tests if the file extension is returned correctly.
	 */
	@Test
	public void testGetFileExtension() {
		assertEquals(_fileExtension, _runner.getFileExtension());
	}
	
	/**
	 * Tests if the file extension can be set correctly.
	 */
	@Test
	public void testSetFileExtension() {
		_fileExtension = "new_Test";
		_runner.setFileExtension(_fileExtension);
		assertEquals(_fileExtension, _runner.getFileExtension());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException occurring when null is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetFileExtensionWithNullAsParameter() {
		_runner.setFileExtension(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException occurs when an empty
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetFileExtensionWithEmptyStringAsParameter() {
		_runner.setFileExtension(new String());
	}
}
