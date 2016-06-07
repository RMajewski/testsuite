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

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.MessageColor;

/**
 * Tests the class {@link org.testsuite.checksource.MessageColor}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestMessageColor {
	/**
	 * Saves the instance of MessageColor
	 */
	private MessageColor _mc;
	
	/**
	 * Saves the text of the message
	 */
	private String _message;
	
	/**
	 * Saves the color of the message
	 */
	private Color _color;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_message = "Dies ist ein Test";
		_color = new Color(0x000000);
		_mc = new MessageColor(_message, _color);
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testMessageColor() {
		assertEquals(_message, _mc.getMessage());
		assertEquals(_color, _mc.getColor());
	}

	/**
	 * Tests if the text of message is returned correctly.
	 */
	@Test
	public void testGetMessage() {
		assertEquals(_message, _mc.getMessage());
	}

	/**
	 * Tests if the text of message is set correctly.
	 */
	@Test
	public void testSetMessage() {
		String test = "Test";
		_mc.setMessage(test);
		assertEquals(test, _mc.getMessage());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetMessageWithNullAsParameter() {
		_mc.setMessage(null);
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetMessageWithEmptyStringAsParameter() {
		_mc.setMessage(new String());
	}

	/**
	 * Tests if the color of the message is returned correctly.
	 */
	@Test
	public void testGetColor() {
		assertEquals(_color, _mc.getColor());
	}

	/**
	 * Tests if the color of the message is set correctly.
	 */
	@Test
	public void testSetColor() {
		Color color = new Color(255, 255, 255);
		_mc.setColor(color);
		assertEquals(color, _mc.getColor());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetColorWithNullAsParameter() {
		_mc.setColor(null);
	}

}
