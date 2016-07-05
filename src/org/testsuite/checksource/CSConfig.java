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

/**
 * Saves settings for the check source.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class CSConfig {
	/**
	 * Saves the instance of this class
	 */
	private static CSConfig _instance;
	
	/**
	 * Saves the list of names for test classes
	 */
	private List<String> _tests;
	
	/**
	 * Saves the path of test classes
	 */
	private String _pathTests;
	
	/**
	 * Saves whether for files to be searched, which were not tested.
	 */
	private boolean _noneListedFiles;
	
	/**
	 * Saves the path for the source files.
	 */
	private String _noneListedPath;
	
	/**
	 * Saves the width of a source code line.
	 */
	private int _lineWidth;
	
	/**
	 * Saves the number of spaces for tab.
	 */
	private int _tabSpace;
	
	/**
	 * Saves the name of parser
	 */
	private String _parserName;
	
	/**
	 * Saves the timeout for parser
	 */
	private long _parserTimeout;
	
	/**
	 * Saves whether to parse. 
	 */
	private boolean _parserParse;
	
	/**
	 * Initialize the datas
	 */
	private CSConfig() {
		_tests = new ArrayList<String>();
		_pathTests = new String();
		_noneListedFiles = false;
		_noneListedPath = new String();
		_lineWidth = -1;
		_tabSpace = -1;
		_parserName = null;
		_parserTimeout = 20000;
		_parserParse = false;
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static CSConfig getInstance() {
		if (_instance == null)
			_instance = new CSConfig();
		
		return _instance;
	}
	
	/**
	 * Returns the count of names of test classes
	 * 
	 * @return Count of names of test classes
	 */
	public int testCount() {
		return _tests.size();
	}
	
	/**
	 * Returns the specified name of test class
	 * 
	 * @param index The index for name of test class
	 */
	public String getTestName(int index) {
		return _tests.get(index);
	}
	
	/**
	 * Added a name to the list of names of test classes
	 * 
	 * @param name The name that will be added to the list of names of test
	 * classes.
	 */
	public void addTestName(String name) {
		_tests.add(name);
	}
	
	/**
	 * Returns the path of check source tests
	 * 
	 * @param The path of check source tests
	 */
	public String getPathCheckSourceTests() {
		return _pathTests;
	}
	
	/**
	 * Sets the path of check source tests
	 * 
	 * @param path The new path of check source tests
	 */
	public void setPathCheckSourceTests(String path) {
		_pathTests = path;
	}
	
	/**
	 * Returns whether for files to be searched, which were not tested.
	 * 
	 * @return Target for files to be searched, which have not been tested?
	 */
	public boolean isListNoneTestedFiles() {
		return _noneListedFiles;
	}
	
	/**
	 * Sets whether for files to be searched, which were not tested.
	 * 
	 * @param listed Target for files to be searched, which have not been
	 * tested?
	 */
	public void setListNoneTestedFiles(boolean listed) {
		_noneListedFiles = listed;
	}
	
	/**
	 * Returns the path, where to look for untested files.
	 * 
	 * @returns The path, where to look for untested files.
	 */
	public String getNoneListedPath() {
		return _noneListedPath;
	}
	
	/**
	 * Sets the path, where to look for untested files.
	 * 
	 * @param path The path, where to look for untested files.
	 */
	public void setNoneListedPath(String path) {
		_noneListedPath = path;
	}
	
	/**
	 * Returns the width of a source code line
	 * 
	 * @return Width of a source code line
	 */
	public int getLineWidth() {
		return _lineWidth;
	}
	
	/**
	 * Sets the width of a source code line
	 * 
	 * @param width The width of a source code line
	 */
	public void setLineWidth(int width) {
		_lineWidth = width;
	}
	
	/**
	 * Returns the number of spaces for a tab
	 * 
	 * @return Number of spaces for a tab
	 */
	public int getTabSpace() {
		return _tabSpace;
	}
	
	/**
	 * Sets the number of spaces for a tab
	 * 
	 * @param width The number of spaces for a tab
	 */
	public void setTabSpace(int spaces) {
		_tabSpace = spaces;
	}
	
	/**
	 * Returns the name of parser
	 * 
	 * @return The nome of parser
	 */
	public String getParserName() {
		return _parserName;
	}
	
	/**
	 * Returns a instance of parser
	 * 
	 * @return A instance of parser
	 */
	public SimpleParser getParser() {
		if ((_parserName == null) || _parserName.isEmpty())
			return null;
		
		SimpleParser parser = null;
		try {
			parser = (SimpleParser)getClass().getClassLoader()
					.loadClass(_parserName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parser;
	}
	
	/**
	 * Sets the name of parser
	 * 
	 * @param name The new name of parser
	 */
	public void setParserName(String name) {
		_parserName = name;
	}
	
	/**
	 * Returns the timeout of parser.
	 * 
	 * @return The timeout of parser
	 */
	public long getParserTimeout() {
		return _parserTimeout;
	}
	
	/**
	 * Sets the timeout of parser
	 * 
	 * @param timeout The new time for the parser in milliseconds.
	 */
	public void setParserTimeout(long timeout) {
		_parserTimeout = timeout;
	}
	
	/**
	 * Returns whether to parse.
	 * 
	 * @return Should be parsed?
	 */
	public boolean getParserParse() {
		return _parserParse;
	}
	
	/**
	 * Sets whether to parse.
	 * 
	 * @param parse Should be parsed?
	 */
	public void setParserParse(boolean parse) {
		_parserParse = parse;
	}
}
