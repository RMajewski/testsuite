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

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CheckSource;

/**
 * Tests for the class {@link org.testsuite.checksource.CheckSource}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
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
	
	@Test
	public void testCheckSource() {
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(_nameResult, _check.getNameResultFile());
		assertTrue(_check.isCreateHtml());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithNullAsNameSource() {
		_check = new CheckSource(null, _nameTest, _nameResult);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithEmptyStringAsNameSource() {
		_check = new CheckSource(new String(), _nameTest, _nameResult);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithNullAsNameTest() {
		_check = new CheckSource(_nameSrc, null, _nameResult);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckSourceWithEmptyStringAsNameTest() {
		_check = new CheckSource(_nameSrc, new String(), _nameResult);
	}
	
	@Test
	public void testCheckSourceWithNullAsNameResult() {
		_check = new CheckSource(_nameSrc, _nameTest, null);
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(null, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}
	
	@Test
	public void testCheckSourceWithEmptyStringAsNameResult() {
		_check = new CheckSource(_nameSrc, _nameTest, new String());
		assertEquals(_nameSrc, _check.getNameSourceFile());
		assertEquals(_nameTest, _check.getNameTestFile());
		assertEquals(new String(), _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	@Test
	public void testSetNameSourceFile() {
		_nameSrc = "Test";
		_check.setNameSourceFile(_nameSrc);
		assertEquals(_nameSrc, _check.getNameSourceFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNameSourceFileWithNullAsParamter() {
		_check.setNameSourceFile(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNameSourceFileWithEmptyStringAsParamter() {
		_check.setNameSourceFile(null);
	}

	@Test
	public void testSetNameTestFile() {
		_nameTest = "Test";
		_check.setNameTestFile(_nameTest);
		assertEquals(_nameTest, _check.getNameTestFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNameTestFileWithNullAsParameter() {
		_check.setNameTestFile(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNameTestFileWithEmptyStringAsParameter() {
		_check.setNameTestFile(null);
	}

	@Test
	public void testGetNameSourceFile() {
		assertEquals(_nameSrc, _check.getNameSourceFile());
	}

	@Test
	public void testGetNameTestFile() {
		assertEquals(_nameTest, _check.getNameTestFile());
	}

	@Test
	public void testGetNameResultFile() {
		assertEquals(_nameResult, _check.getNameResultFile());
	}

	@Test
	public void testSetNameResultFile() {
		_nameResult = "Test";
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertTrue(_check.isCreateHtml());
	}

	@Test
	public void testSetNameResultFileWithNullAsParameter() {
		_nameResult = null;
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	@Test
	public void testSetNameResultFileWithEmptyStringllAsParameter() {
		_nameResult = new String();
		_check.setNameResultFile(_nameResult);
		assertEquals(_nameResult, _check.getNameResultFile());
		assertFalse(_check.isCreateHtml());
	}

	@Test
	public void testIsCreateHtml() {
		assertTrue(_check.isCreateHtml());
	}

	@Test
	public void testSetCreateHtml() {
		_check.setCreateHtml(false);
		assertFalse(_check.isCreateHtml());
	}

}
