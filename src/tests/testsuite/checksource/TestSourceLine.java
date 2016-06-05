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
import org.testsuite.checksource.SourceLine;

/**
 * Tests the class {@link org.testsuite.checkSource.SourceLine}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestSourceLine {
	/**
	 * Save the instance of source.
	 */
	private SourceLine _source;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_source = new SourceLine();
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testSource() {
		assertEquals(new String(), _source.getLine());
		assertFalse(_source.isMultiLineComment());
		assertEquals(-1, _source.getLineNumber());
		assertFalse(_source.isLineTested());
	}

	/**
	 * Tests if a multi-line comment is returned correctly.
	 */
	@Test
	public void testIsMultiLineComment() {
		assertFalse(_source.isMultiLineComment());
	}

	/**
	 * Tests if a multiline comment is set correctly.
	 */
	@Test
	public void testSetMultiLineComment() {
		boolean multi = true;
		_source.setMultiLineComment(multi);
		assertTrue(_source.isMultiLineComment());
	}

	/**
	 * Tests if the line of source code returned correctly.
	 */
	@Test
	public void testGetLine() {
		assertEquals(new String(), _source.getLine());
	}

	/**
	 * Tests if the line of source code set correctly.
	 */
	@Test
	public void testSetLine() {
		String line = "Test";
		_source.setLine(line);
		assertEquals(line, _source.getLine());
	}

	/**
	 * Tests it the number of line returned correctly.
	 */
	@Test
	public void testGetLineNumber() {
		assertEquals(-1, _source.getLineNumber());
	}
	
	/**
	 * Tests if the number of line set correctly.
	 */
	@Test
	public void testSetLineNumber() {
		int number = 10;
		_source.setLineNumber(number);
		assertEquals(number, _source.getLineNumber());
	}
	
	/**
	 * Tests if the line was tested returned correctly.
	 */
	@Test
	public void testIsTested() {
		assertFalse(_source.isLineTested());
	}
	
	/**
	 * Tests if the line was tested set correctly.
	 */
	@Test
	public void testSetLineTested() {
		boolean tested = true;
		_source.setLineTested(tested);
		assertTrue(_source.isLineTested());
	}
}