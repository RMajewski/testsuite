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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Library;
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
public class TestTestRunner extends TestRunnerHelper {

	/**
	 * Hold an instance of the TestRunner
	 */
	private TestRunnerImplementation _runner;
	
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
		_runner = new TestRunnerImplementation(_fileExtension, _config);
	}
	
	/**
	 * Verifies that the datas has been initialized.
	 */
	@Test
	public void testTestRunner() {
		assertEquals(0, _runner.testSuiteCount());
		assertEquals(0, _runner.libraryCount());
		assertEquals(_fileExtension, _runner.getFileExtension());
		assertEquals(_config, _runner.getConfig());
		assertEquals(new String(), _runner.getDescription());
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
	 * Verifies that the correct number of libraries is returned.
	 */
	@Test
	public void testLibraryCount() {
		assertEquals(0, _runner.libraryCount());
	}
	
	/**
	 * Checks whether a library can be added to the list.
	 */
	@Test
	public void testAddLibrary() {
		Library library = mock(Library.class);
		_runner.addLibrary(library);
		assertEquals(1, _runner.libraryCount());
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
	
	/**
	 * Tests if the description is returned correctly.
	 */
	@Test
	public void testGetDescription() {
		assertEquals(new String(), _runner.getDescription());
	}
	
	/**
	 * Tests if the description can be set correctly.
	 */
	@Test
	public void testSetDescription() {
		String desc = "new_Test";
		_runner.setDescription(desc);
		assertEquals(desc, _runner.getDescription());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException occurring when null is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDescriptionWithNullAsParameter() {
		_runner.setDescription(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException occurs when an empty
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDescriptionWithEmptyStringAsParameter() {
		_runner.setDescription(new String());
	}
	
	/**
	 * Tests if the configuration is returned.
	 */
	@Test
	public void testGetConfig() {
		assertEquals(_config, _runner.getConfig());
	}
	
	/**
	 * Tests if the configuration can be set.
	 */
	@Test
	public void testSetConfig() {
		_config = mock(Config.class);
		_runner.setConfig(_config);
		assertEquals(_config, _runner.getConfig());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as a
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigWithNullAsParameter() {
		_runner.setConfig(null);
	}
	
	@Test
	public void testCreateHtmlListOfLibraries() {
		String name1 = "Test";
		String version1 = "1.1.1";
		String name2 = "testen";
		Library library1 = mock(Library.class);
		when(library1.getName()).thenReturn(name1);
		when(library1.getVersion()).thenReturn(version1);
		_runner.addLibrary(library1);
		
		Library library2 = mock(Library.class);
		when(library2.getName()).thenReturn(name2);
		when(library2.getVersion()).thenReturn(new String());
		_runner.addLibrary(library2);
		
		Library library3 = mock(Library.class);
		when(library3.getName()).thenReturn(new String());
		_runner.addLibrary(library3);
		
		String newLine = System.lineSeparator();
		
		String result = "\t\t<p>Verwendete Bibliotheken:</p>" + newLine +
				"\t\t<ul>" + newLine + "\t\t\t<li>" + name1 + " " + version1 +
				"</li>" + newLine + "\t\t\t<li>" + name2 + "</li>" + newLine +
				"\t\t</ul>" + newLine;

		assertEquals(result, _runner.createHtmlListOfLibraries());
		
		verify(library1).getName();
		verify(library1).getVersion();
		verify(library2).getName();
		verify(library2).getVersion();
		verify(library3).getName();
		verify(library3, never()).getVersion();
	}
	
	/**
	 * Test whether the list of non-existing tests is created correctly.
	 */
	@Test
	public void testCreateHtmlListOfNonExistsTests() {
		org.testsuite.data.Test test1 = mock(org.testsuite.data.Test.class);
		when(test1.isExists()).thenReturn(false);
		when(test1.getName()).thenReturn("Test1");
		
		org.testsuite.data.Test test2 = mock(org.testsuite.data.Test.class);
		when(test2.isExists()).thenReturn(true);
		
		org.testsuite.data.Test test3 = mock(org.testsuite.data.Test.class);
		when(test3.isExists()).thenReturn(true);
		
		org.testsuite.data.Test test4 = mock(org.testsuite.data.Test.class);
		when(test4.isExists()).thenReturn(false);
		when(test4.getName()).thenReturn("Test4");
		
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
		
		String ret = "\t\t<div class=\"nonexists\">" + System.lineSeparator() +
				"\t\t\t<p>Folgende Tests existieren nicht:</p>" + 
				System.lineSeparator() + "\t\t\t<ul>" + System.lineSeparator() +
				"\t\t\t\t<li>Test1</li>" + System.lineSeparator() +
				"\t\t\t\t<li>Test4</li>" + System.lineSeparator() +
				"\t\t\t</ul>" + System.lineSeparator() +
				"\t\t</div>" + System.lineSeparator();
		assertEquals(ret, _runner.createHtmlListOfNonExistsTests());
	}
	
	/**
	 * Tests if the description of the test is output correctly. As parameter
	 * false is passed. It is not drawn horizontal line.
	 */
	@Test
	public void testCreateHtmlHeadWithFalseAsParameter() {
		String head = "[h2]Test[/h2][p]Dies ist ein Test[/p]";
		_runner.setDescription(head);
		String ret = "\t\t<div class=\"testhead\"><h2>Test</h2><p>Dies ist " +
				"ein Test</p></div>" + System.lineSeparator();
		assertEquals(ret, _runner.createHtmlHead(false));
	}
	
	/**
	 * Tests if the description of the test is output correctly. As parameter
	 * true is passed. Plot a horizontal line.
	 */
	@Test
	public void testCreateHtmlHeadWithTrueAsParameter() {
		String head = "[h2]Test[/h2][p]Dies ist ein großer Test[/p]";
		_runner.setDescription(head);
		String ret = "\t\t<hr/>" + System.lineSeparator() +
				"\t\t<div class=\"testhead\"><h2>Test</h2><p>Dies ist ein " +
				"großer Test</p></div>" + System.lineSeparator();
		assertEquals(ret, _runner.createHtmlHead(true));
	}
	
	/**
	 * Tests if the HTML code is generated correctly.
	 */
	@Test
	public void testCreateHtml() {
		org.testsuite.data.Test test1 = mock(org.testsuite.data.Test.class);
		
		org.testsuite.data.Test test2 = mock(org.testsuite.data.Test.class);
		
		org.testsuite.data.Test test3 = mock(org.testsuite.data.Test.class);
		
		org.testsuite.data.Test test4 = mock(org.testsuite.data.Test.class);
		
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

		String ret = "\t\t<div class=\"testgroup\">" + System.lineSeparator() +
				"\t\t\t<div class=\"testsuite\">" + System.lineSeparator() +
				"\t\t\t\t<table>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t</table>" + System.lineSeparator() +
				"\t\t\t</div>" + System.lineSeparator() +
				"\t\t\t<div class=\"testsuite\">" + System.lineSeparator() +
				"\t\t\t\t<table>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				"\t\t\t\t\t</tr>" + System.lineSeparator() +
				"\t\t\t\t</table>" + System.lineSeparator() +
				"\t\t\t</div>" + System.lineSeparator() +
				"\t\t</div>" + System.lineSeparator();
		assertEquals(ret, _runner.createHtml());
	}

	/**
	 * Tests if the last test suite id is returned correctly.
	 * 
	 * @see org.testsuite.core.TestRunner#getLastSuiteId()
	 */
	@Test
	public void testGetLastSuiteId() {
		assertEquals(-1, _runner.getLastSuiteId());
	}
	
	/**
	 * Tests whether the last test suite id can be set correctly.
	 * 
	 * @see org.testsuite.core.TestRunner#setLastSuiteId(int)
	 */
	@Test
	public void testSetLastSuiteId() {
		int id = 100;
		_runner.setLastSuiteId(id);
		assertEquals(id, _runner.getLastSuiteId());
	}
	
	/**
	 * Tests if the library information is generated for the classpath properly. 
	 */
	@Test
	public void testCreateLibraryAsString()
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		Method createLibraryAsString = getProtectedMethod(TestRunner.class, 
				"createLibraryAsString", null);
		
		String pathLibrary = "path";
		String pathLib2 = "test";
		String libName1 = "lib1";
		String libName2 = "lib2";
		
		when(_config.getPathLibrary()).thenReturn(pathLibrary);
		
		Library lib1 = mock(Library.class);
		when(lib1.getFileName()).thenReturn(libName1);
		when(lib1.getPath()).thenReturn(new String());
		_runner.addLibrary(lib1);
		
		Library lib2 = mock(Library.class);
		when(lib2.getFileName()).thenReturn(libName2);
		when(lib2.getPath()).thenReturn(pathLib2);
		_runner.addLibrary(lib2);
		
		String ret = pathLibrary + File.separator + libName1 + 
				File.pathSeparator +
				pathLib2 + File.separator + libName2;
		assertEquals(ret, createLibraryAsString.invoke(_runner, null));
		
		verify(_config).getPathLibrary();
		
		verify(lib1).getPath();
		verify(lib1).getFileName();
		
		verify(lib2, times(2)).getPath();
		verify(lib2).getFileName();
	}
}
