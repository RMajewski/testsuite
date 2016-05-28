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

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.Junit;

/**
 * Test the class {@link org.testsuite.data.Junit}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestJunitData {

	/**
	 * Save the class JunitData
	 */
	private Junit _data;
	
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new Junit();
	}
	
	/**
	 * Checks if the class of TestData has been derived.
	 */
	@Test
	public void testJunitDataDerivedFromTestData() {
		assertEquals(org.testsuite.data.Test.class.getName(),
				Junit.class.getSuperclass().getName());
	}
	
	/**
	 * Tests whether the data has been correctly initialized.
	 * 
	 * @see org.testsuite.data.Junit#Junit()
	 */
	@Test
	public void testJunitData() {
		assertEquals(0, _data.getFail());
		assertEquals(0, _data.getOk());
	}
	
	/**
	 * Testing whether the number of correct test is returned correctly.
	 * 
	 * @see org.testsuite.data.Junit#getOk()
	 */
	@Test
	public void testGetOk() {
		assertEquals(0, _data.getOk());
	}
	
	/**
	 * Testing whether the number of correct tests can be set correctly.
	 * 
	 * @see org.testsuite.data.Junit#setOk(int)
	 */
	@Test
	public void testSetOk() {
		int i = 10;
		_data.setOk(i);
		assertEquals(i, _data.getOk());
	}
	
	/**
	 * Testing whether the number of failed tests is returned correctly.
	 * 
	 * @see org.testsuite.data.Junit#getFail()
	 */
	@Test
	public void testGetFail() {
		assertEquals(0, _data.getFail());
	}
	
	/**
	 * Testing whether the number of failed tests can be set correctly.
	 * 
	 * @see org.testsuite.data.Junit#setFail(int)
	 */
	@Test
	public void testSetFail() {
		int i = 10;
		_data.setFail(i);
		assertEquals(i, _data.getFail());
	}

}
