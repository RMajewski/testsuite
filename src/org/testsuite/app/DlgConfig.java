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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * From this class all configuration dialogs are derived.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class DlgConfig extends JDialog implements ActionListener {

	/**
	 * Serilisation ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Saves the action command for accept button
	 */
	protected static final String BTN_ACCEPT = "DlgConfig.accept";
	
	/**
	 * Saves the action command for cancel button
	 */
	protected static final String BTN_CANCEL = "DlgConfig.cancel";
	
	/**
	 * Specifies that ended with accept.
	 */
	public static final int EXIT_ACCEPT = 1;
	
	/**
	 * Specifies that ended with cancel.
	 */
	public static final int EXIT_CANCEL = 2;
	
	/**
	 * Saves the Exit status
	 */
	protected int _exitStatus;
	
	/**
	 * Saves the instance of resource bundle
	 */
	protected ResourceBundle _bundle;

	/**
	 * Initialize the data
	 * 
	 * @param bundleName The name of the resource bundle
	 */
	public DlgConfig(JFrame owner, String bundleName) {
		super(owner);
		
		_bundle = ResourceBundle.getBundle(bundleName);
		_exitStatus = -1;

		setTitle(_bundle.getString("dialog_title"));
	}
	
	/**
	 * Returns the exit status
	 * 
	 * @return Exit status of this dialog
	 */
	public int getExitStatus() {
		return _exitStatus;
	}

	/**
	 * Responds to pressing the buttons to accept or cancel.
	 * 
	 * @param e Event data
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(BTN_CANCEL)) {
			_exitStatus = EXIT_CANCEL;
			setVisible(false);
		}
		
		else if(e.getActionCommand().equals(BTN_ACCEPT)) {
			_exitStatus = EXIT_ACCEPT;
			setVisible(false);
		}
	}
}
