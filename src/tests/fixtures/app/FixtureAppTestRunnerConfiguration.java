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

package tests.fixtures.app;

/**
 * Implements the fixtures for testing the test runner configuration.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTestRunnerConfiguration extends FixtureApp {
	
	/**
	 * Determines whether the text field for the class name exists.
	 * 
	 * @return In existence the text field for the class name?
	 */
	public boolean haveConfigurationTestRunnerTextFieldClassName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	/**
	 * Return the text from text field class name
	 * 
	 * @return Text from text field class name
	 */
	public String getConfigurationTestRunnerTextClassName() {
		return _tests.getDialogTextField(0).getDisplayedText();
	}
	
	/**
	 * Determines whether the text field for the file extension exists.
	 * 
	 * @return In existence the text field for the file extension?
	 */
	public boolean haveConfigurationTestRunnerTextFieldFileExtension() {
		return _tests.getDialogTextField(1).isEnabled();
	}
	
	/**
	 * Return the text from text field file extension
	 * 
	 * @return Text from text field file extension
	 */
	public String getConfigurationTestRunnerTextFileExtension() {
		return _tests.getDialogTextField(1).getDisplayedText();
	}
	
	/**
	 * Determines whether the text area for the description exists.
	 * 
	 * @return In existence the text area for the description?
	 */
	public boolean haveConfigurationTestRunnerTextAreaDescription() {
		return _tests.getDialogTextArea(0).isEnabled();
	}
	
	/**
	 * Return the text from text area description
	 * 
	 * @return Text from text area description
	 */
	public String getConfigurationTestRunnerTextDescription() {
		return _tests.getDialogTextArea(0).getDisplayedText();
	}
	
	/**
	 * Determines whether the list for the libraries
	 * 
	 * @return In existence the list for the libraries?
	 */
	public boolean haveConfigurationTestRunnerListLibraries() {
		return _tests.getDialogList(0).isEnabled();
	}
	
	public int getConfigurationTestRunnerLibraryCount() {
		return _tests.getDialogList(0).getModel().getSize();
	}
	
	/**
	 * Determines whether the list for the class paths
	 * 
	 * @return In existence the list for the class paths?
	 */
	public boolean haveConfigurationTestRunnerListClasspath() {
		return _tests.getDialogList(1).isEnabled();
	}
	
	/**
	 * Set the text on text field for class name.
	 * 
	 * @param name The new class name
	 */
	public void setConfigurationTestRunnerTextClassName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	/**
	 * Set the text on text field for file extension.
	 * 
	 * @param extension The new file extension
	 */
	public void setConfigurationTestRunnerTextFileExtension(String extension) {
		_tests.getDialogTextField(1).setText(extension);
	}
	
	/**
	 * Set the text on text area for description.
	 * 
	 * @param description The new description
	 */
	public void setConfigurationTestRunnerTextDescription(String description) {
		_tests.getDialogTextArea(0).setText(description);
	}
	
	/**
	 * Return the selected TestRunner class name
	 * 
	 * @return Selected TestRunner class name
	 */
	public String getTestRunnerClassName() {
		return _tests.getTreeSelectedTestRunner().getClass().getName();
	}
	
	/**
	 * Returns the selected TestRunner file extension
	 * 
	 * @return Selected TestRunner file extension
	 */
	public String getTestRunnerExtension() {
		return _tests.getTreeSelectedTestRunner().getFileExtension();
	}
	
	/**
	 * Returns the selected TestRunner description
	 * 
	 * @return Selected TestRunner description
	 */
	public String getTestRunnerDescription() {
		return _tests.getTreeSelectedTestRunner().getDescription();
	}

}
