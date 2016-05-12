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

package org.testsuite.data;

import java.io.InputStream;

/**
 * In this class, the data are stored into a text.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Test extends Data {
	/**
	 * Stores the start time
	 */
	private long _start;
	
	/**
	 * Saves the end time
	 */
	private long _end;
	
	/**
	 * Stores, as the test was completed.
	 */
	private int _exitStatus;
	
	/**
	 * Saves the output of the console
	 */
	private String _console;
	
	/**
	 * Saves the output of the error
	 */
	private String _error;
	
	/**
	 * Initialize the data of this class
	 */
	public Test() {
		super();
		_end = 0;
		_start = 0;
		_error = null;
		_exitStatus = -1;
		_console = null;
	}
	
	/**
	 * Sets the start time
	 * 
	 * @param time Start time of the test
	 */
	public void setStart(long time) {
		_start = time;
	}
	
	/**
	 * Returns the start time
	 * 
	 * @return Start time of the test
	 */
	public long getStart() {
		return _start;
	}
	
	/**
	 * Specifies the end time.
	 * 
	 * @param time Time at which the test was terminated.
	 */
	public void setEnd(long time) {
		_end = time;
	}
	
	/**
	 * Returns the end time.
	 * 
	 * @return Time at which the test was terminated.
	 */
	public long getEnd() {
		return _end;
	}
	
	/**
	 * Returns the time that has passed the test.
	 * 
	 * @return Duration of the test
	 */
	public long getDurationTime() {
		return _end - _start;
	}
	
	/**
	 * Sets the input stream for the error.
	 * 
	 * @param error String, in which the errors have been posted
	 */
	public void setError(String error) {
		_error = error;
	}
	
	/**
	 * Returns the stream in which the errors have been posted.
	 * 
	 * @return String for error
	 */
	public String getError() {
		return _error;
	}
	
	/**
	 * Sets the Steam, where the outputs of the console have been posted.
	 * 
	 * @param console String with the outputs of the console.
	 */
	public void setStringConsole(String console) {
		_console = console;
	}
	
	/**
	 * Returns the stream, in which the expenditure of the console have been
	 * posted.
	 * 
	 * @return InputStream with the outputs of the console.
	 */
	public String getIn() {
		return _console;
	}
	
	/**
	 * Specifies the exit status of the test completes.
	 * 
	 * @param status Exit status of the tests
	 */
	public void setExitStatus(int status) {
		_exitStatus = status;
	}
	
	/**
	 * Returns the exit status returned by the test.
	 * 
	 * @return Exit status of the tests
	 */
	public int getExitStatus() {
		return _exitStatus;
	}
}
