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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.testsuite.core.TestRunner;
import org.testsuite.data.TestSuite;

/**
 * Lists the tests on a tree.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestRunnerModel implements TreeModel {
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
	public TestRunnerModel() {
		_testRunner = new ArrayList<TestRunner>();
		_treeModelListener = new Vector<TreeModelListener>();
	}
	
	/**
	 * Save the list of TestRunner
	 */
	public void setListOfTestRunner(List<TestRunner> list) {
		_testRunner = list;
		for (int runner = 0; runner < _testRunner.size(); runner++)
			_testRunner.get(runner).checkFileExists();
		
		fireTreeStructureChanged();
	}

	/**
	 * Adds a TreeModelListener to the list of TreeModelListener.
	 */
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		_treeModelListener.add(l);
	}

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

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return -1;
	}

	@Override
	public Object getRoot() {
		return _testRunner;
	}

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

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}
	
	public void fireTreeStructureChanged() {
		for (int i = 0; i < _treeModelListener.size(); i++)
			_treeModelListener.elementAt(i).treeStructureChanged(
					new TreeModelEvent(this, new Object[] {"TestRunner"}));
	}

}
