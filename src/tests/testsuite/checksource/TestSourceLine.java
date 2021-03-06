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
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;

/**
 * Tests the class {@link org.testsuite.checksource.SourceLine}.
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
		assertFalse(_source.isJavadoc());
		assertEquals(0, _source.messageCount());
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
		assertFalse(_source.isBeginMethod());
		assertEquals(new String(), _source.getClassName());
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

	/**
	 * Tests if a javadoc comment is returned correctly.
	 */
	@Test
	public void testIsJavadoc() {
		assertFalse(_source.isJavadoc());
	}

	/**
	 * Tests if a javadoc comment is set correctly.
	 */
	@Test
	public void testSetJavadoc() {
		boolean javadoc = true;
		_source.setJavadoc(javadoc);
		assertTrue(_source.isJavadoc());
	}
	
	/**
	 * Tests if the number of messages is returned correctly.
	 */
	@Test
	public void testMessageCount() {
		assertEquals(0, _source.messageCount());
	}
	
	/**
	 * Tests if the new message is added correctly.
	 */
	@Test
	public void testAddMessage() {
		MessageColor message = mock(MessageColor.class);
		assertEquals(0, _source.messageCount());
		_source.addMessage(message);
		assertEquals(1, _source.messageCount());
		assertEquals(message, _source.getMessage(0));
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddMessageWithNullAsParameter() {
		_source.addMessage(null);
	}
	
	/**
	 * Tests if the message is returned correctly.
	 */
	@Test
	public void testGetMessage() {
		MessageColor message = mock(MessageColor.class);
		_source.addMessage(message);
		assertEquals(message, _source.getMessage(0));
	}
	
	/**
	 * Tests if a method in this line starts.
	 */
	@Test
	public void testIsBeginMethod() {
		assertFalse(_source.isBeginMethod());
	}

	/**
	 * Tests if a method in this line starts set correctly.
	 */
	@Test
	public void testSetBeginMethod() {
		_source.setBeginMethod(true);
		assertTrue(_source.isBeginMethod());
	}

	/**
	 * Tests if the name of class is returned correctly.
	 */
	@Test
	public void testGetClassName() {
		assertEquals(new String(), _source.getClassName());
	}

	/**
	 * Tests if the message is set correctly.
	 */
	@Test
	public void testSetClassName() {
		String className = "TestClass";
		_source.setClassName(className);
		assertEquals(className, _source.getClassName());
	}

}