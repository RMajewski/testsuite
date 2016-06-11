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

package tests.testsuite.checksource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.SourceFile;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.tests.SourceTest;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests the class {@link org.testsuite.checksource.SourceFile}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SourceFile.class})
public class TestSourceFile {
	/**
	 * Saves the instance of SourceFile
	 */
	private SourceFile _sf;
	
	/**
	 * Initialize the test.
	 */
	@Before
	public void setUp() throws Exception {
		_sf = new SourceFile();
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testSourceFile() {
		assertNull(_sf.getFileName());
		assertEquals(0, _sf.methodsCount());
		assertEquals(0, _sf.sourceCount());
	}

	/**
	 * Tests if was the file name returned correctly.
	 */
	@Test
	public void testGetFileName() {
		assertNull(_sf.getFileName());
	}

	/**
	 * Tests if was the file name set correctly.
	 */
	@Test
	public void testSetFileName() {
		String name = "Test.java";
		_sf.setFileName(name);
		assertEquals(name, _sf.getFileName());
	}

	/**
	 * Tests if was the number of lines of source code returned correctly.
	 */
	@Test
	public void testSourceCount() {
		assertEquals(0, _sf.sourceCount());
	}

	/**
	 * Tests if was the line of source code added to the list of source code
	 * correctly.
	 */
	@Test
	public void testAddSourceLine() {
		String line = "Dies ist ein Test";
		assertEquals(0, _sf.sourceCount());
		_sf.addSourceLine(line);
		assertEquals(1, _sf.sourceCount());
		assertEquals(line, _sf.getSourceLineString(0));
	}

	/**
	 * Tests if was the line of source code returned from the list of source
	 * code correctly.
	 */
	@Test
	public void testGetSourceLineString() {
		String line = "Dies ist ein Test";
		_sf.addSourceLine(line);
		assertEquals(line, _sf.getSourceLineString(0));
	}

	/**
	 * Tests if was the line of source code returned from the list of source
	 * code correctly.
	 */
	@Test
	public void testGetSourceLine() {
		String line = "Dies ist ein Test";
		_sf.addSourceLine(line);
		SourceLine source = new SourceLine();
		source.setLine(line);
		assertEquals(source.getLine(), _sf.getSourceLine(0).getLine());
		assertEquals(source.isMultiLineComment(), 
				_sf.getSourceLine(0).isMultiLineComment());
		assertEquals(source.getLineNumber(), 
				_sf.getSourceLine(0).getLineNumber());
	}

	/**
	 * Tests if was the number of declarations returned correctly.
	 */
	@Test
	public void testDeclarationsCount() {
		assertEquals(0, _sf.methodsCount());
	}

	/**
	 * Tests if was the declaration added to the list of declarations correctly.
	 */
	@Test
	public void testAddDeclaration() {
		CSMethod method = new CSMethod();
		assertEquals(0, _sf.methodsCount());
		_sf.addMethod(method);
		assertEquals(1, _sf.methodsCount());
		assertEquals(method, _sf.getMethod(0));
	}

	/**
	 * Tests if was the declaration returned from the list of declarations
	 * correctly.
	 */
	@Test
	public void testGetDeclaration() {
		CSMethod method = new CSMethod();
		_sf.addMethod(method);
		assertEquals(method, _sf.getMethod(0));
	}

	/**
	 * Tests if was the source file read correctly.
	 */
	@Test
	public void testReadFile() throws Exception {
		String fileName = "Test.java";
		_sf.setFileName(fileName);
		
		String line1 = "/*";
		String line2 = "* This is a test.";
		String line3 = "*/";
		String line4 = "public class TestClass {";
		String line5 = "  protected final static int TEST = 99;";
		String line6 = "  private int _test;";
		String line7 = "  public void test(int index) {";
		String line8 = "    _test = TEST;";
		String line9 = "  }";
		String line10 = "}";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName)
			.thenReturn(file);
		
		FileReader fr = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withArguments(fileName)
			.thenReturn(fr);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn(line1, line2, line3, line4, line5, line6,
				line7, line8, line9, line10, null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr)
			.thenReturn(br);
		
		assertTrue(_sf.readFile(false, null));
		
		assertEquals(1, _sf.getSourceLine(0).getLineNumber());
		assertEquals(line1, _sf.getSourceLine(0).getLine());
		assertTrue(_sf.getSourceLine(0).isMultiLineComment());
		
		assertEquals(2, _sf.getSourceLine(1).getLineNumber());
		assertEquals(line2, _sf.getSourceLine(1).getLine());
		assertTrue(_sf.getSourceLine(1).isMultiLineComment());

		assertEquals(3, _sf.getSourceLine(2).getLineNumber());
		assertEquals(line3, _sf.getSourceLine(2).getLine());
		assertTrue(_sf.getSourceLine(2).isMultiLineComment());

		assertEquals(4, _sf.getSourceLine(3).getLineNumber());
		assertEquals(line4, _sf.getSourceLine(3).getLine());
		assertFalse(_sf.getSourceLine(3).isMultiLineComment());

		assertEquals(5, _sf.getSourceLine(4).getLineNumber());
		assertEquals(line5, _sf.getSourceLine(4).getLine());
		assertFalse(_sf.getSourceLine(4).isMultiLineComment());

		assertEquals(6, _sf.getSourceLine(5).getLineNumber());
		assertEquals(line6, _sf.getSourceLine(5).getLine());
		assertFalse(_sf.getSourceLine(5).isMultiLineComment());

		assertEquals(7, _sf.getSourceLine(6).getLineNumber());
		assertEquals(line7, _sf.getSourceLine(6).getLine());
		assertFalse(_sf.getSourceLine(6).isMultiLineComment());

		assertEquals(8, _sf.getSourceLine(7).getLineNumber());
		assertEquals(line8, _sf.getSourceLine(7).getLine());
		assertFalse(_sf.getSourceLine(7).isMultiLineComment());

		assertEquals(9, _sf.getSourceLine(8).getLineNumber());
		assertEquals(line9, _sf.getSourceLine(8).getLine());
		assertFalse(_sf.getSourceLine(8).isMultiLineComment());

		assertEquals(10, _sf.getSourceLine(9).getLineNumber());
		assertEquals(line10, _sf.getSourceLine(9).getLine());
		assertFalse(_sf.getSourceLine(9).isMultiLineComment());
		
		assertEquals(1, _sf.methodsCount());
		
		assertEquals(7, _sf.getMethod(0).getLine());
		assertEquals("public", _sf.getMethod(0).getModifier());
		assertEquals(9, _sf.getMethod(0).getLastLineNumber());
		assertEquals("void", _sf.getMethod(0).getType());
		assertEquals(new String(), _sf.getMethod(0).getValue());
		assertEquals("test", _sf.getMethod(0).getName());
		assertEquals("TestClass", _sf.getMethod(0).getClassName());
		
		assertEquals(1, _sf.getMethod(0).parametersCount());
		assertEquals("int", _sf.getMethod(0).getParameter(0).getType());
		assertEquals("index", _sf.getMethod(0).getParameter(0).getName());

		assertEquals(0, _sf.getMethod(0).callsCount());
	}
	
