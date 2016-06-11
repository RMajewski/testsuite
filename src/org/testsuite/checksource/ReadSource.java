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

package org.testsuite.checksource;

import java.util.List;

/**
 * Reads the source code and determines the individual declarations.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class ReadSource implements Read {
	/**
	 * Saves the number of blocks
	 */
	private int _blocks;
	
	/**
	 * Saves the class name
	 */
	private String _className;
	
	/**
	 * Saves if the method started.
	 */
	private String _methodStarted;
	
	/**
	 * Initialize the data
	 */
	public ReadSource() {
		_blocks = 0;
		_className = null;
		_methodStarted = null;
	}

	/**
	 * Scans the source code for methods, attributes and constants.
	 * 
	 * @param line The actual line of source code.
	 * 
	 * @param list The list of methods
	 */
	@Override
	public void read(int lineNumber, String line, List<CSMethod> list) {
		int startIndex = 0;
		int endIndex = 0;
		
		while (startIndex < line.length()) {
			if (line.indexOf(";", startIndex) > -1)
				endIndex = line.indexOf(";", startIndex);
			else if (line.indexOf("{", startIndex) > -1)
				endIndex = line.indexOf("{", startIndex);
			else if (line.indexOf("}", startIndex) > -1)
				endIndex = line.indexOf("}", startIndex);
			else if (line.indexOf("*/", startIndex) > -1)
				endIndex = line.indexOf("*/", startIndex);
			else
				endIndex = line.length() - 1;

			String[] read = line.substring(startIndex, endIndex).split(" ");
			
			if (line.matches("^[\\s]*(private|protected|public)[\\w\\s<>,"+ 
					"\\[\\]]*\\([\\w\\ſ, <>\\[\\]]*\\)[\\p{Graph}\\s]*\\{$"))
				readMethod(lineNumber, read, list);
			else if (line.matches("^[\\s]*(private|protected|public)" + 
					"[\\w\\s<>,]*\\([\\w\\ſ, <>\\[\\]]*\\)[\\p{Graph}\\s]*$") ||
					line.matches("^[\\s]*(private|protected|public)" + 
					"[\\w\\s<>,]*\\([\\w\\ſ, <>\\[\\]]*\\)*$") ||
					line.matches("^[\\s]*(private|protected|public)" + 
					"[\\w\\s<>,]*\\([\\w\\ſ, <>\\[\\]]*$")) 
				_methodStarted = line;
			else if ((_methodStarted != null) && (
					( line.matches("^[\\w\\ſ, <>\\[\\]]*\\)[\\s\\w]*\\{$")) || 
					line.matches("^\\s*[\\w\\s<>,\\[\\]]*\\{$") )) {
				_methodStarted += line;
				System.out.println(_methodStarted);
				readMethod(lineNumber - 1, _methodStarted.split(" "), list);
				_methodStarted = null;
			} else if (line.matches("^[\\s]*(private|protected|public)[\\s]?" +
					"(class)[\\s\\w]*\\{$"))
				_className = read[read.length - 1];
			
				// TODO Calls
			
			if (line.indexOf("{") > -1)
				_blocks++;
			else if (line.indexOf("}") > -1) {
				_blocks--;
				if ((_blocks == 1) && !list.isEmpty()) {
					CSMethod method = list.get(list.size() -1);
					method.setLastLineNumber(lineNumber);
				}
			}
			
			startIndex = endIndex + 1;
		}
	}

	private void readMethod(int lineNumber, String[] read, List<CSMethod> list) {
		CSMethod method = new CSMethod();
		if ((_className != null) && !_className.isEmpty())
			method.setClassName(_className);
		method.setModifier(read[0]);
		method.setLine(lineNumber);

		int startAttribut = -1;
		String artType = new String();
		String artName = new String();
		boolean type = false;
		
		for (int i = 1; i < read.length; i++) {
			if (read[i].equals(",") || read[i].equals("{"))
				continue;
			
			if (read[i].indexOf("(") > -1) {
				String name = read[i].substring(0,  read[i].indexOf("("));
				method.setName(name);
				method.setType(read[i - 1]);
				if (read[i].indexOf(")") == -1)
					startAttribut = i;
			}
			
			if (startAttribut > -1) {
				if (startAttribut == i) {
					artType = read[startAttribut]
							.substring(read[startAttribut].indexOf("(") + 1);
					type = false;
				} else if (!type) {
					type = true;
					int endIndex = 0;
					if (read[i].indexOf(",") > -1)
						endIndex = read[i].indexOf(",");
					else if (read[i].indexOf(")") > -1)
						endIndex = read[i].indexOf(")");
					artName = read[i].substring(0, endIndex);
					method.addParameter(new CSParameter(artType, artName));
				} else {
					artType = read[i];
					type = false;
				}
			}
		}
		
		list.add(method);
	}
}
