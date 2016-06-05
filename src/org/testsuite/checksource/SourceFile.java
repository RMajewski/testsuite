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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class SourceFile {
	/**
	 * Saves the methods, attributes and constants.
	 */
	private List<CSMethod> _methods;
	
	/**
	 * Saves the source code.
	 */
	private List<SourceLine> _source;
	
	/**
	 * Saves the file name
	 */
	private String _fileName;
	
	/**
	 * Initialize the data
	 */
	public SourceFile() {
		_methods = new ArrayList<CSMethod>();
		_source = new ArrayList<SourceLine>();
		_fileName = null;
	}
	
	/**
	 * Returns the file name
	 * 
	 * @return The file name
	 */
	public String getFileName() {
		return _fileName;
	}
	
	/**
	 * Sets the file name
	 * 
	 * @param name The new file name.
	 */
	public void setFileName(String name) {
		_fileName = name;
	}
	
	/**
	 * Returns the number of lines of source code.
	 * 
	 * @return The number of lines of source code.
	 */
	public int sourceCount() {
		return _source.size();
	}
	
	/**
	 * Add a line of source code to the list of lines.
	 * 
	 * @param line The new line of source code
	 */
	public void addSourceLine(String line) {
		SourceLine source = new SourceLine();
		source.setLine(line);
		_source.add(source);
	}
	
	/**
	 * Returns the specified line of source code.
	 * 
	 * @param index The index of line.
	 * 
	 * @return The specified line of source code.
	 */
	public String getSourceLineString(int index) {
		return _source.get(index).getLine();
	}
	
	/**
	 * Returns the specified line of source code.
	 * 
	 *  @param index The index of line.
	 *  
	 *  @return The specified line of source code.
	 */
	public SourceLine getSourceLine(int index) {
		return _source.get(index);
	}
	
	/**
	 * Return the number of methods.
	 * 
	 * @return The number of methods.
	 */
	public int methodsCount() {
		return _methods.size();
	}
	
	/**
	 * Added a new method to the list of methods.
	 * 
	 * @param method The new method.
	 */
	public void addMethod(CSMethod method) {
		_methods.add(method);
	}
	
	/**
	 * Returns the specified method
	 * 
	 * @param index The index of method.
	 * 
	 * @return The specified method
	 */
	public CSMethod getMethod(int index) {
		return _methods.get(index);
	}
	
	public boolean readFile() {
		if ((_fileName == null) || _fileName.isEmpty())
			return false;
		
		if (!new File(_fileName).exists())
			return false;
		
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(_fileName));
			ReadSource read = new ReadSource();
			
			String line;
			int count = 0;
			boolean multicomment = false;
			while ((line = bf.readLine()) != null) {
				count++;
				// create Source instance
				SourceLine source = new SourceLine();
				source.setLine(line);
				source.setLineNumber(count);
				
				// Multiline comment?
				if ((line.indexOf("/*") > -1) && (line.indexOf("*/") == -1))
					multicomment = true;
				else if (multicomment && (line.indexOf("*/") > -1))
					multicomment = false;
				
				source.setMultiLineComment(multicomment);
				
				// add Source to list
				_source.add(source);
				
				// read source code
				if (!multicomment) {
					read.read(count, line, _methods);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (bf != null)
					bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
