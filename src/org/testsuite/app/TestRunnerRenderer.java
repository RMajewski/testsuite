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

import java.awt.Component;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.testsuite.core.FitTestRunner;
import org.testsuite.core.JemmyTestRunner;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.TestSuite;

import java.awt.Color;

/**
 * Displays the tests in tree on.
 * 
 * In version 0.2 takes into account whether the initialized general
 * configuration.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class TestRunnerRenderer implements TreeCellRenderer {
	
	/**
	 * Saves the instance of check box for execute test
	 */
	private JCheckBox _check;
	
	/**
	 * Saves the instance of label for generated output 
	 */
	private JLabel _label;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Saves the general configuration
	 */
	private Config _config;
	
	/**
	 * Initialize the renderer
	 * 
	 * @param config General configuration
	 */
	
	public TestRunnerRenderer(Config config) {
		_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE);
		
		_config = config;
		
		_check = new JCheckBox();
		_check.setOpaque(true);
		
		_label = new JLabel();
		_label.setOpaque(true);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		if (value instanceof org.testsuite.data.Test) {
			if (selected)
				_check.setBackground(new Color(0xcf, 0xff, 0xcf));
			else
				_check.setBackground(tree.getBackground());
			
			_check.setEnabled(((org.testsuite.data.Test)value).isExists());
			_check.setSelected(((org.testsuite.data.Test)value).isExecuted());
			
			_check.setText(((org.testsuite.data.Test)value).getName());
			return _check;
		}
		
		if (selected)
			_label.setBackground(new Color(0xcf, 0xff, 0xcf));
		else
			_label.setBackground(tree.getBackground());
		
		if (value instanceof TestSuite)
			_label.setText(((TestSuite)value).getName());
		
		else if (value instanceof JunitTestRunner)
			_label.setText("JunitTestRunner");
		
		else if (value instanceof JemmyTestRunner)
			_label.setText("JemmyTestRunner");
		
		else if (value instanceof FitTestRunner)
			_label.setText("FitTestRunner");
		
		else if (row == 0) {
			if ((value instanceof List<?>) && ((List<?>)value).isEmpty() &&
					_config.isEmpty()) {
				_label.setText(_bundle.getString("tree_null_node"));
				_label.setBackground(new Color(0xff, 0xcf, 0xcf));
			} else if ((value instanceof List<?>) && ((List<?>)value).isEmpty() &&
					!_config.isEmpty()) {
				_label.setText(_bundle.getString("tree_insert_test_runner"));
			} else if (value instanceof List<?>) {
				_label.setText(_bundle.getString("tree_configuration"));
			}
		} 
		
		else {
			_label.setText(value.toString());
		}
		return _label;
	}

}