	/**
	 * Tests if was the return false, if the file name does not set.
	 */
	@Test
	public void testReadFileNameNotSet() throws FileNotFoundException {
		assertFalse(_sf.readFile(false, null));
	}
	
	/**
	 * Tests if was the return false, if the file name is empty string.
	 */
	@Test
	public void testReadFileNameEmptyString() throws FileNotFoundException {
		_sf.setFileName(new String());
		assertFalse(_sf.readFile(false, null));
	}
	
	/**
	 * Tests if was the return false if the file does not exists.
	 */
	@Test
	public void testReadFileNotExists() throws Exception {
		String fileName = "Test.java";
		_sf.setFileName(fileName);
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName)
			.thenReturn(file);
		
		assertFalse(_sf.readFile(false, null));
	}

	/**
	 * Tests if was the source file read correctly.
	 */
	@Test
	public void testReadTestFile() throws Exception {
		String fileName = "Test.java";
		String testName = "TestTest.java";
		_sf.setFileName(fileName);
		
		String line1 = "/*";
		String line2 = "* This is a test.";
		String line3 = "*/";
		String line4 = "public class TestClass {";
		String line5 = "  protected final static int TEST = 99;";
		String line6 = "  private int _test;";
		String line7 = "  public void test(int index) {";
		String line8 = "    _test = TEST;";
		String line9 = "  }";
		String line10 = "}";
		
		String test1 = "public class Test {";
		String test2 = "  @Test";
		String test3 = "  public void test() {";
		String test4 = "    TestClass test = new TestClass();";
		String test5 = "    test.test(10);";
		String test6 = "  }";
		String test7 = "}";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName)
			.thenReturn(file);
		
		PowerMockito.whenNew(File.class)
			.withArguments(testName)
			.thenReturn(file);
		
		FileReader fr = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withArguments(fileName)
			.thenReturn(fr);
		
		FileReader fr2 = mock(FileReader.class);
		PowerMockito.whenNew(FileReader.class)
			.withArguments(testName)
			.thenReturn(fr2);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn(line1, line2, line3, line4, line5, line6,
				line7, line8, line9, line10, null);
		
		BufferedReader br2 = mock(BufferedReader.class);
		when(br2.readLine()).thenReturn(test1, test2, test3, test4, test5, test6,
				test7, null);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr)
			.thenReturn(br);
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(fr2)
			.thenReturn(br2);
		
		assertTrue(_sf.readFile(false, null));
		assertTrue(_sf.readFile(true, testName));
		
		assertEquals(1, _sf.getMethod(0).callsCount());
		assertEquals(5, _sf.getMethod(0).getCall(0));
	}
	
	/**
	 * Tests if was the return false, if the file name does not set.
	 */
	@Test
	public void testReadTestFileNameNotSet() throws FileNotFoundException {
		assertFalse(_sf.readFile(true, null));
	}
	
	/**
	 * Tests if was the return false, if the file name is empty string.
	 */
	@Test
	public void testReadTestFileNameEmptyString() throws FileNotFoundException {
		assertFalse(_sf.readFile(true, new String()));
	}
	
	/**
	 * Tests if was the return false if the file does not exists.
	 */
	@Test
	public void testReadTestFileNotExists() throws Exception {
		String fileName = "Test.java";
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withArguments(fileName)
			.thenReturn(file);
		
		assertFalse(_sf.readFile(true, fileName));
	}

	/**
	 * Tests if the source code has been properly prepared for the tests.
	 */
	@Test
	public void testPrepairSource() {
		String className = "Test";
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getLine()).thenReturn(1);
		when(method1.callsCount()).thenReturn(0);
		when(method1.getModifier()).thenReturn("public");
		when(method1.getClassName()).thenReturn(className);
		_sf.addMethod(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getLine()).thenReturn(2);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getModifier()).thenReturn("private");
		when(method2.getClassName()).thenReturn(className);
		_sf.addMethod(method2);
		
		CSMethod method3 = mock(CSMethod.class);
		when(method3.getLine()).thenReturn(3);
		when(method3.callsCount()).thenReturn(4);
		when(method3.getClassName()).thenReturn(className);
		_sf.addMethod(method3);
		
		String line1 = "Test line 1";
		_sf.addSourceLine(line1);
		String line2 = "Test line 2";
		_sf.addSourceLine(line2);
		String line3 = "Test line 3";
		_sf.addSourceLine(line3);
		
		_sf.prepaireSource();
		
		SourceLine line = _sf.getSourceLine(0);
		assertTrue(line.isBeginMethod());
		assertFalse(line.isLineTested());
		assertFalse(line.isMultiLineComment());
		assertFalse(line.isJavadoc());
		assertEquals(1, line.messageCount());
		assertEquals(ResourceBundle.getBundle(SourceTest.BUNDLE_FILE).getString(
				"sourceMethodNotTested"), line.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.ERROR, line.getMessage(0).getColor());
		assertEquals(className, line.getClassName());
		
		line = _sf.getSourceLine(1);
		assertTrue(line.isBeginMethod());
		assertFalse(line.isLineTested());
		assertFalse(line.isMultiLineComment());
		assertFalse(line.isJavadoc());
		assertEquals(1, line.messageCount());
		assertEquals(ResourceBundle.getBundle(SourceTest.BUNDLE_FILE).getString(
				"sourceMethodNotTested"), line.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line.getMessage(0).getColor());
		assertEquals(className, line.getClassName());
		
		line = _sf.getSourceLine(2);
		assertTrue(line.isBeginMethod());
		assertTrue(line.isLineTested());
		assertFalse(line.isMultiLineComment());
		assertFalse(line.isJavadoc());
		assertEquals(0, line.messageCount());
		assertEquals(className, line.getClassName());
	}
	
	/**
	 * Tests whether the list of source lines is returned correctly.
	 */
	@Test
	public void testGetSourceList() {
		assertEquals(new ArrayList<SourceLine>(), _sf.getSourceList());
	}

	/**
	 * Tests whether the list of methods is returned correctly.
	 */
	@Test
	public void testGetMethodList() {
		assertEquals(new ArrayList<CSMethod>(), _sf.getMethodList());
	}

}
