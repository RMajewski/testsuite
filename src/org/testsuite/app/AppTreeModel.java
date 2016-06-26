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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.testsuite.checksource.annotation.CheckSource;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.TestSuite;

/**
 * Lists the tests on a tree.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class AppTreeModel implements TreeModel {
	/**
	 * Saves all validation event listener.
	 */
	private Vector<ValidationEventListener> _validationEventListeners;
	
	/**
	 * List of class TestRunner.
	 */
	private List<TestRunner> _testRunner;
	
	/**
	 * List of the TreeModelListener
	 */
	private Vector<TreeModelListener> _treeModelListener;
	
	/**
	 * Initializes this model.
	 */
	public AppTreeModel() {
		_testRunner = new ArrayList<TestRunner>();
		_treeModelListener = new Vector<TreeModelListener>();
		_validationEventListeners = new Vector<ValidationEventListener>();
	}
	
	/**
	 * Save the list of TestRunner
	 */
	public void setListOfTestRunner(List<TestRunner> list) {
		_testRunner = list;
		
		fireTreeStructureChanged();
	}
	
	/**
	 * Validate the configuration.
	 * 
	 * The following values validated:
	 * <ul>
	 * <li>General configuration: source path not specified</li>
	 * <li>General configuration: no TestRunner</li>
	 * <li>TestRunner: description</li>
	 * <li>TestRunner: file extension</li>
	 * <li>TestSuite: name</li>
	 * <li>TestSuite: name of package</li>
	 * <li>TestSuite: no test</li>
	 * <li>Test: name</li>
	 * </ul>
	 * 
	 * The following items are checked for exists:
	 * <ul>
	 * <li>General configuration: source path</li>
	 * <li>General configuration: result path</li>
	 * <li>General configuration: libraries path</li>
	 * <li>General configuration: class path</li>
	 * <li>TestRunner: library</li>
	 * <li>TestRunner: class path</li>
	 * <li>TestSuite: package</li>
	 * <li>TestSuite: test</li>
	 * </ul>
	 * 
	 * @param config The global configuration
	 * 
	 * @deprecated {@link #validateConfiguration()}
	 */
	public void validateConfiguration(Config config) {
		validateConfiguration();
	}
		
	/**
	 * Validate the configuration.
	 * 
	 * The following values validated:
	 * <ul>
	 * <li>General configuration: source path not specified</li>
	 * <li>General configuration: no TestRunner</li>
	 * <li>TestRunner: description</li>
	 * <li>TestRunner: file extension</li>
	 * <li>TestSuite: name</li>
	 * <li>TestSuite: name of package</li>
	 * <li>TestSuite: no test</li>
	 * <li>Test: name</li>
	 * </ul>
	 * 
	 * The following items are checked for exists:
	 * <ul>
	 * <li>General configuration: source path</li>
	 * <li>General configuration: result path</li>
	 * <li>General configuration: libraries path</li>
	 * <li>General configuration: class path</li>
	 * <li>TestRunner: library</li>
	 * <li>TestRunner: class path</li>
	 * <li>TestSuite: package</li>
	 * <li>TestSuite: test</li>
	 * </ul>
	 */
	public void validateConfiguration() {
		Object[] path = {getRoot()};
		
		Config config = Config.getInstance();
		
		// General configuration: source path
		if (config.getPathSrc().isEmpty())
			fireValidationError(new TreePath(path),
					ValidationEvent.TYPE_CONFIG_PATH_SRC, null);
		
		// General configuration: source path not exists
		else if (!new File(config.getPathSrc()).exists())
			fireValidationError(new TreePath(path),
					ValidationEvent.TYPE_CONFIG_PATH_SRC_NOT_EXISTS, null);
		
		// General configuration: libraries path not exists
		if (!config.getPathLibrary().isEmpty() && 
				!new File(config.getPathLibrary()).exists())
			fireValidationError(new TreePath(path), 
					ValidationEvent.TYPE_CONFIG_PATH_LIB_NOT_EXISTS, null);
		
		// General configuration: result path not exists
		if (!config.getPathResult().isEmpty() &&
				!new File(config.getPathResult()).exists())
			fireValidationError(new TreePath(path), 
					ValidationEvent.TYPE_CONFIG_PATH_RESULT_NOT_EXISTS, null);
		
		// General configuration: class path not exists
		for (int i = 0; i < config.classPathCount(); i++)
			if (!new File(config.getClassPath(i)).exists())
				fireValidationError(new TreePath(path), 
						ValidationEvent.TYPE_CONFIG_CLASSPATH_NOT_EXISTS,
						new int[]{i});
		
		// General configuration: java script not exists
		for (int i = 0; i < config.javascriptFileCount(); i++)
			if (getClass().getClassLoader().getResource(
					config.getJavascriptFile(i)) == null)
				fireValidationError(new TreePath(path), 
						ValidationEvent.TYPE_CONFIG_JAVASCRIPT_NOT_EXISTS,
						new int[]{i});
		
		// General configuration: style sheet not exists
		for (int i = 0; i < config.stylesheetFileCount(); i++)
			if (getClass().getClassLoader().getResource(
					config.getStylesheetFile(i)) == null)
				fireValidationError(new TreePath(path), 
						ValidationEvent.TYPE_CONFIG_STYLESHEET_NOT_EXISTS,
						new int[]{i});
		
		// No TestRunner inserted
		if (_testRunner.size() == 0)
			fireValidationError(new TreePath(path),
					ValidationEvent.TYPE_CONFIG_NO_TEST_RUNNER, null);
		
		// Test Runner
		for (int runner = 0; runner < _testRunner.size(); runner++) {
			// Check exists of directories and files.
			_testRunner.get(runner).checkFileExists();

			// Create path
			path = new Object[]{getRoot(), _testRunner.get(runner)};
			
			// No description
			if (_testRunner.get(runner).getDescription().isEmpty())
				fireValidationError(new TreePath(path),
						ValidationEvent.TYPE_TEST_RUNNER_DESCRIPTION, null);
			
			// No file extension
			if (_testRunner.get(runner).getFileExtension().isEmpty())
				fireValidationError(new TreePath(path),
						ValidationEvent.TYPE_TEST_RUNNER_FILE_EXTENSION, null);
			
			// Library not exists
			for (int lib = 0; lib < _testRunner.get(runner).libraryCount(); lib++) {
				String name = new String();
				
				if (_testRunner.get(runner).getLibrary(lib).getPath().isEmpty())
					name = config.getPathLibrary();
				else
					name = _testRunner.get(runner).getLibrary(lib).getPath();
				
				if (!new File(name + File.separator + _testRunner.get(runner)
						.getLibrary(lib).getFileName()).exists())
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS,
							new int[]{lib});
			}
			
			// Class path not exists
			for (int cp = 0; cp < _testRunner.get(runner).classPathCount(); cp++)
				if (!new File(_testRunner.get(runner).getClassPath(cp)).exists())
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS,
							new int[]{cp});
			
			// No TestSuite
			if (_testRunner.get(runner).testSuiteCount() == 0)
				fireValidationError(new TreePath(path),
						ValidationEvent.TYPE_TEST_RUNNER_NO_TEST_SUITE, null);
			
			// TestSuite
			for (int suite = 0; suite < _testRunner.get(runner).testSuiteCount();
					suite++) {
				path = new Object[]{getRoot(), _testRunner.get(runner),
						_testRunner.get(runner).getTestSuite(suite)};
				// No name
				if (_testRunner.get(runner).getTestSuite(suite).getName().isEmpty())
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_SUITE_NAME, null);
				
				// No name for package
				if (_testRunner.get(runner).getTestSuite(suite).getPackage().isEmpty())
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_SUITE_PACKAGE, null);
				
				// None exists package
				if (!_testRunner.get(runner).getTestSuite(suite).isExists())
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS,
							null);
				
				// No test
				if (_testRunner.get(runner).getTestSuite(suite).testCount() == 0)
					fireValidationError(new TreePath(path),
							ValidationEvent.TYPE_TEST_SUITE_NO_TEST, null);
				
				// Tests
				for (int test = 0; test < _testRunner.get(runner)
						.getTestSuite(suite).testCount(); test++) {
					// Path
					path = new Object[]{getRoot(), _testRunner.get(runner),
							_testRunner.get(runner).getTestSuite(suite),
							_testRunner.get(runner).getTestSuite(suite)
							.getTest(test)};
					
					// No Name
					if (_testRunner.get(runner).getTestSuite(suite)
							.getTest(test).getName().isEmpty())
						fireValidationError(new TreePath(path),
								ValidationEvent.TYPE_TEST_NAME, null);
					
					// Not exists
					if (!_testRunner.get(runner).getTestSuite(suite)
							.getTest(test).isExists())
						fireValidationError(new TreePath(path),
								ValidationEvent.TYPE_TEST_NOT_EXISTS, null);
				}
			}
		}
	}

	/**
	 * Adds a TreeModelListener to the list of TreeModelListener.
	 * 
	 * @param l Listener to be added to the list of listeners
	 */
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		_treeModelListener.add(l);
	}
	
	/**
	 * Returns the number of TreeModelListener in the list.
	 * 
	 * @return Number TreeModelListener in the list
	 */
	public int treeModelListenerCount() {
		return _treeModelListener.size();
	}

	/**
	 * Determines what to output
	 * 
	 * @param parent Parent element in the tree
	 * 
	 * @param index Child element that should be returned.
	 * 
	 * @return Object what to output
	 */
	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof List<?>)
			return ((List<?>)parent).get(index);
		if (parent instanceof TestRunner)
			return ((TestRunner)parent).getTestSuite(index);
		if (parent instanceof org.testsuite.data.Test)
			return ((org.testsuite.data.Test)parent).getName();
		if (parent instanceof TestSuite)
			return ((TestSuite)parent).getTest(index);
		return null;
	}

	/**
	 * Determines number of children from parent element.
	 * 
	 * @param parent Parent element in the tree
	 * 
	 * @return Number of childs
	 */
	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof List<?>)
			return ((List<?>)parent).size();
		if (parent instanceof TestRunner)
			return ((TestRunner)parent).testSuiteCount();
		if (parent instanceof TestSuite)
			return ((TestSuite)parent).testCount();
		return 0;
	}

	/**
	 * Is not needed.
	 */
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return 0;
	}

	/**
	 * Returns the root element of the tree
	 * 
	 * @return Root element of the tree
	 */
	@Override
	public Object getRoot() {
		return _testRunner;
	}

	/**
	 * Determines whether the tree element is a node or a leaf.
	 * 
	 * @param node Tree element is to be determined in which, if it is a leaf.
	 * 
	 * @return True if node is a leaf. False if it is not a leaf.
	 */
	@Override
	public boolean isLeaf(Object node) {
		return node instanceof org.testsuite.data.Test;
	}

	/**
	 * Deletes the specified TreeModelListener from the list of
	 * TreeModelListener.
	 * 
	 * @param l Listener, to be deleted from the list.
	 */
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		_treeModelListener.remove(l);
	}

	/**
	 * Is not needed.
	 */
	@CheckSource(ignored=true)
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}
	
	/**
	 * Called when the tree has changed.
	 */
	public void fireTreeStructureChanged() {
		for (int i = 0; i < _treeModelListener.size(); i++)
			_treeModelListener.elementAt(i).treeStructureChanged(
					new TreeModelEvent(this, new Object[] {"TestRunner"}));
	}
	
	/**
	 * Called when a node has changed.
	 * 
	 * @param path Specifies which node has changed.
	 */
	public void fireTreeNodesChanged(TreePath path) {
		int index = getChildCount(path.getLastPathComponent());
		
		for (int i = 0; i < _treeModelListener.size(); i++)
			_treeModelListener.elementAt(i).treeNodesChanged(
					new TreeModelEvent(this, path.getParentPath(), 
							new int[] {index}, 
							new Object[] {path.getLastPathComponent()}));
	}
	
	/**
	 * Returns the list of TestRunner
	 * 
	 * @return List of TestRunner
	 */
	public List<TestRunner> getTestRunnerList() {
		return _testRunner;
	}
	
	/**
	 * Adds a ValidationEventListener to the list of ValidationEventListeners.
	 * 
	 * @param l Listener to be added to the list of listeners
	 */
	public void addValidationEventListener(ValidationEventListener l) {
		_validationEventListeners.addElement(l);
	}
	
	/**
	 * Returns the number of ValidationEventListener in the list.
	 * 
	 * @return Number ValidationEventListener in the list
	 */
	public int validationEventListenerCount() {
		return _validationEventListeners.size();
	}
	
	/**
	 * Removes the specified listener from the list of listeners.
	 * 
	 * @param l ValidationEventListener to be deleted from the list of listeners
	 */
	public void removeValidationEventListener(ValidationEventListener l) {
		_validationEventListeners.remove(l);
	}
	
	/**
	 * Called when a validation error occurs.
	 */
	public void fireValidationError(TreePath path, int type, int[] indexes) {
		for (int i = 0; i < _validationEventListeners.size(); i++)
			_validationEventListeners.elementAt(i).validationError(
					new ValidationEvent(this, path, type, indexes));
	}
}
