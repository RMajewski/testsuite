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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Library;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;
import org.testsuite.data.TestSuite;

/**
 * Tests the class {@link org.testsuite.core.TestRunner}.
 * 
 * $java -Duser.language=de -Duser.country=DE
 * 
 * @author René Majewski
 * 
 * @version 0.2
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TestRunner.class})
public class TestTestRunner extends TestRunnerHelper {

	/**
	 * Hold an instance of the TestRunner
	 */
	private TestRunnerImplementation _runner;
	
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
		_runner = new TestRunnerImplementation(_config);
		_runCount = 0;
	}
	
	/**
	 * Verifies that the datas has been initialized.
	 */
	@Test
	public void testTestRunner() {
		assertEquals(0, _runner.testSuiteCount());
		assertEquals(0, _runner.libraryCount());
		assertEquals(new String(), _runner.getFileExtension());
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
	 * Verifies that the correct test suite is return.
	 */
	@Test
	public void testGetTestSuite() {
		TestSuite suite = mock(TestSuite.class);
		_runner.addTestSuite(suite);
		assertEquals(suite, _runner.getTestSuite(0));
	}
	
	/**
	 * Checks whether the new list of test suites has been stored correctly and
	 * whether they will also be given right back.
	 */
	@Test
	public void testSetTestSuiteListAndGetTestSuiteList() {
		List<TestSuite> list = new ArrayList<TestSuite>();
		_runner.setTestSuiteList(list);
		assertEquals(list, _runner.getTestSuiteList());
	}
	
	/**
	 * Verifies that remove the correct test suite.
	 */
	@Test
	public void testRemoveTestSuite() {
		TestSuite suite1 = new TestSuite();
		TestSuite suite2 = new TestSuite();
		TestSuite suite3 = new TestSuite();
		
		_runner.addTestSuite(suite1);
		_runner.addTestSuite(suite2);
		_runner.addTestSuite(suite3);
		
		assertEquals(3, _runner.testSuiteCount());
		
		_runner.removeTestSuite(suite2);
		
		assertEquals(2, _runner.testSuiteCount());
		assertEquals(suite1, _runner.getTestSuite(0));
		assertEquals(suite3, _runner.getTestSuite(1));
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
	 * Verifies that the correct library was deleted.
	 */
	@Test
	public void testRemoveLibrary() {
		Library lib1 = new Library();
		lib1.setFileName("Test1");
		_runner.addLibrary(lib1);
		
		Library lib2 = new Library();
		lib2.setFileName("Test2");
		_runner.addLibrary(lib2);
		
		Library lib3 = new Library();
		lib3.setFileName("Test3");
		_runner.addLibrary(lib3);
		
		assertEquals(3, _runner.libraryCount());
		
		_runner.removeLibrary(lib2);
		
		assertEquals(2, _runner.libraryCount());
		assertEquals(lib1, _runner.getLibrary(0));
		assertEquals(lib3, _runner.getLibrary(1));
	}
	
	/**
	 * Checks whether the new list of libraries has been stored correctly and
	 * whether they will also be given right back.
	 */
	@Test
	public void testSetLibraryListAndGetLibraryList() {
		List<Library> list = new ArrayList<Library>();
		
		_runner.setLibraryList(list);
		
		assertEquals(list, _runner.getLibraryList());
	}
	
	/**
	 * Verifies that the correct number of directories for classpath is
	 * returned.
	 */
	@Test
	public void testClasspathCount() {
		assertEquals(0, _runner.classPathCount());
	}
	

	/**
	 * Verifies that the correct name of class path is returned.
	 */
	@Test
	public void testGetClassPath() {
		String classpath = "Test";
		_runner.addClassPath(classpath);
		assertEquals(classpath, _runner.getClassPath(0));
	}

	/**
	 * Verifies that the correct name of class path is removed.
	 */
	@Test
	public void testRemoveClassPath() {
		String classpath1 = "Test1";
		String classpath2 = "Test2";
		String classpath3 = "Test3";
		
		_runner.addClassPath(classpath1);
		_runner.addClassPath(classpath2);
		_runner.addClassPath(classpath3);

		assertEquals(3, _runner.classPathCount());
		
		_runner.removeClassPath(classpath2);
		
		assertEquals(2, _runner.classPathCount());
		assertEquals(classpath1, _runner.getClassPath(0));
		assertEquals(classpath3, _runner.getClassPath(1));
	}
	
	/**
	 * Verifies that the new list has been stored correctly and whether the list
	 * is given right back.
	 */
	@Test
	public void testAddClassPathListAndGetClassPathList() {
		List<String> list = new ArrayList<String>();
		list.add("Test");
		
		_runner.setClassPathList(list);
		
		assertEquals(list, _runner.getClassPathList());
	}
	
	/**
	 * Checks whether a directory can be added to the list.
	 */
	@Test
	public void testAddClasspath() {
		_runner.addClassPath("test");
		assertEquals(1, _runner.classPathCount());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddClassPathWithNullAsParameter() {
		_runner.addClassPath(null);
		assertEquals(0, _runner.classPathCount());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddPropertyhWithEmptyStringAsParameter() {
		_runner.addClassPath(new String());
		assertEquals(0, _runner.classPathCount());
	}
	
	/**
	 * Tests if a non-existent directory is detected.
	 */
	@Test
	public void testCheckFileExistsWithNonExistsPath() throws Exception {
		String src = "src";
		String path = "tests.path";
		String testName = "test";
		String testPath = src + File.separator + 
				path.replaceAll("\\.", File.separator);
		
		when(_config.getPathSrc()).thenReturn(src);
		
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
		
		PowerMockito.verifyNew(File.class).withArguments(testPath);
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
		String src = "src";
		String path = "tests.path";
		String testName = "testen";
		String fileExtension = "test";
		String testPath = src + File.separator + 
				path.replaceAll("\\.", File.separator);
		
		when(_config.getPathSrc()).thenReturn(src);
		
		_runner.setFileExtension(fileExtension);
		
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
		
		PowerMockito.verifyNew(File.class).withArguments(testPath);
		PowerMockito.verifyNew(File.class).withArguments(testPath + 
				File.separator + testName + "." + fileExtension);
		verify(file, times(2)).exists();
		verify(test, times(1)).getName();
		verify(test, times(1)).setExists(false);
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite, atLeastOnce()).testCount();
		verify(suite).setExists(true);
	}
	
	/**
	 * Tests are marked if file and directory as exists.
	 */
	@Test
	public void testCheckFileExists() throws Exception{
		String src = "src";
		String path = "tests.path";
		String testName = "testen";
		String fileExtension = "test";
		String testPath = src + File.separator + 
				path.replaceAll("\\.", File.separator);
		
		when(_config.getPathSrc()).thenReturn(src);
		
		_runner.setFileExtension(fileExtension);
		
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
		
		PowerMockito.verifyNew(File.class).withArguments(testPath);
		PowerMockito.verifyNew(File.class).withArguments(testPath + 
				File.separator + testName + "." + fileExtension);
		verify(file, times(2)).exists();
		verify(test).getName();
		verify(test).setExists(true);
		verify(suite, atLeastOnce()).getTest(0);
		verify(suite).getPackage();
		verify(suite, atLeastOnce()).testCount();
		verify(suite).setExists(true);
	}
	
	/**
	 * Tests if the file extension is returned correctly.
	 */
	@Test
	public void testGetFileExtension() {
		assertEquals(new String(), _runner.getFileExtension());
	}
	
	/**
	 * Tests if the file extension can be set correctly.
	 */
	@Test
	public void testSetFileExtension() {
		String fileExtension = "new_Test";
		_runner.setFileExtension(fileExtension);
		assertEquals(fileExtension, _runner.getFileExtension());
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
		
		String result = "\t\t\t<div class=\"libraries\">" + newLine + 
				"\t\t\t\t<p>Verwendete Bibliotheken:</p>" + newLine +
				"\t\t\t\t<ul>" + newLine + "\t\t\t\t\t<li>" + name1 + " " + 
				version1 + "</li>" + newLine + "\t\t\t\t\t<li>" + name2 + 
				"</li>" + newLine + "\t\t\t\t</ul>" + newLine + "\t\t\t</div>" +
				newLine;

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
		
		String ret = "\t\t\t<div class=\"nonexists\">" + System.lineSeparator() +
				"\t\t\t\t<p>Folgende Tests existieren nicht:</p>" + 
				System.lineSeparator() + "\t\t\t\t<ul>" + System.lineSeparator() +
				"\t\t\t\t\t<li>Test1</li>" + System.lineSeparator() +
				"\t\t\t\t\t<li>Test4</li>" + System.lineSeparator() +
				"\t\t\t\t</ul>" + System.lineSeparator() +
				"\t\t\t</div>" + System.lineSeparator();
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
		String ret = "\t\t\t<div class=\"testdescription\"><h2>Test</h2><p>" +
				"Dies ist ein Test</p></div>" + System.lineSeparator();
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
		String ret = "\t\t\t<hr/>" + System.lineSeparator() +
				"\t\t\t<div class=\"testdescription\"><h2>Test</h2><p>Dies " +
				"ist ein großer Test</p></div>" + System.lineSeparator();
		assertEquals(ret, _runner.createHtmlHead(true));
	}
	
	/**
	 * Tests if the HTML code is generated correctly.
	 * @throws IOException 
	 */
	@Test
	public void testCreateHtml() throws IOException {
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
		
		HtmlOut html = mock(HtmlOut.class);

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
		assertEquals(ret, _runner.createHtml(html, false));
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
	
	/**
	 * Tests if the generated classpath properly. 
	 */
	@Test
	public void testCreateClasspath()
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method createClasspath = getProtectedMethod(TestRunner.class, 
		"createClasspath", null);
		
		String path1 = "path1";
		_runner.addClassPath(path1);
		
		String path2 = "path2";
		_runner.addClassPath(path2);
		
		String path3 = "path3";
		_runner.addClassPath(path3);
		
		String pathLib1 = "lib1";
		String name1 = "test1.jar";
		String pathLib2 = "lib2";
		String name2 = "test2.jar";
		
		when(_config.getPathLibrary()).thenReturn(pathLib1);
		
		Library lib1 = mock(Library.class);
		when(lib1.getFileName()).thenReturn(name1);
		when(lib1.getPath()).thenReturn(new String());
		_runner.addLibrary(lib1);
		
		Library lib2 = mock(Library.class);
		when(lib2.getFileName()).thenReturn(name2);
		when(lib2.getPath()).thenReturn(pathLib2);
		_runner.addLibrary(lib2);
		
		String ret = path1 + File.pathSeparator + path2 + File.pathSeparator +
				path3 + File.pathSeparator + pathLib1 + File.separator + name1 +
				File.pathSeparator + pathLib2 + File.separator + name2 + " ";
		
		assertEquals(ret, createClasspath.invoke(_runner, null));
		
		verify(_config, times(2)).getPathLibrary();
		
		verify(lib1).getFileName();
		verify(lib1).getPath();
		
		verify(lib2).getFileName();
		verify(lib2, times(2)).getPath();
	}
	
	/**
	 * Tests if the system settings are generated correctly.
	 */
	@Test
	public void testCreateProperty()
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method createProperty = getProtectedMethod(TestRunner.class, 
		"createProperty", null);
		
		String prop1 = "test1=\"1\"";
		when(_config.getProperty(0)).thenReturn(prop1);
		
		String prop2 = "test2=\"true\"";
		when(_config.getProperty(1)).thenReturn(prop2);
		
		String prop3 = "test3=\"false\"";
		when(_config.getProperty(2)).thenReturn(prop3);
		when(_config.propertyCount()).thenReturn(3);
		
		String ret = "-D" + prop1 + " -D" + prop2 + " -D" + prop3 + " ";

		assertEquals(ret, createProperty.invoke(_runner, null));
		
		verify(_config, times(5)).propertyCount();
		verify(_config).getProperty(0);
		verify(_config).getProperty(1);
		verify(_config).getProperty(2);
	}
	
	@Test
	public void testInputStreamToString() throws Exception{
		Method inputStreamToString = getProtectedMethod(TestRunner.class, 
				"inputStreamToString", InputStream.class);
		inputStreamToString.setAccessible(true);
		
		String line = "<h1>Test</h1><p>Dies ist ein Test!</p>";
		
		InputStream is = mock(InputStream.class);
		
		InputStreamReader isr = mock(InputStreamReader.class);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withArguments(is)
			.thenReturn(isr);
		
		BufferedReader bf = mock(BufferedReader.class);
		when(bf.readLine()).thenReturn(line, null);
		when(bf.ready()).thenReturn(true);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isr)
			.thenReturn(bf);
		
		String ret = "&lt;h1&gt;Test&lt;/h1&gt;&lt;p&gt;Dies ist ein " +
				"Test!&lt;/p&gt;<br/>";
		assertEquals(ret, inputStreamToString.invoke(_runner, is));
	}
	
	/**
	 * Testing whether the number of all included tests was determined
	 * correctly.
	 */
	@Test
	public void testGetTestsCount() {
		int testCount1 = 10;
		int testCount2 = 5;
		int testCount3 = 2;
		int testCount4 = 2;
		int testCount5 = 0;
		
		TestSuite suite1 = mock(TestSuite.class);
		when(suite1.testCount()).thenReturn(testCount1);
		_runner.addTestSuite(suite1);
		
		TestSuite suite2 = mock(TestSuite.class);
		when(suite2.testCount()).thenReturn(testCount2);
		_runner.addTestSuite(suite2);
		
		TestSuite suite3 = mock(TestSuite.class);
		when(suite3.testCount()).thenReturn(testCount3);
		_runner.addTestSuite(suite3);
		
		TestSuite suite4 = mock(TestSuite.class);
		when(suite4.testCount()).thenReturn(testCount4);
		_runner.addTestSuite(suite4);
		
		TestSuite suite5 = mock(TestSuite.class);
		when(suite5.testCount()).thenReturn(testCount5);
		_runner.addTestSuite(suite5);
		
		assertEquals(testCount1 + testCount2 + testCount3 + testCount4 + 
				testCount5, _runner.getTestsCount());
		
		InOrder order = inOrder(suite1, suite2, suite3, suite4, suite5);
		order.verify(suite1).testCount();
		order.verify(suite2).testCount();
		order.verify(suite3).testCount();
		order.verify(suite4).testCount();
		order.verify(suite5).testCount();
	}
	
	/**
	 * Checks whether a test listener could be added to the list.
	 */
	@Test
	public void testAddTestListener() throws Exception {
		assertEquals(0, _runner.getTestEventListenerCount());
		
		TestEventListener listener = mock(TestEventListener.class);
		
		_runner.addTestEventListener(listener);
		assertEquals(1, _runner.getTestEventListenerCount());
	}
	
	/**
	 * Verifies that the correct test listener could be deleted from the list.
	 */
	@Test
	public void testRemoveTestListener() throws Exception {
		TestEventListener listener1 = mock(TestEventListener.class);
		_runner.addTestEventListener(listener1);
		TestEventListener listener2 = mock(TestEventListener.class);
		_runner.addTestEventListener(listener2);
		
		assertEquals(2, _runner.getTestEventListenerCount());
		
		_runner.removeTestEventListener(listener1);
		assertEquals(1, _runner.getTestEventListenerCount());
	}
	
	/**
	 * Checks whether the TestExecuteCompleted event has been sent to all
	 * listeners.
	 */
	@Test
	public void testFireTestExecutedCompleted() {
		TestEventListener listener1 = mock(TestEventListener.class);
		_runner.addTestEventListener(listener1);
		TestEventListener listener2 = mock(TestEventListener.class);
		_runner.addTestEventListener(listener2);
		
		String pName = "package";
		String tName = "Test";
		int suiteId = 1;
		int testId = 19;
		String result = "ausgeführt";
		
		_runner.fireTestExecutedCompleted(this, pName, tName, suiteId, testId,
				result);
		
		verify(listener1).testExecutedCompleted(Matchers.any(TestEvent.class));
		verify(listener2).testExecutedCompleted(Matchers.any(TestEvent.class));
	}
	
	/**
	 * Checks whether the new list of EventListenerList has been saved and
	 * whether it is correctly returned back.
	 */
	@Test
	public void testSetTestEventListenersListAndGetTestEventListenersList() {
		Vector<TestEventListener> vector = new Vector<TestEventListener>();
		_runner.setTestEventListenerList(vector);
		assertEquals(vector, _runner.getTestEventListenerList());
	}
	/**
	 * Tests if the will be canceled if the test file does not exist.
	 */
	@Test
	public void testRunWithNonExistingTestFile() {
		String packageName = "package";
		String testName = "test";

		Junit junit = mock(Junit.class);
		when(junit.isExists()).thenReturn(false);
		when(junit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		
		_runner.addTestSuite(suite);
		
		_runner.run(suite, junit);
		
		verify(junit).isExists();
		verify(junit).setExitStatus(100);
		verify(junit, never()).setExists(Matchers.anyBoolean());
		verify(junit, times(2)).getName();
		
		verify(suite, times(2)).getPackage();
		verify(suite).isExists();
	}
	
	/**
	 * Tests if the will be canceled when the directory of the test file does
	 * not exist.
	 */
	@Test
	public void testRunWithNonExistingPath() {
		String pathSrc = "src";
		String packageName = "package";
		String testName = "test";
		
		when(_config.getPathSrc()).thenReturn(pathSrc);

		Junit junit = mock(Junit.class);
		when(junit.isExists()).thenReturn(true);
		when(junit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(false);
		_runner.addTestSuite(suite);
		
		_runner.run(suite, junit);
		
		verify(_config, never()).getPathSrc();
		verify(_config, never()).getPathResult();
		
		verify(junit, never()).isExists();
		verify(junit).setExitStatus(100);
		verify(junit, never()).setExists(Matchers.anyBoolean());
		verify(junit, times(2)).getName();
		
		verify(suite, times(2)).getPackage();
		verify(suite).isExists();
	}
	
	/**
	 * Tests if the will be canceled if the test file does not exist.
	 */
	@Test
	public void testRunWithNoneExecuteTest() {
		String packageName = "package";
		String testName = "test";

		Junit junit = mock(Junit.class);
		when(junit.isExists()).thenReturn(true);
		when(junit.isExecuted()).thenReturn(false);
		when(junit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(junit);
		when(suite.getPackage()).thenReturn(packageName);
		when(suite.isExists()).thenReturn(true);
		_runner.addTestSuite(suite);
		
		_runner.run(suite, junit);
		
		verify(junit).isExists();
		verify(junit).isExecuted();
		verify(junit, never()).setExitStatus(100);
		verify(junit, never()).setExists(Matchers.anyBoolean());
		verify(junit, times(2)).getName();
		
		verify(suite, times(2)).getPackage();
		verify(suite).isExists();
	}

	/**
	 * Tests if the test is performed correctly.
	 */
	@Test
	public void testRun() throws Exception {
		String packageName = "package";
		String testName = "test";
		String exec = packageName + "." + testName;
		
		Library library = mock(Library.class);
		
		InputStream isConsole = mock(InputStream.class);
		InputStream isError = mock(InputStream.class);

		Junit junit = mock(Junit.class);
		when(junit.isExists()).thenReturn(true);
		when(junit.isExecuted()).thenReturn(true);
		when(junit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(junit);
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
		when(console.ready()).thenReturn(true);
		when(console.readLine())
			.thenReturn("OK (2 tests)", null);
		
		BufferedReader error = mock(BufferedReader.class);
		when(error.readLine()).thenReturn(null);
		
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
		
		_runner.run(suite, junit);
		
		assertEquals(1, _runCount);
		
		verify(runtime).exec(exec);
		
		verify(process).waitFor();
		verify(process).getInputStream();
		verify(process).getErrorStream();
		
		verify(junit).isExists();
		verify(junit).isExecuted();
		verify(junit).setExitStatus(0);
		verify(junit, never()).setExists(Matchers.anyBoolean());
		verify(junit, atLeastOnce()).getName();
		verify(junit).setStart(Matchers.anyLong());
		verify(junit).setEnd(Matchers.anyLong());
		verify(junit).getDurationTime();
		verify(junit).setStringConsole(Matchers.anyString());
		
		verify(suite, atLeastOnce()).getPackage();
		verify(suite).isExists();
	}

	/**
	 * Tests if the test has a failure.
	 */
	@Test
	public void testRunWithFailure() throws Exception {
		String packageName = "package";
		String testName = "test";

		InputStream isConsole = mock(InputStream.class);
		InputStream isError = mock(InputStream.class);

		Junit junit = mock(Junit.class);
		when(junit.isExists()).thenReturn(true);
		when(junit.isExecuted()).thenReturn(true);
		when(junit.getName()).thenReturn(testName);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getTest(0)).thenReturn(junit);
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
		when(console.ready()).thenReturn(true);
		when(console.readLine())
			.thenReturn("Tests run: 1,  Failures: 2", null);
		
		BufferedReader error = mock(BufferedReader.class);
		when(error.readLine()).thenReturn(null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrConsole)
			.thenReturn(console);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isrError)
			.thenReturn(error);
		
		_runner.run(suite, junit);
		
		String exec = packageName + "." + testName;
		verify(runtime).exec(exec);
		
		verify(process).waitFor();
		verify(process).getInputStream();
		verify(process).getErrorStream();
		
		verify(junit).isExists();
		verify(junit).isExecuted();
		verify(junit).setExitStatus(0);
		verify(junit, never()).setExists(Matchers.anyBoolean());
		verify(junit, atLeastOnce()).getName();
		verify(junit).setStart(Matchers.anyLong());
		verify(junit).setEnd(Matchers.anyLong());
		verify(junit).getDurationTime();
		verify(junit).setStringConsole(Matchers.anyString());
		
		verify(suite, atLeastOnce()).getPackage();
		verify(suite).isExists();
	}
}
