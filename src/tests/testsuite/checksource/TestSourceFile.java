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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.SourceFile;
import org.testsuite.checksource.SourceLine;

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
		String line2 = "*";
		String line3 = "*/";
		String line4 = "public class {";
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
		
		assertTrue(_sf.readFile());
		
		assertEquals(1, _sf.getSourceLine(0).getLineNumber());
		assertEquals(line1, _sf.getSourceLine(0).getLine());
		assertTrue(_sf.getSourceLine(0).isMultiLineComment());
		
		assertEquals(2, _sf.getSourceLine(1).getLineNumber());
		assertEquals(line2, _sf.getSourceLine(1).getLine());
		assertTrue(_sf.getSourceLine(1).isMultiLineComment());

		assertEquals(3, _sf.getSourceLine(2).getLineNumber());
		assertEquals(line3, _sf.getSourceLine(2).getLine());
		assertFalse(_sf.getSourceLine(2).isMultiLineComment());

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
		assertFalse(_sf.readFile());
	}
	
	/**
	 * Tests if was the return false, if the file name is empty string.
	 */
	@Test
	public void testReadFileNameEmptyString() throws FileNotFoundException {
		_sf.setFileName(new String());
		assertFalse(_sf.readFile());
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
		
		assertFalse(_sf.readFile());
	}

}
