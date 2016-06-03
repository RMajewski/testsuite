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
import org.junit.Ignore;
import org.junit.Test;
import org.testsuite.data.Data;
import org.testsuite.data.TestSuite;

/**
 * Test the class {@link org.testsuite.data.TestSuite}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestTestSuite {

	/**
	 * Save the class TestSuiteData
	 */
	private TestSuite _data;
	
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new TestSuite();
	}
	
	/**
	 * Checks if the class of Data has been derived.
	 */
	@Test
	public void testJunitDataDerivedFromData() {
		assertEquals(Data.class.getName(),
				TestSuite.class.getSuperclass().getName());
	}
	
	/**
	 * Tests whether the data has been correctly initialized.
	 * 
	 * @see org.testsuite.data.TestSuite#TestSuite()
	 */
	@Test
	public void testJunitData() {
		assertEquals(new String(), _data.getName());
		assertEquals(new String(), _data.getPackage());
		assertFalse(_data.isExists());
		assertEquals(0, _data.testCount());
	}

	/**
	 * Tests if the package name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestSuite#getPackage()
	 */
	@Test
	public void testGetPackage() {
		assertEquals(new String(), _data.getPackage());
	}
	
	/**
	 * Tests whether the package name can be set correctly.
	 * 
	 * @see org.testsuite.data.TestSuite#setPackage(String)
	 */
	@Test
	public void testSetPackage() {
		String name = "Test";
		_data.setPackage(name);
		assertEquals(name, _data.getPackage());
	}
	
	/**
	 * Tests if the name of the error IllegalArgumentException occurs when null
	 * is passed as the package name.
	 * 
	 * @see org.testsuite.data.Test#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testsetPackageNullAsName() {
		_data.setPackage(null);
	}
	
	/**
	 * Tests if the name of the error IllegalArgumentException occurs when an
	 * empty string is passed as the package name.
	 * 
	 * @see org.testsuite.data.Test#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testsetPackageEmptyStringAsName() {
		_data.setPackage(new String());
	}

	/**
	 * Tests if the number of tests can be determined correctly.
	 * 
	 * @see org.testsuite.data.TestSuite#testCount()
	 */
	@Test
	public void testTestCount() {
		assertEquals(0, _data.testCount());
	}
	
	/**
	 * Tests whether a test can be placed correctly in the list.
	 * 
	 * @see org.testsuite.data.TestSuite#addTest(org.testsuite.data.Test)
	 */
	@Test
	public void testAddTest() {
		org.testsuite.data.Test data = 
				mock(org.testsuite.data.Test.class);
		_data.addTest(data);
		assertEquals(1, _data.testCount());
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddTestWithNullAsParameter() {
		_data.addTest(null);
	}
	
	/**
	 * Tests whether the test can be read correctly from the list.
	 * 
	 * @see org.testsuite.data.TestSuite#getTest(int)
	 */
	@Test
	public void testGetTest() {
		org.testsuite.data.Test data = 
				mock(org.testsuite.data.Test.class);
		_data.addTest(data);
		assertEquals(data, _data.getTest(0));
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetTestWithMinusNumberAsParameter() {
		_data.getTest(-1);
	}
	
	/**
	 * Verifies that the correct test is deleted.
	 * 
	 * @deprecated
	 */
	@Ignore("Test a deprecated method")
	@Test
	public void testRemoveTestOld() {
		org.testsuite.data.Test test1 = new org.testsuite.data.Test();
		test1.setName("Test1");
		_data.addTest(test1);
		
		org.testsuite.data.Test test2 = new org.testsuite.data.Test();
		test2.setName("Test2");
		_data.addTest(test2);
		
		org.testsuite.data.Test test3 = new org.testsuite.data.Test();
		test3.setName("Test3");
		_data.addTest(test3);
		
		assertEquals(3, _data.testCount());
		
		_data.removeTest(test2);
		
		assertEquals(2, _data.testCount());
		assertEquals(test1, _data.getTest(0));
		assertEquals(test3, _data.getTest(1));
	}
	
	/**
	 * Verifies that the correct test is deleted.
	 */
	@Test
	public void testRemoveTest() {
		org.testsuite.data.Test test1 = new org.testsuite.data.Test();
		test1.setName("Test1");
		_data.addTest(test1);
		
		org.testsuite.data.Test test2 = new org.testsuite.data.Test();
		test2.setName("Test2");
		_data.addTest(test2);
		
		org.testsuite.data.Test test3 = new org.testsuite.data.Test();
		test3.setName("Test3");
		_data.addTest(test3);
		
		assertEquals(3, _data.testCount());
		
		_data.removeTest(test2);
		
		assertEquals(2, _data.testCount());
		assertEquals(test1, _data.getTest(0));
		assertEquals(test3, _data.getTest(1));
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveTestWithNullAsParameter() {
		_data.removeTest(null);
	}

	/**
	 * Tests if the last test id is returned correctly.
	 * 
	 * @see org.testsuite.data.TestSuite#getPackage()
	 */
	@Test
	public void testGetLastTestId() {
		assertEquals(-1, _data.getLastTestId());
	}
	
	/**
	 * Tests whether the last test id can be set correctly.
	 * 
	 * @see org.testsuite.data.TestSuite#setPackage(String)
	 */
	@Test
	public void testSetLastTestId() {
		int id = 100;
		_data.setLastTestId(id);
		assertEquals(id, _data.getLastTestId());
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetLastIdWithMinusNumberAsParameter() {
		_data.setLastTestId(-1);
	}
	
}
