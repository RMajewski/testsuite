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

package org.testsuite.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Implements methods of handling files.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HelperFile {
	/**
	 * Lists all source code files.
	 * 
	 * @param path The path for source files.
	 * 
	 * @param extension The extension of source files.
	 * 
	 * @return List with all source code files.
	 */
	public static List<File> getSourceFiles(String path, String extension) {
		List<File> ret = new ArrayList<File>();
		Stack<File> dirs = new Stack<File>();
		File start = new File(path);
		Pattern p = Pattern.compile(extension, Pattern.CASE_INSENSITIVE);
		
		if (start.isDirectory())
			dirs.push(start);
		
		while (dirs.size() > 0) {
			for (File file : dirs.pop().listFiles())
				if (file.isDirectory())
					dirs.push(file);
				else
					if (p.matcher(file.getName()).matches())
						ret.add(file);
		}
		
		return ret;
	}

}
