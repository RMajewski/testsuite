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
import org.testsuite.data.TodoData;

public class TestTodoData {
	/**
	 * Saves the instance of TodoData
	 */
	private TodoData _data;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_data = new TodoData();
	}

	/**
	 * Tests the initialize
	 */
	@Test
	public void testTodoData() {
		assertEquals(new String(), _data.getLine());
		assertEquals(-1, _data.getNumber());
		assertNull(_data.getFileName());
	}

	/**
	 * Tests the initialize
	 */
	@Test
	public void testTodoDataStringIntString() {
		String line = "Test";
		int number = 100;
		String name = "test.java";
		
		_data = new TodoData(line, number, name);
		
		assertEquals(line, _data.getLine());
		assertEquals(number, _data.getNumber());
		assertEquals(name, _data.getFileName());
	}

	/**
	 * Tests if the line returned correctly.
	 */
	@Test
	public void testGetLine() {
		assertEquals(new String(), _data.getLine());
	}

	/**
	 * Tests if the line set correctly.
	 */
	@Test
	public void testSetLine() {
		String line = "Test";
		_data.setLine(line);
		assertEquals(line, _data.getLine());
	}

	/**
	 * Tests if the line number returned correctly.
	 */
	@Test
	public void testGetNumber() {
		assertEquals(-1, _data.getNumber());
	}

	/**
	 * Tests if the line number set correctly.
	 */
	@Test
	public void testSetNumber() {
		int number = 100;
		_data.setNumber(number);
		assertEquals(number, _data.getNumber());
	}

	/**
	 * Tests if the file name returned correctly.
	 */
	@Test
	public void testGetFileName() {
		assertNull(_data.getFileName());
	}

	/**
	 * Tests if the file name set correctly.
	 */
	@Test
	public void testSetFileName() {
		String name = "test.java";
		_data.setFileName(name);
		assertEquals(name, _data.getFileName());
	}

}
