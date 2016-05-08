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

/**
 * Saves the configuration from the configuration file.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Config {
	/**
	 * Saves the directory for the source code.
	 */
	private String _pathSrc;
	
	/**
	 * Saves the directory for the results.
	 */
	private String _pathResult;
	
	/**
	 * Saves the directory for the fit results.
	 */
	private String _pathSuitesResult;
	
	/**
	 * Saves the directory for the libraries.
	 */
	private String _pathLibrary;
	
	/**
	 * Saves the class path.
	 */
	private String _classpath;

	/**
	 * Initialize the data of this class
	 */
	public Config() {
		_classpath = new String();
		_pathLibrary = new String();
		_pathResult = new String();
		_pathSrc = new String();
		_pathSuitesResult = new String();
	}

	public String getPathSrc() {
		return _pathSrc;
	}

	public void setPathSrc(String pathSrc) {
		if ((pathSrc == null) || pathSrc.isEmpty())
			throw new IllegalArgumentException();
		_pathSrc = pathSrc;
	}

	public String getPathResult() {
		return _pathResult;
	}

	public void setPathResult(String pathResult) {
		if ((pathResult == null) || pathResult.isEmpty())
			throw new IllegalArgumentException();
		_pathResult = pathResult;
	}

	public String getPathSuitesResult() {
		return _pathSuitesResult;
	}

	public void setPathSuitesResult(String pathSuitesResult) {
		if ((pathSuitesResult == null) || pathSuitesResult.isEmpty())
			throw new IllegalArgumentException();
		_pathSuitesResult = pathSuitesResult;
	}

	public String getPathLibrary() {
		return _pathLibrary;
	}

	public void setPathLibrary(String pathLibrary) {
		if ((pathLibrary == null) || pathLibrary.isEmpty())
			throw new IllegalArgumentException();
		_pathLibrary = pathLibrary;
	}

	public String getClasspath() {
		return _classpath;
	}

	public void setClasspath(String classpath) {
		if ((classpath == null) || classpath.isEmpty())
			throw new IllegalArgumentException();
		_classpath = classpath;
	}

}
