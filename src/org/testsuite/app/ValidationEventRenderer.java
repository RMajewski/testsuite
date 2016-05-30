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
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Prepares the Validation errors and outputs them in the list.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@SuppressWarnings("serial")
public class ValidationEventRenderer extends JLabel 
		implements ListCellRenderer<ValidationEvent> {
	/**
	 * The name of resource bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.ValidationEventRenderer";
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Initialize the class
	 */
	public ValidationEventRenderer() {
		super();
		setOpaque(true);
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
	}

	/**
	 * Configures the output and returns then.
	 * 
	 * @param list List of validation event data.
	 * 
	 * @param ve Validation event data.
	 * 
	 * @param index The cells index.
	 * 
	 * @param isSelected True if the specified cell was selected.
	 * 
	 * @param True if the specified cell has the focus.
	 * 
	 * @return The configured output.
	 */
	@Override
	public Component getListCellRendererComponent(
			JList<? extends ValidationEvent> list, ValidationEvent ve,
			int index, boolean isSelected, boolean hasFocus) {
		if (isSelected)
			setBackground(Colors.backgroundSelection);
		else
			setBackground(list.getBackground());
		setForeground(list.getForeground());
		
		if (ve.getType() == ValidationEvent.TYPE_CONFIG_PATH_SRC)
			setText("Configuration: " + _bundle.getString("configPathSrc"));
		
		else if (ve.getType() == ValidationEvent.TYPE_CONFIG_NO_TEST_RUNNER)
			setText("Configuration: " + _bundle.getString("configNoTestRunner"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_CONFIG_PATH_SRC_NOT_EXISTS)
			setText("Configuration: " + 
				_bundle.getString("configPathSrcNotExists"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_CONFIG_PATH_RESULT_NOT_EXISTS)
			setText("Configuration: " + 
				_bundle.getString("configPathResultNotExists"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_CONFIG_PATH_LIB_NOT_EXISTS)
			setText("Configuration: " + 
				_bundle.getString("configPathLibNotExists"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_CONFIG_CLASSPATH_NOT_EXISTS)
			setText("Configuration: " + 
				_bundle.getString("configClassPathNotExists")
				.replace("?",String.valueOf(ve.getIndexes()[0])));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_RUNNER_DESCRIPTION)
			setText("TestRunner: " + _bundle.getString("testRunnerDescription"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_RUNNER_FILE_EXTENSION)
			setText("TestRunner: " + 
				_bundle.getString("testRunnerFileExtension"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS)
			setText("TestRunner: " + 
				_bundle.getString("testRunnerLibraryNotExists")
				.replace("?", String.valueOf(ve.getIndexes()[0])));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS)
			setText("TestRunner: " + 
				_bundle.getString("testRunnerClassPathNotExists")
				.replace("?", String.valueOf(ve.getIndexes()[0])));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS)
			setText("TestRunner: " + 
				_bundle.getString("testRunnerClassPathNotExists"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_RUNNER_NO_TEST_SUITE)
			setText("TestRunner: " + 
				_bundle.getString("testRunnerNoTestSuite"));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_SUITE_NAME)
			setText("TestSuite: " + _bundle.getString("testSuiteName"));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_SUITE_PACKAGE)
			setText("TestSuite: " + _bundle.getString("testSuitePackage"));
		
		else if (ve.getType() == 
				ValidationEvent.TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS)
			setText("TestSuite: " + 
				_bundle.getString("testSuitePackageNotExists"));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_SUITE_NO_TEST)
			setText("TestSuite: " + _bundle.getString("testSuiteNoTest"));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_NAME)
			setText("Test: " + _bundle.getString("testName"));
		
		else if (ve.getType() == ValidationEvent.TYPE_TEST_NOT_EXISTS)
			setText("Test: " + _bundle.getString("testNotExists"));
		
		else
			setText(_bundle.getString("otherError"));
		
		return this;
	}

}
