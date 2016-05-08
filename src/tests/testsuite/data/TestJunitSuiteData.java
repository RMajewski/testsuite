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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.JunitData;
import org.testsuite.data.JunitSuiteData;
import org.testsuite.data.TestSuiteData;

/**
 * Test the class {@link org.testsuite.data.JunitSuiteData}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestJunitSuiteData {

	/**
	 * Save the class JunitDataSuiteData
	 */
	private JunitSuiteData _data;
	
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new JunitSuiteData();
	}
	
	/**
	 * Checks if the class of TestSuiteData has been derived.
	 */
	@Test
	public void testJunitDataDerivedFromTestData() {
		assertEquals(TestSuiteData.class.getName(),
				JunitSuiteData.class.getSuperclass().getName());
	}
	
	/**
	 * Tests whether a test can be placed correctly in the list.
	 * 
	 * @see org.testsuite.data.TestSuiteData#addTest(org.testsuite.data.TestData)
	 */
	@Test
	public void testAddTest() {
		JunitData data = mock(JunitData.class);
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
		JunitData data = mock(JunitData.class);
		_data.addTest(data);
		assertEquals(data, _data.getTest(0));
	}

}
