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

package org.testsuite.app;

import java.util.EventObject;

import javax.swing.tree.TreePath;

/**
 * Saves all necessary data for the validation event.
 * @author René Majewski
 *
 * @version 0.1
 */
@SuppressWarnings("serial")
public class ValidationEvent extends EventObject {
	/**
	 * Saves the number of types.
	 */
	private static int _typeCount = 0;
	
	/**
	 * Specifies that the path for the source file in general configuration was
	 * not specified.
	 */
	public static final int TYPE_CONFIG_PATH_SRC = ++_typeCount;
	
	/**
	 * Specifies that the TestRunner in general configuration was not specified.
	 */
	public static final int TYPE_CONFIG_NO_TEST_RUNNER = ++_typeCount;
	
	/**
	 * Specifies that the path for the source files was not exists.
	 */
	public static final int TYPE_CONFIG_PATH_SRC_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that the path for result files was not exists.
	 */
	public static final int TYPE_CONFIG_PATH_RESULT_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that the path for libraries files was not exists.
	 */
	public static final int TYPE_CONFIG_PATH_LIB_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that a class path was not exists.
	 */
	public static final int TYPE_CONFIG_CLASSPATH_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that a java script file was not exists.
	 */
	public static final int TYPE_CONFIG_JAVASCRIPT_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that a style sheet file was not exists.
	 */
	public static final int TYPE_CONFIG_STYLESHEET_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that the description for the test runner  was not specified.
	 */
	public static final int TYPE_TEST_RUNNER_DESCRIPTION = ++_typeCount;
	
	/**
	 * Specifies that the file extension for the test runner was not specified.
	 */
	public static final int TYPE_TEST_RUNNER_FILE_EXTENSION = ++_typeCount;
	
	/**
	 * Specifies that a library not exists.
	 */
	public static final int TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that a class path not exists.
	 */
	public static final int TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that a TestSuite in TestRunner was not specified.
	 */
	public static final int TYPE_TEST_RUNNER_NO_TEST_SUITE = ++_typeCount;
	
	/**
	 * Specifies that the name of test suite was not specified.
	 */
	public static final int TYPE_TEST_SUITE_NAME = ++_typeCount;
	
	/**
	 * Specifies that the package of test suite was not specified.
	 */
	public static final int TYPE_TEST_SUITE_PACKAGE = ++_typeCount;
	
	/**
	 * Specifies that the package of test suite was not exists.
	 */
	public static final int TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Specifies that the test of test suite was not specified.
	 */
	public static final int TYPE_TEST_SUITE_NO_TEST = ++_typeCount;
	
	/**
	 * Specifies that the name of test was not specified.
	 */
	public static final int TYPE_TEST_NAME = ++_typeCount;
	
	/**
	 * Specifies that the file of test is not exists.
	 */
	public static final int TYPE_TEST_NOT_EXISTS = ++_typeCount;
	
	/**
	 * Saves the TreePath to selection the node in tree
	 */
	private TreePath _path;
	
	/**
	 * Saves type of the event
	 */
	private int _type;
	
	/**
	 * Saves the indexes.
	 */
	private int[] _indexes;

	/**
	 * Initialize data
	 * 
	 * @param src Has the event triggered.
	 * 
	 * @param path The selection path for tree
	 * 
	 * @param type The type of this event.
	 * 
	 * @param indexes If used for certain types.
	 */
	public ValidationEvent(Object src, TreePath path, int type, int[] indexes) {
		super(src);
		_path = path;
		if ((type <= 0) || (type > _typeCount))
			throw new IllegalArgumentException();
		_type = type;
		_indexes = indexes;
	}

	/**
	 * Return the selection path for tree.
	 * 
	 * @return The selection path for tree
	 */
	public TreePath getSelectionPath() {
		return _path;
	}
	
	/**
	 * Return the type of this event.
	 * 
	 * @return Type of this event.
	 */
	public int getType() {
		return _type;
	}
	
	/**
	 * Return the array of indexes.
	 * 
	 * @return Array of indexes
	 */
	public int[] getIndexes() {
		return _indexes;
	}
	
	/**
	 * Return the number of types.
	 * 
	 * @return Number of types
	 */
	public static final int typeCount() {
		return _typeCount;
	}
}
