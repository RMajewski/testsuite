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
import org.testsuite.data.Library;

/**
 * Tests the class {@link org.testsuite.data.Library}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestLibrary {

	/**
	 * Hold an instance of the Library
	 */
	private Library _data;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new Library();
	}

	/**
	 * Tests if the file name is returned correctly.
	 */
	@Test
	public void testGetFileName() {
		assertEquals(new String(), _data.getFileName());
	}
	
	/**
	 * Tests if the file name is set correctly.
	 */
	@Test
	public void testSetFileName() {
		String name = "Test";
		_data.setFileName(name);
		assertEquals(name, _data.getFileName());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the file name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetFileNameWithNullAsParameter() {
		_data.setFileName(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as the file name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetFileNameWithEmptyStringAsParameter() {
		_data.setFileName(new String());
	}

	/**
	 * Tests if the name is returned correctly.
	 */
	@Test
	public void testGetName() {
		assertEquals(new String(), _data.getName());
	}
	
	/**
	 * Tests if the name is set correctly.
	 */
	@Test
	public void testSetName() {
		String name = "Test";
		_data.setName(name);
		assertEquals(name, _data.getName());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameWithNullAsParameter() {
		_data.setName(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as the name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameWithEmptyStringAsParameter() {
		_data.setName(new String());
	}

	/**
	 * Tests if the path is returned correctly.
	 */
	@Test
	public void testGetPath() {
		assertEquals(new String(), _data.getPath());
	}
	
	/**
	 * Tests if the path is set correctly.
	 */
	@Test
	public void testSetPath() {
		String path = "Test";
		_data.setPath(path);
		assertEquals(path, _data.getPath());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the path.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathWithNullAsParameter() {
		_data.setPath(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as the path.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathWithEmptyStringAsParameter() {
		_data.setPath(new String());
	}

	/**
	 * Tests if the version is returned correctly.
	 */
	@Test
	public void testGetVersion() {
		assertEquals(new String(), _data.getVersion());
	}
	
	/**
	 * Tests if the version is set correctly.
	 */
	@Test
	public void testSetVersion() {
		String version = "1.1.1";
		_data.setFileName(version);
		assertEquals(version, _data.getFileName());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the version.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetVersionWithNullAsParameter() {
		_data.setVersion(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if empty string is
	 * passed as the version.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetVersionWithEmptyStringAsParameter() {
		_data.setVersion(new String());
	}

}
