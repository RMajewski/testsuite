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

package org.testsuite.checksource.tests;

import java.util.List;
import java.util.ResourceBundle;

import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.helper.HelperUsedColor;

/**
 * Scans the source for empty methods.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestEmptyMethod implements SourceTest {
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Initialize the data
	 */
	public TestEmptyMethod() {
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
	}

	/**
	 * Scans the source consecutively after several blank lines.
	 * 
	 * @param list The list of source lines.
	 * 
	 * @return True, if the test was successful. False if an error has occurred
	 * in the line.
	 */
	@Override
	public boolean test(List<SourceLine> list) {
		int beginMethod = -1;
		MessageColor message = new MessageColor(_bundle.getString("emptyMethod"), 
				HelperUsedColor.WARNING);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLine().matches(
					"^\\s*(private|public|protected)[\\w\\s\\(\\)]*\\{\\s*\\}$")) {
				beginMethod = -1;
				list.get(i).addMessage(message);
			} else if (list.get(i).getLine().matches(
					"^\\s*(private|public|protected)[\\s\\w (){]*[\\s]*$")) {
				beginMethod = i;
			} else if ((beginMethod == i - 1) && 
					((list.get(i).getLine().trim().isEmpty()) 
							|| list.get(i).getLine()
							.matches("^\\s*\\/\\/[\\w ]*$")) && 
					((i + 1 < list.size()) && 
							list.get(i + 1).getLine().matches("^\\s*}\\s*$"))) {
				list.get(i - 1).addMessage(message);
				list.get(i).addMessage(message);
				list.get(i + 1).addMessage(message);
			} else if ((beginMethod == i - 1) && 
					list.get(i).getLine().matches("^\\s*}\\s*$")) {
				list.get(i - 1).addMessage(message);
				list.get(i).addMessage(message);
			}
			
			if ((beginMethod > -1) && 
					list.get(i).getLine().matches("^\\s*}\\s*$"))
				beginMethod = -1;
		}
		return true;
	}

}
