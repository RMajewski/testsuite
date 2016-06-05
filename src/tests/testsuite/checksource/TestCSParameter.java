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
import org.testsuite.checksource.CSParameter;

/**
 * Tests the class {@link org.testsuite.checksource.CSParameter}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestCSParameter {
	/**
	 * Saves the instance of CSParameter
	 */
	private CSParameter _cs;
	
	/**
	 * Saves the name of type
	 */
	private String _type;
	
	/**
	 * Saves the name
	 */
	private String _name;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_type = "int";
		_name = "test";
		_cs = new CSParameter(_type, _name);
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testCSConst() {
		assertEquals(_name, _cs.getName());
		assertEquals(_type, _cs.getType());
	}

	/**
	 * Tests if the type is returned correctly
	 */
	@Test
	public void testGetType() {
		assertEquals(_type, _cs.getType());
	}

	/**
	 * Tests if the type is set correctly.
	 */
	@Test
	public void testSetType() {
		String type = "Object";
		_cs.setType(type);
		assertEquals(type, _cs.getType());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetTypeWithNullAsParameter() {
		_cs.setType(null);
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetTypeWithEmptyStringlAsParameter() {
		_cs.setType(null);
	}

	/**
	 * Tests if the name is returned correctly
	 */
	@Test
	public void testGetName() {
		assertEquals(_name, _cs.getName());
	}

	/**
	 * Tests if the name is set correctly.
	 */
	@Test
	public void testSetName() {
		String name = "Test";
		_cs.setName(name);
		assertEquals(name, _cs.getName());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameWithNullAsParameter() {
		_cs.setName(null);
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameWithEmptyStringlAsParameter() {
		_cs.setName(null);
	}

}
