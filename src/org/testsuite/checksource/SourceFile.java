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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.testsuite.checksource.html.HtmlNoneExistFile;
import org.testsuite.checksource.tests.SourceTest;
import org.testsuite.helper.HelperUsedColor;

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
	 * Saves the instance of ReadTest
	 */
	private ReadTest _readTest;
	
	/**
	 * Initialize the data
	 */
	public SourceFile() {
		_methods = new ArrayList<CSMethod>();
		_source = new ArrayList<SourceLine>();
		_fileName = null;
		_readTest = new ReadTest();
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
	 * Returns the list of source lines
	 */
	public List<SourceLine> getSourceList() {
		return _source;
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
	
	/**
	 * Return the list of methods
	 * 
	 * @return The list of methods
	 */
	public List<CSMethod> getMethodList() {
		return _methods;
	}
	
	/**
	 * Reads the source file or test file line by line.
	 * 
	 * @param test If the test file can be read?
	 * 
	 * @param testName The name of the test file
	 * 
	 * @return True if the file could be read. False if the file could not be
	 * read.
	 */
	public boolean readFile(boolean test, String testName) {
		if (!test && ((_fileName == null) || _fileName.isEmpty()))
			return false;
		
		if (!test && !new File(_fileName).exists()) {
			HtmlNoneExistFile.getInstance().addNoneExistsFileName(_fileName);
			return false;
		}
		
		if (test && ((testName == null) || testName.isEmpty()))
			return false;
		
		if (test && !new File(testName).exists()) {
			HtmlNoneExistFile.getInstance().addNoneExistsFileName(testName);
			return false;
		}
		
		BufferedReader bf = null;
		try {
			if (!test)
				bf = new BufferedReader(new FileReader(_fileName));
			else
				bf = new BufferedReader(new FileReader(testName));
			
			ReadSource readSource = new ReadSource();
			
			String line;
			int count = 0;
			boolean multicomment = false;
			boolean javadoc = false;
			while ((line = bf.readLine()) != null) {
				count++;
				
				// create Source instance
				SourceLine source = new SourceLine();
				source.setLine(line);
				source.setLineNumber(count);
				
				// Multiline comment?
				if ((line.indexOf("/**") > -1) && 
						(line.indexOf("*/") == -1))
					javadoc = true;
				else if ((line.indexOf("/*") > -1) && (line.indexOf("*/") == -1))
					multicomment = true;
				else if (multicomment && (line.indexOf("*/") > -1)) {
					multicomment = false;
					source.setMultiLineComment(true);
				} else if (javadoc && (line.indexOf("*/") > -1)) {
					javadoc = false;
					source.setJavadoc(true);
				}
				
				if (multicomment)
					source.setMultiLineComment(true);
				
				if (javadoc)
					source.setJavadoc(true);
				
				// add Source to list
				if (!test)
					_source.add(source);
				
				// read source code
				if (!multicomment && !javadoc) {
					line = line.trim();
					if (!test) {
						int methods = _methods.size();
						readSource.read(count, line, _methods);
						if (methods < _methods.size())
							_readTest.addClassName(
									_methods.get(_methods.size() -1)
									.getClassName());
					} else
						_readTest.read(count, line, _methods);
				} else if (line.matches("^[*\\s]*(@deprecated|@Deprecated)" +
						"[\\p{Graph}\\s]*$")) {
					readSource.setDeprecated(true);
				} else if (readSource.isDeprecated() && (line.matches(
						"^[*\\s]*(@deprecated|@Deprecated)" +
						"[\\p{Graph}\\s]*$")))
					readSource.setDeprecated(true);
			}
			
			for (int i = 0; i < _source.size(); i++) {
				if (!test && (readSource.getClassName() != null) && 
						!readSource.getClassName().isEmpty())
					_source.get(i).setClassName(readSource.getClassName());
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
	
	/**
	 * Check the source code
	 */
	public void prepaireSource() {
		for (int i = 0; i < _methods.size(); i++) {
			_source.get(_methods.get(i).getLine() - 1).setBeginMethod(true);
			if (_methods.get(i).callsCount() >  0)
				_source.get(_methods.get(i).getLine() - 1).setLineTested(true);
			else  if (!_methods.get(i).isDeprecated() && 
					!_methods.get(i).isIgnore()) {
				Color color;
				if (_methods.get(i).getModifier().equals("public"))
					color = HelperUsedColor.ERROR;
				else
					color = HelperUsedColor.WARNING;
				_source.get(_methods.get(i).getLine() -1).addMessage(
						new MessageColor(ResourceBundle.getBundle(
								SourceTest.BUNDLE_FILE).getString(
										"sourceMethodNotTested") ,color));
				System.out.println(_source.get(_methods.get(i).getLine() - 1).getClassName());
				System.out.println(_methods.get(i).getClassName());
			}
		}
	}
}
