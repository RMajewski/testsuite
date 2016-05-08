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

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests the class {@link org.testsuite.data.TestData}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestData {
	/**
	 * Save the class TestData.
	 */
	private org.testsuite.data.TestData _data;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new org.testsuite.data.TestData();
	}
	
	/**
	 * Verifies that was correctly initialized.
	 * 
	 * @see org.testsuite.data.TestData#TestData()
	 */
	@Test
	public void testTestData() {
		assertEquals(new String(), _data.getName());
		assertEquals(0l, _data.getEnd());
		assertEquals(0l, _data.getStart());
		assertEquals(-1, _data.getExitStatus());
		assertNull(_data.getError());
		assertNull(_data.getIn());
		assertFalse(_data.isExists());
	}
	
	/**
	 * Tests if the name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getName()
	 */
	@Test
	public void testGetName() {
		assertEquals(new String(), _data.getName());
	}
	
	/**
	 * Tests whether the name can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setName(String)
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
	 * @see org.testsuite.data.TestData#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameNullAsName() {
		_data.setName(null);
	}
	
	/**
	 * Tests if the name of the error ... occurs when an empty string is passed
	 * as the name.
	 * 
	 * @see org.testsuite.data.TestData#setName(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameEmptyStringAsName() {
		_data.setName(new String());
	}
	
	/**
	 * Tests if the end time returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getEnd()
	 */
	@Test
	public void testGetEnd() {
		assertEquals(0l, _data.getEnd());
	}
	
	/**
	 * Tests if the end time can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setEnd(long)
	 */
	@Test
	public void testSetEnd() {
		long l = 1879l;
		_data.setEnd(l);
		assertEquals(l, _data.getEnd());
	}
	
	/**
	 * Tests if the start time returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getStart()
	 */
	@Test
	public void testGetStart() {
		assertEquals(0l, _data.getStart());
	}
	
	/**
	 * Tests if the start time can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setStart(long)
	 */
	@Test
	public void testSetStart() {
		long l = 1879l;
		_data.setStart(l);
		assertEquals(l, _data.getStart());
	}
	
	/**
	 * Tests if the exit status returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getExitStatus()
	 */
	@Test
	public void testGetExitStatus() {
		assertEquals(-1, _data.getExitStatus());
	}
	
	/**
	 * Tests if the exit status can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setExitStatus(int)
	 */
	@Test
	public void testSetExitStatus() {
		int i = 10;
		_data.setExitStatus(i);
		assertEquals(i, _data.getExitStatus());
	}
	
	/**
	 * Tests if the error stream returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getError()
	 */
	@Test
	public void testGetError() {
		assertNull(_data.getError());
	}
	
	/**
	 * Tests if the error stream can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setError(InputStream)
	 */
	@Test
	public void testSetError() {
		InputStream is = mock(InputStream.class);
		_data.setError(is);
		assertEquals(is, _data.getError());
	}
	
	/**
	 * Tests if the console stream returned correctly.
	 * 
	 * @see org.testsuite.data.TestData#getIn()
	 */
	@Test
	public void testGetIn() {
		assertNull(_data.getIn());
	}
	
	/**
	 * Tests if the error stream can be set correctly.
	 * 
	 * @see org.testsuite.data.TestData#setIn(InputStream)
	 */
	@Test
	public void testSetIn() {
		InputStream is = mock(InputStream.class);
		_data.setIn(is);
		assertEquals(is, _data.getIn());
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
	 * @see org.testsuite.data.TestData#setExists(boolean)
	 */
	@Test
	public void testSetExists() {
		boolean exists = true;
		_data.setExists(exists);
		assertEquals(exists, _data.isExists());
	}

}
