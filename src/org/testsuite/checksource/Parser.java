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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the source code
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Parser {
	/**
	 * Parse the source code
	 * 
	 * @param methods The methods of the class
	 * 
	 * @param lines Sources lines from source file
	 */
	public static void parse(List<CSMethod> methods, List<SourceLine> lines) {
		for (int method = 0; method < methods.size(); method++) {
			if (!methods.get(method).isIgnore() && 
					(methods.get(method).callsCount() > 0)) {
				int next = -1;
				for (int line = methods.get(method).getLine(); 
						line < methods.get(method).getLastLineNumber(); line++) {
					String source = new String();
					
					if ((next > -1) && (line <= next))
						continue;
					
					if (lines.get(line).getLine().indexOf("//") > -1)
						source = lines.get(line).getLine().substring(
								lines.get(line).getLine().indexOf("//"));
					else
						source = lines.get(line).getLine();
					
					Matcher matcher = 
							Pattern.compile("\\b").matcher(source);
					boolean tested = true;
					int start = 0;
					while (matcher.find()) {
						int end = matcher.end();
						String word = lines.get(line).getLine().substring(start, 
								end);
						start = end;
						
						int key = searchKeywords(word, methods.get(method),
								lines, line);
						if (key > 0) {
							line += key - 1;
							tested = false;
							break;
						}
					}
					
					if (tested)
						lines.get(line).setLineTested(true);
				}
			}
		}
	}
	
	/**
	 * Checks whether the specified word is a key word.
	 * 
	 * @param word Word to be checked on a keyword
	 * 
	 * @param method The actually method.
	 * 
	 * @param lines All source lines of the source file.
	 * 
	 * @param number The number of line
	 * 
	 * @return A keyword that row is returned, where it continues. Is it not a
	 * keyword, so -1 is returned.
	 */
	private static int searchKeywords(String word, CSMethod method,
			List<SourceLine> lines, int number) {
		int end = method.getLastLineNumber();
		
		System.out.println(word);
		
		switch(word) {
			case "assert":
				return end;
				
			case "break":
				return end;
				
			case "case":
				return end;
				
			case "catch":
				return end;
				
			case "continue":
				return end;
				
			case "default":
				return end;
				
			case "do":
				return end;
				
			case "else":
				return end;
				
			case "finally":
				return end;
				
			case "for":
				return end;
				
			case "goto":
				return end;
				
			case "if":
				return end;
				
			case "instanceof":
				return -1;
				
			case "new":
				return -1;
				
			case "return":
				lines.get(number).setLineTested(true);
				if (lines.get(number + 1).getLine().trim().equals("}"))
					lines.get(number + 1).setLineTested(true);
				return end;
				
			case "switch":
				return end;
				
			case "throw":
				lines.get(number).setLineTested(true);
				return end;
			
			case "try":
				return 1;
				
			case "while":
				return end;
		}
		
		// Standard return value
		return -1;
	}
}
