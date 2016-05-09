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
import org.testsuite.data.Data;

/**
 * Tests the class {@link org.testsuite.data.Data}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestData {
	/**
	 * Save the class TestData.
	 */
	private Data _data;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new Data();
	}
	
	/**
	 * Tests if the file exists returned correctly.
	 */
	@Test
	public void testIsExists() {
		assertFalse(_data.isExists());
	}
	
	/**
	 * Tests if the file exists can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setExists(boolean)
	 */
	@Test
	public void testSetExists() {
		boolean exists = true;
		_data.setExists(exists);
		assertEquals(exists, _data.isExists());
	}
	
	/**
	 * Tests if the name is returned correctly.
	 * 
	 * @see org.testsuite.data.Test#getName()
	 */
	@Test
	public void testGetName() {
		assertEquals(new String(), _data.getName());
	}
	
	/**
	 * Tests whether the name can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setName(String)
	 */
	@Test
	public void testSetName() {
		String name = "test";
		_data.setName(name);
		assertEquals(name, _data.getName());
	}
	
	/**
	 * Tests if the name of the error  occurs when null is passed as the
	 * name.
	 * 
	 * @see org.testsuite.data.Test#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameNullAsName() {
		_data.setName(null);
	}
	
	/**
	 * Tests if the name of the error ... occurs when an empty string is passed
	 * as the name.
	 * 
	 * @see org.testsuite.data.Test#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameEmptyStringAsName() {
		_data.setName(new String());
	}
	
	/**
	 * Tests if the id is returned correctly.
	 * 
	 * @see org.testsuite.data.Test#getId()
	 */
	@Test
	public void testGetId() {
		assertEquals(-1, _data.getId());
	}
	
	/**
	 * Tests whether the id can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setId(int)
	 */
	@Test
	public void testSetId() {
		int id = 100;
		_data.setId(id);
		assertEquals(id, _data.getId());
	}

}
