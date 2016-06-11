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
 * Scans the source consecutively after several blank lines.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestEmptyLines implements SourceTest {
	/**
	 * Saves the instance of the resource bundle.
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Initialize this class.
	 */
	public TestEmptyLines() {
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
		int last = -1;
		
		for (int i = 0; i < list.size(); i++) {
			boolean empty = list.get(i).getLine().matches("^[\\s]*$");
			
			if ((last == i - 1) && (list.get(i).getLine().isEmpty() || empty)) {
				list.get(i - 1).addMessage(new MessageColor(
						_bundle.getString("emptylines"), 
						HelperUsedColor.WARNING));
				list.get(i).addMessage(new MessageColor(
						_bundle.getString("emptylines"), 
						HelperUsedColor.WARNING));
			}
			
			if (list.get(i).getLine().isEmpty() || empty)
				last = i;
		}
		return true;
	}

}
