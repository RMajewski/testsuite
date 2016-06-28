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

import org.testsuite.checksource.CSConfig;
import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests if the lines are too wide.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestToWideLines implements SourceTest {

	/**
	 * Looking into the source code files according to wide rows.
	 * 
	 * @param list The list of source lines.
	 * 
	 * @return True, if the test was successful. False if an error has occurred
	 * in the line.
	 */
	@Override
	public boolean test(List<SourceLine> list) {
		int wide = CSConfig.getInstance().getLineWidth();
		if (wide < 0)
			wide = 80;
		
		String spaces = new String();
		if (CSConfig.getInstance().getTabSpace() > 0)
			for (int i = 0; i < CSConfig.getInstance().getTabSpace(); i++)
				spaces += " ";
		
		for (int line = 0; line < list.size(); line++) {
			String source = list.get(line).getLine().replaceAll("\t", spaces);
			if (source.length() > wide)
				list.get(line).addMessage(new MessageColor(
						ResourceBundle.getBundle(BUNDLE_FILE)
							.getString("toWideLine").replace("?", 
									String.valueOf(wide)),
						HelperUsedColor.IGNORE));
		}
		
		return false;
	}

}
