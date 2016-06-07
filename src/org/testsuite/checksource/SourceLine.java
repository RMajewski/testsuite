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

import java.util.ArrayList;
import java.util.List;

public class SourceLine {
	/**
	 * Saves the line of source code.
	 */
	private String _line;
	
	/**
	 * Saves whether the line belonging to a multi-line comment.
	 */
	private boolean _multiLineComment;
	
	/**
	 * Saves whether the line belonging to a javadoc comment. 
	 */
	private boolean _javadoc;
	
	/**
	 * Saves the number of line
	 */
	private int _number;
	
	/**
	 * Saves the line was tested.
	 */
	private boolean _tested;
	
	/**
	 * Saves the messages
	 */
	private List<MessageColor> _messages;
	
	/**
	 * Initialize the data.
	 */
	public SourceLine() {
		_line = new String();
		_multiLineComment = false;
		_number = -1;
		_tested = false;
		_javadoc = false;
		_messages = new ArrayList<MessageColor>();
	}
	
	/**
	 * Is this line to a multi-line comment?
	 */
	public boolean isMultiLineComment() {
		return _multiLineComment;
	}
	
	/**
	 * Saves whether the line belonging to a multi-line comment.
	 * 
	 * @param multi If the line belonging to a multi-line comment?
	 */
	public void setMultiLineComment(boolean multi) {
		_multiLineComment = multi;
	}
	
	/**
	 * Returns the line of source code
	 * 
	 * @return The line of source code
	 */
	public String getLine() {
		return _line;
	}
	
	/**
	 * Sets the line of source code.
	 * 
	 * @param line The new line of source code.
	 */
	public void setLine(String line) {
		_line = line;
	}
	
	/**
	 * Returns the number of line.
	 * 
	 * @return The number of line.
	 */
	public int getLineNumber() {
		return _number;
	}
	
	/**
	 * Set the number of line
	 * 
	 * @param number The new number of line
	 */
	public void setLineNumber(int number) {
		_number = number;
	}
	
	/**
	 * Returns this line was tested.
	 * 
	 * @return Is this line tested?
	 */
	public boolean isLineTested() {
		return _tested;
	}
	
	/**
	 * Set this line was tested.
	 * 
	 * @param tested If this line tested?
	 */
	public void setLineTested(boolean tested) {
		_tested = tested;
	}
	
	/**
	 * Returns in the line belongs to a Javadoc comment.
	 * 
	 * @return Is this line a Javadoc comment?
	 */
	public boolean isJavadoc() {
		return _javadoc;
	}
	
	/**
	 * Saves whether this line is part of a Javadoc comment.
	 * 
	 * @param javadoc Is this line a Javadoc comment?
	 */
	public void setJavadoc(boolean javadoc) {
		_javadoc = javadoc;
	}
	
	/**
	 * Returns the number of messages
	 * 
	 * @return The number of messages
	 */
	public int messageCount() {
		return _messages.size();
	}
	
	/**
	 * Adds a new message in the list of messages.
	 * 
	 * @param message The new message
	 */
	public void addMessage(MessageColor message) {
		if (message == null)
			throw new IllegalArgumentException();
		
		_messages.add(message);
	}
	
	/**
	 * Returns the specified message
	 * 
	 * @param index The index of the message to return.
	 * 
	 * @return The specified message.
	 */
	public MessageColor getMessage(int index) {
		return _messages.get(index);
	}
}
