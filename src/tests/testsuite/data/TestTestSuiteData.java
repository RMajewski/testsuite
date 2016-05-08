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

package tests.testsuite.data;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.TestSuiteData;

/**
 * Test the class {@link org.testsuite.data.TestSuiteData}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestTestSuiteData {

	/**
	 * Save the class TestSuiteData
	 */
	private TestSuiteData _data;
	
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new TestSuiteData();
	}
	
	/**
	 * Tests whether the data has been correctly initialized.
	 * 
	 * @see org.testsuite.data.TestSuiteData#TestSuiteData()
	 */
	@Test
	public void testJunitData() {
		assertEquals(new String(), _data.getName());
		assertEquals(new String(), _data.getPackage());
		assertFalse(_data.isExists());
		assertEquals(0, _data.testCount());
	}

	/**
	 * Tests if the name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#getName()
	 */
	@Test
	public void testGetName() {
		assertEquals(new String(), _data.getName());
	}
	
	/**
	 * Tests whether the name can be set correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#setName(String)
	 */
	@Test
	public void testSetName() {
		String name = "Test";
		_data.setName(name);
		assertEquals(name, _data.getName());
	}

	/**
	 * Tests if the package name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#getPackage()
	 */
	@Test
	public void testGetPackage() {
		assertEquals(new String(), _data.getPackage());
	}
	
	/**
	 * Tests whether the package name can be set correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#setPackage(String)
	 */
	@Test
	public void testSetPackage() {
		String name = "Test";
		_data.setPackage(name);
		assertEquals(name, _data.getPackage());
	}

	/**
	 * Tests if the path exists is returned correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#getPackage()
	 */
	@Test
	public void testIsExists() {
		assertFalse(_data.isExists());
	}
	
	/**
	 * Tests whether the path exists can be set correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#setPackage(String)
	 */
	@Test
	public void testSetExists() {
		boolean exists = true;
		_data.setExists(exists);
		assertEquals(exists, _data.isExists());
	}

	/**
	 * Tests if the number of tests can be determined correctly.
	 * 
	 * @see org.testsuite.data.TestSuiteData#testCount()
	 */
	@Test
	public void testTestCount() {
		assertEquals(0, _data.testCount());
	}
	
	/**
	 * Tests whether a test can be placed correctly in the list.
	 * 
	 * @see org.testsuite.data.TestSuiteData#addTest(org.testsuite.data.TestData)
	 */
	@Test
	public void testAddTest() {
		org.testsuite.data.TestData data = 
				mock(org.testsuite.data.TestData.class);
		_data.addTest(data);
		assertEquals(1, _data.testCount());
	}
	
	/**
	 * Tests whether the test can be read correctly from the list.
	 * 
	 * @see org.testsuite.data.TestSuiteData#getTest(int)
	 */
	@Test
	public void testGetTest() {
		org.testsuite.data.TestData data = 
				mock(org.testsuite.data.TestData.class);
		_data.addTest(data);
		assertEquals(data, _data.getTest(0));
	}
	
}
