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
public class TestData {
	/**
	 * Stores the name of the test
	 */
	private String _name;
	
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
	private InputStream _in;
	
	/**
	 * Saves the output of the error
	 */
	private InputStream _error;
	
	/**
	 * Saves whether the file exists.
	 */
	private boolean _exists;
	
	/**
	 * Initialize the data of this class
	 */
	public TestData() {
		_name = new String();
		_end = 0;
		_start = 0;
		_error = null;
		_exitStatus = -1;
		_in = null;
		_start = 0;
		_exists = false;
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
	 * @param stream InputStream, in which the errors have been posted
	 */
	public void setError(InputStream stream) {
		_error = stream;
	}
	
	/**
	 * Returns the stream in which the errors have been posted.
	 * 
	 * @return InputStream for error
	 */
	public InputStream getError() {
		return _error;
	}
	
	/**
	 * Sets the Steam, where the outputs of the console have been posted.
	 * 
	 * @param stream InputStream with the outputs of the console.
	 */
	public void setIn(InputStream stream) {
		_in = stream;
	}
	
	/**
	 * Returns the stream, in which the expenditure of the console have been
	 * posted.
	 * 
	 * @return InputStream with the outputs of the console.
	 */
	public InputStream getIn() {
		return _in;
	}
	
	/**
	 * Specifies whether the file exists.
	 * 
	 * @param exists In existence Testfile?
	 */
	public void setExists(boolean exists) {
		_exists = exists;
	}
	
	/**
	 * Returns exists in the test file.
	 * 
	 * @return In existence Testfile?
	 */
	public boolean isExists() {
		return _exists;
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
	 * Specifies the name of the tests requested.
	 * 
	 * @param name Name of the test
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * Returns the name of the test.
	 * 
	 * @return Name of the test
	 */
	public String getName() {
		return _name;
	}
}
