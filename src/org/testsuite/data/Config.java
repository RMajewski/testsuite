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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves the configuration from the configuration file.
 * 
 * In the version 0.2 added a list for class paths
 * 
 * @author René Majewski
 *
 * @version 0.2
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
	 * 
	 * @deprecated Use {@link #_listClasspath}
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
	 * Saves the list of class paths.
	 */
	private List<String> _listClasspath;
	
	/**
	 * Saves the list of java script files.
	 */
	private List<String> _listJavascript;
	
	/**
	 * Saves the list of style sheet files.
	 */
	private List<String> _listStylesheet;
	
	/**
	 * Saves the maximum duration for a test.
	 */
	private long _maxDuration;

	/**
	 * Initialize the data of this class
	 */
	public Config() {
		_property = new ArrayList<String>();
		_listClasspath = new ArrayList<String>();
		_listJavascript = new ArrayList<String>();
		_listStylesheet = new ArrayList<String>();
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
	 * @deprecated Use {@link #getClassPath(int)}
	 */
	public String getClasspath() {
		return _classpath;
	}

	/**
	 * Sets the classpath
	 * 
	 * @param classpath The new classpath
	 * 
	 * @deprecated Use {@link #addClassPath(String)}
	 */
	public void setClasspath(String classpath) {
		if ((classpath == null) || classpath.isEmpty())
			throw new IllegalArgumentException();
		_classpath = classpath;
	}
	
	/**
	 * Added a class path to the list.
	 * 
	 * @param classpath The new class path, which is to be added to the list.
	 */
	public void addClassPath(String classpath) {
		if ((classpath == null) || classpath.isEmpty())
			throw new IllegalArgumentException();
		_listClasspath.add(classpath);
	}
	
	/**
	 * Change the specified class path
	 * 
	 * @param oldClasspath Old class path
	 * 
	 * @param newClasspath Changed class path
	 */
	public void changeClassPath(String oldClasspath, String newClasspath) {
		if ((oldClasspath == null) || oldClasspath.isEmpty())
			throw new IllegalArgumentException();
		if ((newClasspath == null) || newClasspath.isEmpty())
			throw new IllegalArgumentException();
		int index = _listClasspath.indexOf(oldClasspath);
		_listClasspath.remove(index);
		_listClasspath.add(index, newClasspath);
	}
	
	/**
	 * Returns the specified class path from the list
	 * 
	 * @param index Place where the class path is.
	 * 
	 * @return The specified class path.
	 */
	public String getClassPath(int index) {
		return _listClasspath.get(index);
	}
	
	/**
	 * Returns the count of class paths.
	 * 
	 * @return Count of class paths.
	 */
	public int classPathCount() {
		return _listClasspath.size();
	}
	
	/**
	 * Remove the class path from the list of class paths
	 * 
	 * @param classpath Class path that is to be deleted
	 */
	public void removeClassPath(String classpath) {
		if ((classpath == null) || classpath.isEmpty())
			throw new IllegalArgumentException();
		_listClasspath.remove(_listClasspath.indexOf(classpath));
	}
	
	/**
	 * Returns all class paths as parameter for the JVM.
	 * 
	 * @return All class paths as parameter for the JVM
	 */
	public String classPathsAsParameterJVM() {
		StringBuilder ret = new StringBuilder();
		
		if (_listClasspath.size() > 0) {
			for (int i = 0; i < _listClasspath.size(); i++) {
				if (i > 0)
					ret.append(File.pathSeparator);
				ret.append(_listClasspath.get(i));
			}
		}
		
		return ret.toString();
	}
	
	/**
	 * Returns the list of class paths.
	 * 
	 * @return The List of class paths.
	 */
	public List<String> getClassPathList() {
		return _listClasspath;
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
		if ((oldProperty == null) || oldProperty.isEmpty())
			throw new IllegalArgumentException();
		if ((newProperty == null) || newProperty.isEmpty())
			throw new IllegalArgumentException();
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
	 * Added a java script file to the list.
	 * 
	 * @param name The new java script file, which is to be added to the
	 * list.
	 */
	public void addJavascriptFile(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_listJavascript.add(name);
	}
	
	/**
	 * Returns the specified java script file from the list
	 * 
	 * @param index Place where the java script file is.
	 * 
	 * @return The specified java script file.
	 */
	public String getJavascriptFile(int index) {
		return _listJavascript.get(index);
	}
	
	/**
	 * Remove the java script file from the list of java script files.
	 * 
	 * @param name Java script file that is to be deleted
	 */
	public void removeJavascriptFile(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_listJavascript.remove(name);		
	}
	
	/**
	 * Change the java script file
	 * 
	 * @param old Old java script file name
	 * 
	 * @param name Changed java script file name
	 */
	public void changeJavascriptFile(String old, String name) {
		if ((old == null) || old.isEmpty())
			throw new IllegalArgumentException();
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		int index = _listJavascript.indexOf(old);
		_listJavascript.remove(index);
		_listJavascript.add(index, name);
	}
	
	/**
	 * Added a style sheet file to the list.
	 * 
	 * @param name The new style sheet file, which is to be added to the
	 * list.
	 */
	public void addStylesheetFile(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_listStylesheet.add(name);
	}
	
	/**
	 * Returns the specified style sheet file from the list
	 * 
	 * @param index Place where the style sheet file is.
	 * 
	 * @return The specified style sheet file.
	 */
	public String getStylesheetFile(int index) {
		return _listStylesheet.get(index);
	}
	
	/**
	 * Remove the style sheet file from the list of java script files.
	 * 
	 * @param name style sheet file that is to be deleted
	 */
	public void removeStylesheetFile(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_listStylesheet.remove(name);		
	}
	
	/**
	 * Change the style sheet file
	 * 
	 * @param old Old java style sheet name
	 * 
	 * @param name Changed style sheet file name
	 */
	public void changeStylesheetFile(String old, String name) {
		if ((old == null) || old.isEmpty())
			throw new IllegalArgumentException();
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		int index = _listStylesheet.indexOf(old);
		_listStylesheet.remove(index);
		_listStylesheet.add(index, name);
	}
	
	/**
	 * Returns the count of java script files.
	 * 
	 * @return Count of java script files.
	 */
	public int javascriptFileCount() {
		return _listJavascript.size();
	}
	
	/**
	 * Returns the count of style sheet files.
	 * 
	 * @return Count of style sheet files.
	 */
	public int stylesheetFileCount() {
		return _listStylesheet.size();
	}
	
	/**
	 * Return if no settings exist.
	 * @return If no settings exist?
	 */
	public boolean isEmpty() {
		return (_pathLibrary.isEmpty() && _pathResult.isEmpty() &&
				_pathSrc.isEmpty() && !_createHtml && (_maxDuration == 0) && 
				_property.isEmpty() && _listClasspath.isEmpty() && 
				_listJavascript.isEmpty() && _listStylesheet.isEmpty());
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
		_listClasspath.clear();
		_listJavascript.clear();
		_listStylesheet.clear();
	}
}
