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

package org.testsuite.checksource;

import java.awt.Color;

/**
 * Saves the message and their color.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class MessageColor {
	/**
	 * Saves the message.
	 */
	private String _message;
	
	/**
	 * Saves the color
	 */
	private Color _color;
	
	/**
	 * Initialize the data.
	 * 
	 * @param message The new text of the message.
	 * 
	 * @param color The new color.
	 */
	public MessageColor(String message, Color color) {
		setMessage(message);
		setColor(color);
	}
	
	/**
	 * Return the message
	 * 
	 * @return The text of message.
	 */
	public String getMessage() {
		return _message;
	}
	
	/**
	 * Sets the text of message.
	 * 
	 * @param message The new text of message.
	 */
	public void setMessage(String message) {
		if ((message == null) || message.isEmpty())
			throw new IllegalArgumentException();
		_message = message;
	}
	
	/**
	 * Return the color.
	 * 
	 * @return The color
	 */
	public Color getColor() {
		return _color;
	}
	
	/**
	 * Sets the color.
	 * 
	 * @param color The new color
	 */
	public void setColor(Color color) {
		if (color == null)
			throw new IllegalArgumentException();
		_color = color;
	}
}
