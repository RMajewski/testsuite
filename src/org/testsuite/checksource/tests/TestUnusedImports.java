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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;

/**
 * Checks if in the source files are unused import.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestUnusedImports implements SourceTest {

	@Override
	public boolean test(List<SourceLine> list) {
		HashMap<Integer, String> imports = new HashMap<Integer, String>();
		
		for (int source = 0; source < list.size(); source++) {
			String line = list.get(source).getLine();
			if (line.matches("^import[\\s\\w.;]*$")) {
				String[] imp = line.substring(line.indexOf(" ") + 1, 
						line.length() - 1).split("\\.");
				imports.put(Integer.valueOf(list.get(source).getLineNumber()), 
						new String(imp[imp.length - 1]));
			} else {
				List<Integer> del = new ArrayList<Integer>();
				for (Map.Entry<Integer, String> entry : imports.entrySet()) {
					String imp = entry.getValue();
					if ((imp != null) && (line.indexOf(imp) > -1)) {
						del.add(entry.getKey());
					}
				}
				for (int i = 0; i < del.size(); i++)
					imports.remove(del.get(i));
			}
		}
		
		for (Integer i : imports.keySet()) {
			list.get(i - 1).addMessage(new MessageColor(
					ResourceBundle.getBundle(BUNDLE_FILE).getString(
							"unusedImports"), COLOR_WARNING));
		}
		
		return true;
	}

}
