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

import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class DlgConfigTestRunner extends DlgConfig {

	/**
	 * Serlisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigTestRunner";

	/**
	 * Initialize the class an create the layout. 
	 * @param owner
	 */
	public DlgConfigTestRunner(JFrame owner) {
		super(owner, BUNDLE_FILE);
		
		setSize(500, 200);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 150, 200};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 
				30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
	}
}
