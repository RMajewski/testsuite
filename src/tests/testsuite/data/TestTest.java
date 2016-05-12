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
import org.testsuite.data.Data;
/**
 * Tests the class {@link org.testsuite.data.Test}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTest {
	/**
	 * Save the class TestData.
	 */
	private org.testsuite.data.Test _data;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_data = new org.testsuite.data.Test();
	}
	
	/**
	 * Checks if the class of Data has been derived.
	 */
	@Test
	public void testJunitDataDerivedFromData() {
		assertEquals(Data.class.getName(),
				org.testsuite.data.Test.class.getSuperclass().getName());
	}
	
	/**
	 * Verifies that was correctly initialized.
	 * 
	 * @see org.testsuite.data.Test#TestData()
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
	 * Tests if the end time returned correctly.
	 * 
	 * @see org.testsuite.data.Test#getEnd()
	 */
	@Test
	public void testGetEnd() {
		assertEquals(0l, _data.getEnd());
	}
	
	/**
	 * Tests if the end time can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setEnd(long)
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
	 * @see org.testsuite.data.Test#getStart()
	 */
	@Test
	public void testGetStart() {
		assertEquals(0l, _data.getStart());
	}
	
	/**
	 * Tests if the start time can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setStart(long)
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
	 * @see org.testsuite.data.Test#getExitStatus()
	 */
	@Test
	public void testGetExitStatus() {
		assertEquals(-1, _data.getExitStatus());
	}
	
	/**
	 * Tests if the exit status can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setExitStatus(int)
	 */
	@Test
	public void testSetExitStatus() {
		int i = 10;
		_data.setExitStatus(i);
		assertEquals(i, _data.getExitStatus());
	}
	
	/**
	 * Tests if the error string returned correctly.
	 * 
	 * @see org.testsuite.data.Test#getError()
	 */
	@Test
	public void testGetError() {
		assertNull(_data.getError());
	}
	
	/**
	 * Tests if the error string can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setError(InputStream)
	 */
	@Test
	public void testSetError() {
		String error = "Error";
		_data.setError(error);
		assertEquals(error, _data.getError());
	}
	
	/**
	 * Tests if the console string returned correctly.
	 * 
	 * @see org.testsuite.data.Test#getIn()
	 */
	@Test
	public void testGetIn() {
		assertNull(_data.getIn());
	}
	
	/**
	 * Tests if the console string can be set correctly.
	 * 
	 * @see org.testsuite.data.Test#setStringConsole(InputStream)
	 */
	@Test
	public void testSetIn() {
		String console = "Console";
		_data.setStringConsole(console);
		assertEquals(console, _data.getIn());
	}
}
