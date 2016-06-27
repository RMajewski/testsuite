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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.CheckSource;
import org.testsuite.checksource.SourceFile;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.html.HtmlOut;

/**
 * Tests for the class {@link org.testsuite.checksource.CheckSource}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CheckSource.class})
public class TestCheckSource {
	/**
	 * Saves the instance of CheckSource
	 */
	private CheckSource _check;
	
	/**
	 * Saves the name of source file
	 */
	private String _nameSrc;
	
	/**
	 * Saves the name of test file;
	 */
	private String _nameTest;
	
	/**
	 * Saves the name of result file;
	 */
	private String _nameResult;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_nameResult = "result/CheckSource.html";
		_nameTest = "src/tests/testsuite/checkSource/TestCheckSource.java";
		_nameSrc = "src/org/testsuite/checkSource/CheckSource.java";
		_check = new CheckSource(_nameSrc, _nameTest, _nameResult);
	}
	
	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testCheckSource() {
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(_nameResult, _check.getNameResultFile());
		assertTrue(_check.isCreateHtml());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithNullAsNameSource() {
		_check = new CheckSource(null, _nameTest, _nameResult);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithEmptyStringAsNameSource() {
		_check = new CheckSource(new String(), _nameTest, _nameResult);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithNullAsNameTest() {
		_check = new CheckSource(_nameSrc, null, _nameResult);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithEmptyStringAsNameTest() {
		_check = new CheckSource(_nameSrc, new String(), _nameResult);
	}
	
	/**
	 * Tests if not an HTML file to be created.
	 */
	@Test
	public void testCheckSourceWithNullAsNameResult() {
		_check = new CheckSource(_nameSrc, _nameTest, null);
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(null, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}
	
	/**
	 * Tests if not an HTML file to be created.
	 */
	@Test
	public void testCheckSourceWithEmptyStringAsNameResult() {
		_check = new CheckSource(_nameSrc, _nameTest, new String());
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(new String(), _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	/**
	 * Tests if the source file can be set correctly.
	 */
	@Test
	public void testSetNameSourceFile() {
		_nameSrc = "Test";
		_check.setNameSourceFile(_nameSrc);
		assertEquals(_nameSrc, _check.getNameSourceFile());
	}

	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameSourceFileWithNullAsParamter() {
		_check.setNameSourceFile(null);
	}

	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameSourceFileWithEmptyStringAsParamter() {
		_check.setNameSourceFile(null);
	}

	/**
	 * Tests if the test file can be set correctly.
	 */
	@Test
	public void testSetNameTestFile() {
		_nameTest = "Test";
		_check.setNameTestFile(_nameTest);
		assertEquals(_nameTest, _check.getNameTestFile());
	}

	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameTestFileWithNullAsParameter() {
		_check.setNameTestFile(null);
	}

	/**
	 * Tests whether the error IllegalArgumentException was triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameTestFileWithEmptyStringAsParameter() {
		_check.setNameTestFile(null);
	}

	/**
	 * Tests if the source file is returned correctly.
	 */
	@Test
	public void testGetNameSourceFile() {
		assertEquals(_nameSrc, _check.getNameSourceFile());
	}

	/**
	 * Tests if the test file is returned correctly.
	 */
	@Test
	public void testGetNameTestFile() {
		assertEquals(_nameTest, _check.getNameTestFile());
	}

	/**
	 * Tests if the result file is returned correctly.
	 */
	@Test
	public void testGetNameResultFile() {
		assertEquals(_nameResult, _check.getNameResultFile());
	}

	/**
	 * Tests if the result file can be set correctly.
	 */
	@Test
	public void testSetNameResultFile() {
		_nameResult = "Test";
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertTrue(_check.isCreateHtml());
	}

	/**
	 * Tests if not an HTML file to be created.
	 */
	@Test
	public void testSetNameResultFileWithNullAsParameter() {
		_nameResult = null;
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	/**
	 * Tests if not an HTML file to be created.
	 */
	@Test
	public void testSetNameResultFileWithEmptyStringllAsParameter() {
		_nameResult = new String();
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	/**
	 * Tests if returned correctly if an HTML file to be created.
	 */
	@Test
	public void testIsCreateHtml() {
		assertTrue(_check.isCreateHtml());
	}

	/**
	 * Tests if set correctly if an HTML file to be created.
	 */
	@Test
	public void testSetCreateHtml() {
		_check.setCreateHtml(false);
		assertFalse(_check.isCreateHtml());
	}

	/**
	 * 
	 */
	@Test
	public void testRun() throws Exception {
		SourceFile sf = mock(SourceFile.class);
		
		PowerMockito.whenNew(SourceFile.class)
			.withAnyArguments()
			.thenReturn(sf);
		
		_check = new CheckSource(_nameSrc, _nameTest, _nameResult);
		_check.run();
		
		verify(sf).readFile(false, null);
		verify(sf).readFile(true, _nameTest);
		verify(sf).prepaireSource();
	}

	/**
	 * Test whether the HTML result is produced.
	 */
	@Test
	public void testCreateHtmlOut() throws Exception {
		HtmlOut html = mock(HtmlOut.class);
		
		PowerMockito.whenNew(HtmlOut.class)
			.withArguments(_nameResult)
			.thenReturn(html);
		
		_check.createHtmlOut();
		
		verify(html).createHtml(_check.getSourceLineList(), 
				_check.getMethodList());
	}
	
	/**
	 * Test whether the list of source lines is returned correctly.
	 */
	@Test
	public void testGetSourceLineList() {
		assertEquals(new ArrayList<SourceLine>(), _check.getSourceLineList());
	}
	
	/**
	 * Test whether the list of methods is returned correctly.
	 */
	@Test
	public void testGetMethodList() {
		assertEquals(new ArrayList<CSMethod>(), _check.getMethodList());
	}

}
