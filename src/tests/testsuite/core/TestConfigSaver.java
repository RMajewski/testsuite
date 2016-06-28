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

import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.ConfigSaver;
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.JemmyTestRunner;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;
import org.testsuite.core.TestRunner;

/**
 * Tests the class {@link org.testsuite.core.ConfigSaver}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigSaver.class})
public class TestConfigSaver {
	/**
	 * Saves the mock object of BufferedWriter
	 */
	private BufferedWriter _bw;
	
	/**
	 * Saves the mock object of File
	 */
	private File _file;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_file = mock(File.class);
		
		FileWriter writer = mock(FileWriter.class);
		
		PowerMockito.whenNew(FileWriter.class)
			.withArguments(_file)
			.thenReturn(writer);
		
		_bw = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(writer).thenReturn(_bw);
	}
	
	/**
	 * Tests if the configuration file is created
	 * 
	 * @deprecated
	 */
	@Test
	public void testSaveOld() throws Exception {
		File file = mock(File.class);
		
		FileWriter writer = mock(FileWriter.class);
		
		PowerMockito.whenNew(FileWriter.class)
			.withArguments(file)
			.thenReturn(writer);
		
		BufferedWriter bw = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(writer).thenReturn(bw);
		
		JunitTestRunner runner1 = new JunitTestRunner();
		JemmyTestRunner runner2 = new JemmyTestRunner();
		FitTestRunner runner3 = new FitTestRunner();
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		list.add(runner1);
		list.add(runner2);
		list.add(runner3);
		
		Config config = Config.getInstance();
		
		ConfigSaver.save(config, list, file);
		verify(bw).write("<?xml version=\"1.0\"?>");
		verify(bw).write("<configurationFile");
		verify(bw).write(" xmlns=\"https://github.com/RMajewski/testsuite\"");
		verify(bw).write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		verify(bw).write(" xsi:schemaLocation=\"https://github.com/RMajewski/");
		verify(bw).write("testsuite https://raw.githubusercontent.com/RMajewski/");
		verify(bw).write("testsuite/master/src/resources/xml/config.xsd\"");
	}

	/**
	 * Tests if the configuration file is created
	 */
	@Test
	public void testSave() throws Exception {
		JunitTestRunner runner1 = new JunitTestRunner();
		JemmyTestRunner runner2 = new JemmyTestRunner();
		FitTestRunner runner3 = new FitTestRunner();
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		list.add(runner1);
		list.add(runner2);
		list.add(runner3);
		
		ConfigSaver.save(list, _file);
		verify(_bw).write("<?xml version=\"1.0\"?>");
		verify(_bw).write("<configurationFile");
		verify(_bw).write(" xmlns=\"https://github.com/RMajewski/testsuite\"");
		verify(_bw).write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		verify(_bw).write(" xsi:schemaLocation=\"https://github.com/RMajewski/");
		verify(_bw).write("testsuite https://raw.githubusercontent.com/RMajewski/");
		verify(_bw).write("testsuite/master/src/resources/xml/config.xsd\"");
	}

	/**
	 * Tests if writes the general configuration to the configuration file.
	 */
	@Test
	public void testWriteGeneralConfiguration() throws IOException {
		String pathResult = "result";
		String pathSrc = "src";
		String pathLib = "lib";
		long maxDuration = 30000;
		boolean htmlOut = true;
		String prop1 = "test=true";
		String prop2 = "debug=false";
		String class1 = "bin";
		String class2 = "resources";
		
		Config config = Config.getInstance();
		config.setPathResult(pathResult);
		config.setPathSrc(pathSrc);
		config.setPathLibrary(pathLib);
		config.setMaxDuration(maxDuration);
		config.setCreateHtml(htmlOut);
		config.addProperty(prop1);
		config.addProperty(prop2);
		config.addClassPath(class1);
		config.addClassPath(class2);
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		
		ConfigSaver.save(list, _file);
		
		verify(_bw).write("\t<config>");
		
		verify(_bw).write("\t\t<resultPath>");
		verify(_bw).write(pathResult);
		verify(_bw).write("</resultPath>");
		
		verify(_bw).write("\t\t<srcPath>");
		verify(_bw).write(pathSrc);
		verify(_bw).write("</srcPath>");
		
		verify(_bw).write("\t\t<libPath>");
		verify(_bw).write(pathLib);
		verify(_bw).write("</libPath>");
		
		verify(_bw).write("\t\t<maxDuration>");
		verify(_bw).write(String.valueOf(maxDuration));
		verify(_bw).write("</maxDuration>");
		
		verify(_bw).write("\t\t<htmlOut>");
		verify(_bw).write(String.valueOf(htmlOut));
		verify(_bw).write("</htmlOut>");
		
		verify(_bw).write("\t\t<systemProperty>");
		verify(_bw, times(2)).write("\t\t\t<property>");
		verify(_bw, times(2)).write("</property>");
		verify(_bw).write(prop1);
		verify(_bw).write(prop2);
		verify(_bw).write("\t\t</systemProperty>");
		
		verify(_bw).write("\t\t<classpath>");
		verify(_bw, times(2)).write("\t\t\t<path>");
		verify(_bw, times(2)).write("</path>");
		verify(_bw).write(class1);
		verify(_bw).write(class2);
		verify(_bw).write("\t\t</classpath>");
		
		verify(_bw).write("\t</config>");
	}
	
	/**
	 * Tests if writes the test runner to the configuration file.
	 */
	@Test
	public void testWriteTestRunner() throws IOException {
		String description = "This is a test";
		String extension = "java";
		String libFile = "testsuite.0.5.0+1.jar";
		String libName = "testsuite";
		String libVersion = "0.5.0";
		String libPath = "lib/testsuite";
		String class1 = "bin";
		String class2 = "resources";
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		
		Library lib = mock(Library.class);
		when(lib.getFileName()).thenReturn(libFile);
		when(lib.getName()).thenReturn(libName);
		when(lib.getVersion()).thenReturn(libVersion);
		when(lib.getPath()).thenReturn(libPath);
		
		JunitTestRunner runner = mock(JunitTestRunner.class);
		when(runner.getDescription()).thenReturn(description);
		when(runner.getFileExtension()).thenReturn(extension);
		when(runner.libraryCount()).thenReturn(1);
		when(runner.getLibrary(0)).thenReturn(lib);
		when(runner.classPathCount()).thenReturn(2);
		when(runner.getClassPath(0)).thenReturn(class1);
		when(runner.getClassPath(1)).thenReturn(class2);
		list.add(runner);
		
		ConfigSaver.save(list, _file);
		
		verify(_bw).write("\t<testGroup>");
		
		verify(_bw).write("\t\t<testRunner>");
		verify(_bw).write(runner.getClass().getName());
		verify(_bw).write("</testRunner>");
		
		verify(_bw).write("\t\t<description>");
		verify(_bw).write(description);
		verify(_bw).write("</description>");
		
		verify(_bw).write("\t\t<extension>");
		verify(_bw).write(extension);
		verify(_bw).write("</extension>");
		
		verify(_bw).write("\t\t<libraries>");
		verify(_bw).write("\t\t\t<library");
		verify(_bw).write(" version=\"");
		verify(_bw).write(libVersion); 
		verify(_bw, times(3)).write("\"");
		verify(_bw).write(" name=\"");
		verify(_bw).write(libName);
		verify(_bw).write(" path=\"");
		verify(_bw).write(libPath);
		verify(_bw, times(2)).write(">");
		verify(_bw).write(libFile);
		verify(_bw).write("</library>");
		verify(_bw).write("\t\t</libraries>");
		
		verify(_bw).write("\t\t<classpath>");
		verify(_bw, times(2)).write("\t\t\t<path>");
		verify(_bw, times(2)).write("</path>");
		verify(_bw).write(class1);
		verify(_bw).write(class2);
		verify(_bw).write("\t\t</classpath>");

		verify(_bw).write("\t</testGroup>");
	}
	
	/**
	 * Tests if writes the test suite to the configuration file.
	 */
	@Test
	public void testWriteTestSuite() throws IOException {
		String name = "This is a test";
		String packName = "tests.test";
		String testName1 = "Test1";
		String testName2 = "Test2";
		String testName3 = "Test3";
		String checkSource = "src.core.Test3";
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		
		Junit test1 = mock(Junit.class);
		when(test1.isExecuted()).thenReturn(false);
		when(test1.isJvm()).thenReturn(true);
		when(test1.isCheckSource()).thenReturn(false);
		when(test1.getName()).thenReturn(testName1);
		
		Junit test2 = mock(Junit.class);
		when(test2.isExecuted()).thenReturn(true);
		when(test2.isJvm()).thenReturn(false);
		when(test2.isCheckSource()).thenReturn(false);
		when(test2.getName()).thenReturn(testName2);
		
		Junit test3 = mock(Junit.class);
		when(test3.isExecuted()).thenReturn(true);
		when(test3.isJvm()).thenReturn(true);
		when(test3.isCheckSource()).thenReturn(true);
		when(test3.getName()).thenReturn(testName3);
		when(test3.getCheckSource()).thenReturn(checkSource);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.getName()).thenReturn(name);
		when(suite.getPackage()).thenReturn(packName);
		when(suite.testCount()).thenReturn(3);
		when(suite.getTest(0)).thenReturn(test1);
		when(suite.getTest(1)).thenReturn(test2);
		when(suite.getTest(2)).thenReturn(test3);
		
		JunitTestRunner runner = mock(JunitTestRunner.class);
		when(runner.getDescription()).thenReturn(new String());
		when(runner.testSuiteCount()).thenReturn(1);
		when(runner.getTestSuite(0)).thenReturn(suite);
		list.add(runner);
		
		ConfigSaver.save(list, _file);
		
		verify(_bw).write("\t\t<testSuite>");
		
		verify(_bw).write("\t\t\t<name>");
		verify(_bw).write(name);
		verify(_bw).write("</name>");
		
		verify(_bw).write("\t\t\t<package>");
		verify(_bw).write(packName);
		verify(_bw).write("</package>");
		
		verify(_bw, times(3)).write("\t\t\t<test");
		verify(_bw).write(" execute=\"false\"");
		verify(_bw, times(4)).write(">");
		verify(_bw).write(testName1);
		verify(_bw, times(3)).write("</test>");
		
		verify(_bw).write(" jvm=\"false\"");
		verify(_bw).write(testName2);
		
		verify(_bw).write(" checkSource=\"");
		verify(_bw).write(checkSource);
		verify(_bw).write("\"");
		verify(_bw).write(testName2);
		
		verify(_bw).write("\t\t</testSuite>");
	}
}
