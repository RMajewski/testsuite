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

import java.util.Date;

import org.testsuite.helper.HelperCalendar;

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
	 * Saves whether the test should be executed.
	 */
	private boolean _executed;
	
	/**
	 * Saves whether the test is terminated.
	 */
	private boolean _terminated;
	
	/**
	 * Saves whether the test is run into a separate JVM.
	 * 
	 * Standard value is true.
	 */
	private boolean _jvm;
	
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
		_executed = true;
		_terminated = false;
		_jvm = true;
	}
	
	/**
	 * Initialize the data of this class
	 */
	public Test(String name) {
		this();
		setName(name);
	}
	
	/**
	 * Sets the start time
	 * 
	 * @param time Start time of the test
	 * 
	 * @deprecated
	 */
	public void setStart(long time) {
		_start = time;
	}
	
	/**
	 * Sets the start time
	 */ 
	public void setStart() {
		_start = new Date().getTime();
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
	 * 
	 * @deprecated Use {@link #setEnd()}
	 */
	public void setEnd(long time) {
		_end = time;
	}
	
	/**
	 * Specifies the end time.
	 */
	public void setEnd() {
		_end = new Date().getTime();
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
		if (error == null)
			throw new IllegalArgumentException();
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
		if (console == null)
			throw new IllegalArgumentException();
		_console = console;
	}
	
	/**
	 * Returns the stream, in which the expenditure of the console have been
	 * posted.
	 * 
	 * @return InputStream with the outputs of the console.
	 * 
	 * @deprecated Use {@link #getConsole()}
	 */
	public String getIn() {
		return _console;
	}
	
	/**
	 * Returns the stream, in which the expenditure of the console have been
	 * posted.
	 * 
	 * @return InputStream with the outputs of the console.
	 */
	public String getConsole() {
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

	/**
	 * Return the duration time as formatted string.
	 * 
	 * @return Duration time as formatted string.
	 */
	public String getDurationTimeFormattedString() {
		return HelperCalendar.timeToString(getDurationTime());
	}
	
	/**
	 * Returns whether the test should be executed.
	 * 
	 * @return Should the test be executed?
	 */
	public boolean isExecuted() {
		return _executed;
	}
	
	/**
	 * Saves whether the test should be executed.
	 * 
	 * @param executed Should the test be executed?
	 */
	public void setExecuted(boolean executed) {
		_executed = executed;
	}

	/**
	 * Returns whether the test was terminated.
	 * 
	 * @return If the test terminated?
	 */
	public boolean isTerminated() {
		return _terminated;
	}
	
	/**
	 * Saves whether the test was terminated.
	 * 
	 * @param terminated if the test terminated?
	 */
	public void setTerminated(boolean terminated) {
		_terminated = terminated;
	}
	
	/**
	 * Return whether the test is run in a separate JVM.
	 * 
	 * @return If the test is run in a separate JVM?
	 */
	public boolean isJvm() {
		return _jvm;
	}
	
	/**
	 * Saves whether the test is run in a separate JVM.
	 * 
	 * @param jvm If the test is run in a separate JVM?
	 */
	public void setJvm(boolean jvm) {
		_jvm = jvm;
	}
}
