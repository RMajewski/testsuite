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
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * 
	 * @deprecated Is no longer needed.
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
		
		// Read source code
		if (!test) {
			BufferedReader bf = null;
			try {
//				if (!test)
					bf = new BufferedReader(new FileReader(_fileName));
				/*else
					bf = new BufferedReader(new FileReader(testName));*/
				
//				ReadSource readSource = new ReadSource();
				
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
//					if (!test)
						_source.add(source);
					
					// read source code
					/*if (!multicomment && !javadoc) {
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
						readSource.setDeprecated(true);*/
				}
				
//				for (int i = 0; i < _source.size(); i++) {
//					if (!test && (readSource.getClassName() != null) && 
//							!readSource.getClassName().isEmpty())
//						_source.get(i).setClassName(readSource.getClassName());
//				}
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
		}
		
		// Methods
		Class<?> c;
		String file;
		if (test)
			file = testName;
		else
			file = _fileName;
		
		file = file.replaceAll(File.separator, ".");
		String className = file.substring(file.indexOf(".") + 1,
				file.indexOf(".java"));
		try {
			c = getClass().getClassLoader().loadClass(className);
			
			// Constructors
			readMethodArray(c.getDeclaredConstructors(), className);
			
			// Methods
			readMethodArray(c.getDeclaredMethods(), className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Returns
		return true;
	}
	
	private void readMethodArray(Member[] methods, String className) {
		for (int i = 0; i < methods.length; i++) {
			if (className.startsWith("test") ||
					!methods[i].getDeclaringClass().getName().equals(className))
				continue;
			
			CSMethod method = new CSMethod();
			method.setName(methods[i].getName());
			method.setClassName(methods[i].getDeclaringClass().getName());
			method.setModifier(Modifier.toString(methods[i].getModifiers()));
			
			if (methods[i] instanceof Method) {
				method.setType(((Method)methods[i]).getReturnType().getName());
				method.addParameterList(readParameters(
						((Method)methods[i]).getParameterTypes()));
				if (isDeprecatedAnnontation(
						((Method)methods[i]).getAnnotations()))
					method.setDeprecated(true);
			} else if (methods[i] instanceof Constructor) {
				method.setName(methods[i].getName().substring(
						methods[i].getName().lastIndexOf(".") + 1));
				method.addParameterList(readParameters(
						((Constructor<?>)methods[i]).getParameterTypes()));
				if (isDeprecatedAnnontation(
						((Constructor<?>)methods[i]).getAnnotations()))
					method.setDeprecated(true);
			}
			
			method.setLine(detectFirstLineNumber(method));
			method.setLastLineNumber(detectLastLineNumber(method));
			method.setBreakpoint(detectBreakpoint(method));
			
			if (!method.isDeprecated() && 
					isDeprecatedJavadoc(method.getLine())) {
				method.setDeprecated(true);
			}
			
			_methods.add(method);
		}
	}

	private boolean isDeprecatedJavadoc(int line) {
		int i = line - 2;
		while ((i > 0) && _source.get(i).isJavadoc()) {
			if (_source.get(i).getLine().indexOf("@deprecated") > -1) {
				return true;
			}
			
			i--;
		}
		
		return false;
	}

	private int detectBreakpoint(CSMethod method) {
		if ((method.getClassName().startsWith("test")) || 
				(method.getLine() == -1) || (method.getLastLineNumber() == -1))
			return -1;
		
		for (int line = (method.getLine() - 1); 
				line < (method.getLastLineNumber() - 1); line++) {
			if (_source.get(line).getLine().trim().endsWith("{"))
				return _source.get(line).getLineNumber() + 1;
		}
		
		return -1;
	}
	
	private int detectLastLineNumber(CSMethod method) {
		if ((method.getClassName().startsWith("test")) || 
				(method.getLine() == -1))
			return -1;
		
		int blocks = 0;
		
		for (int line = (method.getLine() - 1); line < _source.size(); line ++) {
			if (_source.get(line).getLine().trim().endsWith("{"))
				blocks++;
			else if (_source.get(line).getLine().trim().endsWith("}"))
				blocks--;
			
			if ((line > (method.getLine() - 1)) && (blocks == 0))
					return line + 1;
		}
		
		return -1;
	}

	private int detectFirstLineNumber(CSMethod method) {
		if (method.getClassName().startsWith("test"))
			return -1;

		for (int i = 0; i < _source.size(); i++) {
			StringBuilder matches = new StringBuilder();
			matches.append("^\\s*");
			matches.append(method.getModifier());
			matches.append("\\s*");
			
			if (!method.getType().isEmpty()) {
				matches.append("(");
				matches.append(createType(method.getType()));
				matches.append(")\\s*");
			}
			
			matches.append(method.getName());
			matches.append("\\s*\\(");
			
			for (int j = 0; j < method.parametersCount(); j++) {
				if (j > 0)
					matches.append(",\\s*");
				matches.append("(");
				matches.append(createType(method.getParameter(j).getType()));
				matches.append(")\\s*[\\w]*");
			}
			
			matches.append("\\)\\s*\\{$");
			
			String line = _source.get(i).getLine();
			
			if (((line.indexOf("public") > -1) || 
				 (line.indexOf("private") > -1) ||
				 (line.indexOf("protected") > -1)) && 
				(line.indexOf("{") == -1)) {
				for (int j = i + 1; j < _source.size(); j++) {
					line += " " + _source.get(j).getLine().trim();
					if ((line.indexOf("{") > -1) || (line.indexOf(";") > -1)) {
						break;
					}
				}
			}
			
			if (_source.get(i).getLine().matches(matches.toString())) {
				return _source.get(i).getLineNumber();
			}
		}
			
		return -1;
	}
	
	private String createType(String type) {
		String t1 = type;
		if (t1.startsWith("[")) {
			if (t1.indexOf("[B") > -1)
				t1 = "byte";
			else if (t1.indexOf("[C") > -1)
				t1 = "char";
			else if (t1.indexOf("[D") > -1)
				t1 = "double";
			else if (t1.indexOf("[F") > -1)
				t1 = "float";
			else if (t1.indexOf("[I") > -1)
				t1 = "int";
			else if (t1.indexOf("[J") > -1)
				t1 = "long";
			else if (t1.indexOf("[S") > -1)
				t1 = "short";
			else if (t1.indexOf("[Z") > -1)
				t1 = "boolean";
			else if (t1.indexOf("[L") > -1)
				t1 = t1.substring(2, t1.indexOf(";"));
			
			for (int i = 0; i < (type.lastIndexOf("[") + 1); i++)
				t1 += "\\[\\]";
		} else if (t1.indexOf("List") > -1) {
			t1 += "[\\w<>]*";
		}
		
		String t2 = new String();
		if (t1.lastIndexOf(".") > -1)
			t2 = "|" + t1.substring(t1.lastIndexOf(".") + 1);
		
		return t1 + t2;
	}

	private List<CSParameter> readParameters(Class<?>[] parameters) {
		List<CSParameter> ret = new ArrayList<CSParameter>();
		
		for (int i = 0; i < parameters.length; i++)
			ret.add(new CSParameter(parameters[i].getName()));
		
		return ret;
	}
	
	
	private boolean isDeprecatedAnnontation(Annotation[] annotations) {
		for (int i = 0; i < annotations.length; i++)
			if (annotations[i].equals("@java.lang.Deprecated()")) {
				return true;
			}
		
		return false;
	}
	
	/**
	 * Check the source code
	 */
	public void prepaireSource() {
		for (int i = 0; i < _methods.size(); i++) {
			if (_methods.get(i).getLine() == -1)
				continue;
			
			_source.get(_methods.get(i).getLine() - 1).setBeginMethod(true);
			_methods.get(i).setIsTested(methodIsTested(_methods.get(i)));
//			if (_methods.get(i).callsCount() >  0)
//				_source.get(_methods.get(i).getLine() - 1).setLineTested(true);
			if (!_methods.get(i).isDeprecated() && 
					!_methods.get(i).isIgnore() && 
					!_methods.get(i).isTested()) {
				Color color;
				if (_methods.get(i).getModifier().startsWith("public"))
					color = HelperUsedColor.ERROR;
				else
					color = HelperUsedColor.WARNING;
				_source.get(_methods.get(i).getLine() -1).addMessage(
						new MessageColor(ResourceBundle.getBundle(
								SourceTest.BUNDLE_FILE).getString(
										"sourceMethodNotTested") ,color));
			}
		}
	}

	private boolean methodIsTested(CSMethod method) {
		if ((method.getLastLineNumber() == -1) || 
				method.getName().startsWith("test"))
			return false;
		
		for (int line = (method.getLine() - 1); 
				line < (method.getLastLineNumber() -1); line++) {
			if (_source.get(line).isLineTested())
				return true;
		}
		
		return false;
	}
}
