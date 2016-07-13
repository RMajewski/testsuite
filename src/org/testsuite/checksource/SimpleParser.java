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

import org.testsuite.checksource.annotation.CheckSource;

/**
 * Parser for the source code
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class SimpleParser implements Parser {
	/**
	 * Saves the list source lines
	 */
	private List<SourceLine> _source;
	
	/**
	 * Saves the list of methods
	 */
	private List<CSMethod> _methods;
	
	/**
	 * Saves the number of line of end loop
	 */
	private int _endLoop = -1;
	
	/**
	 * Parse the source code
	 * 
	 * @param methods The methods of the class
	 * 
	 * @param lines Sources lines from source file
	 */
	@Override
	public void parse() {
		for (int method = 0; method < _methods.size(); method++) {
			if (!_methods.get(method).isIgnore() && 
					_methods.get(method).isTested()) {
				int next = -1;
				for (int line = _methods.get(method).getLine(); 
						line < _methods.get(method).getLastLineNumber(); line++) {
					String source = new String();
					
					if ((next > -1) && (line <= next))
						continue;
					
					if (_source.get(line).isJavadoc() || 
							_source.get(line).isMultiLineComment())
						continue;
					
					if (_source.get(line).getLine().indexOf("//") > -1)
						source = _source.get(line).getLine().substring(
								_source.get(line).getLine().indexOf("//"));
					else
						source = _source.get(line).getLine();
					
					Matcher matcher = 
							Pattern.compile("\\b").matcher(source);
					boolean tested = true;
					int start = 0;
					while (matcher.find()) {
						int end = matcher.end();
						String word = _source.get(line).getLine().substring(start, 
								end);
						start = end;
						
						int key = searchKeywords(word, _methods.get(method),
								_source, line);
						if (key > 0) {
							line = key;
							tested = false;
							break;
						}
					}
					
					if (tested)
						_source.get(line).setLineTested(true);
				}
			}
		}
	}

	@Override
	public void setSources(List<SourceLine> sources) {
		_source = sources;
	}
	
	@Override
	public void setMethods(List<CSMethod> methods) {
		_methods = methods;
	}

	@CheckSource(ignored=true)
	@Override
	public void setSourceFileName(String name) {
	}
	
	@CheckSource(ignored=true)
	@Override
	public void setTestFileName(String name) {
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
	private int searchKeywords(String word, CSMethod method,
			List<SourceLine> lines, int number) {
		
		int end = method.getLastLineNumber();
		int endLoop = -1;
		
		if (lines.get(number).getLine().trim().endsWith("{")) {
			int level = 1;
			for (int line = number + 1; line < method.getLastLineNumber(); 
					line++) {
				if (lines.get(line).getLine().trim().endsWith("{"))
					level++;
				else if (lines.get(line).getLine().trim().startsWith("}"))
					level--;
				if (level == 0) {
					endLoop = line;
					break;
				}
			}
		}
		
		switch(word) {
			case "assert":
				lines.get(number).setLineTested(true);
				return number + 1;
				
			case "break":
				lines.get(number).setLineTested(true);
				if (_endLoop > -1) {
					int ret = _endLoop;
					_endLoop = -1;
					return ret + 1;
				}
				return end;
				
			case "case":
				lines.get(number).setLineTested(true);
				return number +1;
				
			case "catch":
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
				
			case "continue":
				lines.get(number).setLineTested(true);
				if (_endLoop > -1)
					return _endLoop;
				return end;
				
			case "default":
				lines.get(number).setLineTested(true);
				return number + 1;
				
			case "do":
				lines.get(number).setLineTested(true);
				if (endLoop > number)
					_endLoop = endLoop;
				else
					return -1;
				return number + 1;
				
			case "else":
				if (lines.get(number).getLine().indexOf("if") == -1) {
					lines.get(number).setLineTested(true);
					if (endLoop > -1)
						_endLoop = endLoop;
					else {
						lines.get(number + 1).setLineTested(true);
						return number + 2;
					}
					return number + 1; 
				}
				return -1;
				
			case "finally":
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
				
			case "for":
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
				
			case "if":
				lines.get(number).setLineTested(true);
//				if (endLoop > -1)
//					_endLoop = endLoop;
				return number + 1;
				
			case "return":
				lines.get(number).setLineTested(true);
				if (lines.get(number + 1).getLine().trim().equals("}"))
					lines.get(number + 1).setLineTested(true);
				if (_endLoop > -1)
					_endLoop = -1;
				return end;
				
			case "switch":
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
				
			case "throw":
				lines.get(number).setLineTested(true);
				return end;
			
			case "try":
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
				
			case "while":
				if (_endLoop == number) {
					_endLoop = -1;
					lines.get(number).setLineTested(true);
					return number + 1;
				}
				lines.get(number).setLineTested(true);
				if (endLoop > -1)
					_endLoop = endLoop;
				return number + 1;
		}
		
		// Standard return value
		return -10;
	}
	
	@Override
	public void prepaireSource() {
	}
}
