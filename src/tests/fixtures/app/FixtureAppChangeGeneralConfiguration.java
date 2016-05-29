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

import java.text.ParseException;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import org.testsuite.app.DlgConfigGeneral;

/**
 * Implements the fixtures for testing the general configuration.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppChangeGeneralConfiguration extends FixtureApp {
	
	/**
	 * Determines whether the text field for the library path exists.
	 * 
	 * @return In existence the text field for the library path?
	 */
	public boolean haveTextFieldPathLibrary() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	/**
	 * Determines whether the text field for the result path exists.
	 * 
	 * @return In existence the text field for the result path?
	 */
	public boolean haveTextFieldPathResult() {
		return _tests.getDialogTextField(2).isEnabled();
	}
	
	/**
	 * Determines whether the text field for the source path exists.
	 * 
	 * @return In existence the text field for the source path?
	 */
	public boolean haveTextFieldPathSrc() {
		return _tests.getDialogTextField(1).isEnabled();
	}
	
	/**
	 * Determines whether the text field for the maximum test duration exists.
	 * 
	 * @return In existence the text field for the maximum test duration?
	 */
	public boolean haveTextFieldMaxDuration() {
		return _tests.getDialogTextField(3).isEnabled();
	}
	
	/**
	 * Determines whether the check box for the create html output exists.
	 * 
	 * @return In existence the check box for the create html output?
	 */
	public boolean haveCheckButtonHtmlCreate() {
		return _tests.getDialogCheckBox(0).isEnabled();
	}
	
	/**
	 * Determines whether the list for the system properties exists.
	 * 
	 * @return In existence the list for the system properties?
	 */
	public boolean haveListSystemProperties() {
		return _tests.getDialogList(0).isEnabled();
	}
	
	/**
	 * Determines whether the list for class paths exists.
	 * 
	 * @return In existence the list for the class paths?
	 */
	public boolean haveListClassPaths() {
		return _tests.getDialogList(1).isEnabled();
	}
	
	/**
	 * Set the text on text field for library path.
	 * 
	 * @param path The new library path
	 */
	public void setPathLibrary(String path) {
		_tests.getDialogTextField(0).setText(path);
	}
	
	/**
	 * Set the text on text field for result path.
	 * 
	 * @param path The new result path
	 */
	public void setPathResult(String path) {
		_tests.getDialogTextField(2).setText(path);
	}
	
	/**
	 * Set the text on text field for source path.
	 * 
	 * @param path The new source path
	 */
	public void setPathSrc(String path) {
		_tests.getDialogTextField(1).setText(path);
	}
	
	/**
	 * Set the text on text field for maximum duration.
	 * 
	 * @param str The new maximum duration
	 */
	public void setMaxDuration(String str) throws ParseException {
		_tests.getDialogTextField(3).setText(str);
		((JFormattedTextField)_tests.getDialogTextField(3).getSource()).commitEdit();
	}
	
	/**
	 * Check the check box for generate maximum.
	 * 
	 * @param bln Checked the check box?
	 */
	public void setHtmlCreate(boolean bln) {
		_tests.getDialogCheckBox(0).setSelected(bln);
	}
	
	/**
	 * Insert a property to the list of system properties.
	 * 
	 * @param str Property which inserted in the list.
	 */
	public void addSystemProperty(String str) {
		_tests.openConfigPopup(0);
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_property_title"));
		_tests.getDialog2TextField(0).setText(str);
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Insert a class path to the list of class paths.
	 * 
	 * @param str Class path which inserted in the list.
	 */
	public void addClassPath(String str) {
		_tests.openConfigPopup(1);
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_classpath_title"));
		_tests.getDialog2TextField(0).setText(str);
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Returns the library path from general configuration
	 * 
	 * @return Library path from general configuration
	 */
	public String getConfigurationPathLibrary() {
		return _tests.getGeneralConfiguration().getPathLibrary();
	}
	
	/**
	 * Returns the result path from general configuration
	 * 
	 * @return Result path from general configuration
	 */
	public String getConfigurationPathResult() {
		return _tests.getGeneralConfiguration().getPathResult();
	}
	
	/**
	 * Return the source path from general configuration
	 * 
	 * @return Source path from general configuration
	 */
	public String getConfigurationPathSrc() {
		return _tests.getGeneralConfiguration().getPathSrc();
	}
	
	/**
	 * Return the maximum duration of tests
	 * 
	 * @return The maximum duration
	 */
	 public long getMaxDuration() {
		 return _tests.getGeneralConfiguration().getMaxDuration();
	 }
	 
	 /**
	  * Return is checked the check box for generate HTML output.
	  * 
	  * @return Is checked the check box?
	  */
	 public boolean getHtmlCreate() {
		 return _tests.getGeneralConfiguration().isCreateHtml();
	 }
		
	/**
	 * Gets the entry 0 of the list with system properties
	 * 
	 * @return Entry 0 of the list with system properties
	 */
	 public String getSystemPropertyItem0() {
		 if (_tests.getGeneralConfiguration().propertyCount() > 0)
			 return _tests.getGeneralConfiguration().getProperty(0);
		 
		 return new String();
	 }
	 
	 /**
	  * Gets the entry 0 of the list with class paths.
	  * 
	  * @return Entry 0 of the list with class paths.
	  */
	 public String getClassPathItem0() {
		 if (_tests.getGeneralConfiguration().classPathCount() > 0)
			 return _tests.getGeneralConfiguration().getClassPath(0);
		 
		 return new String();
	 }
}
