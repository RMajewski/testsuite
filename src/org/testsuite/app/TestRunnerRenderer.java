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
import org.testsuite.data.TestSuite;

import java.awt.Color;

/**
 * Displays the tests in tree on.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestRunnerRenderer implements TreeCellRenderer {
	
	private JCheckBox _check;
	
	private JLabel _label;
	
	private ResourceBundle _bundle;
	
	public TestRunnerRenderer() {
		_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE);
		
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
			
			if (((org.testsuite.data.Test)value).isExists())
				_check.setEnabled(true);
			else
				_check.setEnabled(false);
			
			_check.setText(((org.testsuite.data.Test)value).getName());
			// FIXME In Test-Klasse hinzufügen, ob Test ausgeführt werden soll oder nicht.
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
		else if ((row == 0) && (value instanceof List<?>) && 
				((List<?>)value).isEmpty()) {
			_label.setText(_bundle.getString("treeNullNode"));
			_label.setBackground(new Color(0xff, 0xcf, 0xcf));
		} else if (value instanceof List<?>)
			_label.setText("TestRunner");
		else {
			_label.setText(value.toString());
			System.out.println(row);
		}
		return _label;
	}

}
