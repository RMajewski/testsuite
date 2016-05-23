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

import java.util.ArrayList;
import java.util.List;

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
	 * Saves whether the HTML file to be created or not.
	 */
	private boolean _createHtml;
	
	/**
	 * Saves the list of properties.
	 */
	private List<String> _property;
	
	/**
	 * Saves the maximum duration for a test.
	 */
	private long _maxDuration;

	/**
	 * Initialize the data of this class
	 */
	public Config() {
		_property = new ArrayList<String>();
		_pathSuitesResult = new String();
		_classpath = new String();
		clear();
	}

	/**
	 * Returns the directory for the source files.
	 * 
	 * @return Directory for the source files.
	 */
	public String getPathSrc() {
		return _pathSrc;
	}

	/**
	 * Sets the directory for the source files.
	 * 
	 * @param pathSrc The new directory for the source files
	 */
	public void setPathSrc(String pathSrc) {
		if (pathSrc == null)
			throw new IllegalArgumentException();
		_pathSrc = pathSrc;
	}

	/**
	 * Returns the directory for the result files.
	 * 
	 * @return Directory for the result files.
	 */
	public String getPathResult() {
		return _pathResult;
	}

	/**
	 * Sets the directory for the result files.
	 * 
	 * @param pathResult The new directory for the result files.
	 */
	public void setPathResult(String pathResult) {
		if (pathResult == null)
			throw new IllegalArgumentException();
		_pathResult = pathResult;
	}

	/**
	 * Returns the directory for the suite result files.
	 * 
	 * @return Directory for the suite result files.
	 */
	public String getPathSuitesResult() {
		return _pathSuitesResult;
	}

	/**
	 * Sets the directory for the suite result files.
	 * 
	 * @param pathSuitesResult The new directory for the suite result files.
	 */
	public void setPathSuitesResult(String pathSuitesResult) {
		if ((pathSuitesResult == null) || pathSuitesResult.isEmpty())
			throw new IllegalArgumentException();
		_pathSuitesResult = pathSuitesResult;
	}

	/**
	 * Returns the directory for the libraries.
	 * 
	 * @return Directory for the libraries.
	 */
	public String getPathLibrary() {
		return _pathLibrary;
	}

	/**
	 * Sets the directory for the libraries.
	 * 
	 * @param pathLibrary The new directory for libraries.
	 */
	public void setPathLibrary(String pathLibrary) {
		if (pathLibrary == null)
			throw new IllegalArgumentException();
		_pathLibrary = pathLibrary;
	}

	/**
	 * Returns the saved classpath
	 * 
	 * @return Classpath
	 * 
	 * @deprecated
	 */
	public String getClasspath() {
		return _classpath;
	}

	/**
	 * Sets the classpath
	 * 
	 * @param classpath The new classpath
	 * 
	 * @deprecated
	 */
	public void setClasspath(String classpath) {
		if ((classpath == null) || classpath.isEmpty())
			throw new IllegalArgumentException();
		_classpath = classpath;
	}

	/**
	 * Returns, to be creates in the HTML file for the results.
	 * 
	 * @return If the HTML file is generated with the results?
	 */
	public boolean isCreateHtml() {
		return _createHtml;
	}
	
	/**
	 * Saves whether the HTML file to be generated with the results.
	 * 
	 * @param create If the HTML file is generated with the resulst?
	 */
	public void setCreateHtml(boolean create) {
		_createHtml = create;
	}
	
	/**
	 * Returns the count of properties.
	 * 
	 * @return Count of properties.
	 */
	public int propertyCount() {
		return _property.size();
	}
	
	/**
	 * Added a property to the list.
	 * 
	 * @param property The new property, which is to be added to the list.
	 */
	public void addProperty(String property) {
		if ((property == null) || property.isEmpty())
			throw new IllegalArgumentException();
		_property.add(property);
	}
	
	/**
	 * Remove the property from the list of system properties
	 * 
	 * @param property Property that is to be deleted
	 */
	public void removeProperty(String property) {
		if ((property == null) || property.isEmpty())
			throw new IllegalArgumentException();
		_property.remove(_property.indexOf(property));
	}
	
	/**
	 * Returns a property from the list
	 * 
	 * @param index Place where the property is.
	 * 
	 * @return The property specified.
	 */
	public String getProperty(int index) {
		return _property.get(index);
	}
	
	/**
	 * Change the system property
	 * 
	 * @param oldProperty Old system property
	 * 
	 * @param newProperty Changed system property
	 */
	public void changeProperty(String oldProperty, String newProperty) {
		int index = _property.indexOf(oldProperty);
		_property.remove(index);
		_property.add(index, newProperty);
	}
	
	/**
	 * Returns the maximum test duration.
	 * 
	 * @return The maximum test duration.
	 */
	public long getMaxDuration() {
		return _maxDuration;
	}
	
	/**
	 * Sets the maximum test duration.
	 * 
	 * @param duration The new maximum test duration.
	 */
	public void setMaxDuration(long duration) {
		_maxDuration = duration;
	}
	
	/**
	 * Return if no settings exist.
	 * @return If no settings exist?
	 */
	public boolean isEmpty() {
		return (_pathLibrary.isEmpty() && _pathResult.isEmpty() &&
				_pathSrc.isEmpty() && !_createHtml && (_maxDuration == 0) && 
				_property.isEmpty());
	}
	
	/**
	 * Clear all the data
	 */
	public void clear() {
		_pathLibrary = new String();
		_pathResult = new String();
		_pathSrc = new String();
		_createHtml = false;
		_maxDuration = 0;
		_property.clear();
	}
}
