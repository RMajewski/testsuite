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

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.Fit;
import org.testsuite.data.Junit;

/**
 * Test the class {@link org.testsuite.data.Fit}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestFit {

	/**
	 * Save the class FitData
	 */
	private Fit _data;
	
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new Fit();
	}
	
	/**
	 * Checks if the class of TestData has been derived.
	 */
	@Test
	public void testFitDataDerivedFromTestData() {
		assertEquals(Junit.class.getName(),
				Fit.class.getSuperclass().getName());
	}
	
	/**
	 * Tests whether the data has been correctly initialized.
	 * 
	 * @see org.testsuite.data.Fit#Fit()
	 */
	@Test
	public void testFitData() {
		assertEquals(0, _data.getIgnore());
		assertEquals(0, _data.getException());
	}
	
	/**
	 * Testing whether the number of ignored tests is returned correctly.
	 * 
	 * @see org.testsuite.data.Fit#getIgnore()
	 */
	@Test
	public void testGetIgnore() {
		assertEquals(0, _data.getIgnore());
	}
	
	/**
	 * Testing whether the number of ignored tests can be set correctly.
	 * 
	 * @see org.testsuite.data.Fit#setIgnore(int)
	 */
	@Test
	public void testSetIgnore() {
		int i = 10;
		_data.setIgnore(i);
		assertEquals(i, _data.getIgnore());
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetIgnoreWithMinusNumberAsParamter() {
		_data.setIgnore(-1);
	}
	
	/**
	 * Testing whether the number of exception tests is returned correctly.
	 * 
	 * @see org.testsuite.data.Fit#getException()
	 */
	@Test
	public void testGetException() {
		assertEquals(0, _data.getException());
	}
	
	/**
	 * Testing whether the number of exception tests can be set correctly.
	 * 
	 * @see org.testsuite.data.Fit#setException(int)
	 */
	@Test
	public void testSetException() {
		int i = 10;
		_data.setException(i);
		assertEquals(i, _data.getException());
	}
	
	/**
	 * Verifies that the error IllegalArgumentException triggered.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetExceptionWithMinusNumberAsParamter() {
		_data.setException(-1);
	}
}
