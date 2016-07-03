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

import org.testsuite.checksource.html.HtmlOut;
import org.testsuite.checksource.tests.SourceTest;

/**
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class CheckSource {
	/**
	 * Saves the name of source file
	 */
	private String _nameSrc;
	
	/**
	 * Saves the name of test file
	 */
	private String _nameTest;
	
	/**
	 * Saves the name of result file
	 */
	private String _nameResult;
	
	/**
	 * Saves if create the HTML output
	 */
	private boolean _createHtml;
	
	/**
	 * Saves the instance for ReadSource
	 */
	private SourceFile _source;
	
	/**
	 * Initialize the data of the class
	 * 
	 * @param nameSrc The name of source file
	 * 
	 * @param nameTest The name of test file
	 * 
	 * @param nameResult the name of result file (can be null)
	 */
	public CheckSource(String nameSrc, String nameTest, String nameResult) {
		setNameSourceFile(nameSrc);
		setNameTestFile(nameTest);
		setNameResultFile(nameResult);
		_source = new SourceFile();
		_source.setFileName(_nameSrc);
	}
	
	/**
	 * Set the name of source file
	 * 
	 * @param nameSrc The name source file
	 */
	public void setNameSourceFile(String nameSrc) {
		if ((nameSrc == null) || nameSrc.isEmpty())
			throw new IllegalArgumentException();
		_nameSrc = nameSrc;
	}
	
	/**
	 * Set the name of test file.
	 * 
	 * @param nameTest The name of test file
	 */
	public void setNameTestFile(String nameTest) {
		if ((nameTest == null) || nameTest.isEmpty())
			throw new IllegalArgumentException();
		_nameTest = nameTest;
	}
	
	/**
	 * Returns the name of source file
	 * 
	 * @return The name of source file
	 */
	public String getNameSourceFile() {
		return _nameSrc;
	}
	
	/**
	 * Return the name test file
	 * 
	 * @return The name of test file
	 */
	public String getNameTestFile() {
		return _nameTest;
	}

	/**
	 * Returns the name of result file
	 * 
	 * @return The name of result file
	 */
	public String getNameResultFile() {
		return _nameResult;
	}

	/**
	 * Sets the name of result file
	 * 
	 * @param nameResult The name of result file
	 */
	public void setNameResultFile(String nameResult) {
		if ((nameResult == null) || nameResult.isEmpty())
			_createHtml = false;
		else
			_createHtml = true;
		_nameResult = nameResult;
	}

	/**
	 * Return if create the result HTML file.
	 * 
	 * @return If create the result HTML file?
	 */
	public boolean isCreateHtml() {
		return _createHtml;
	}

	/**
	 * Sets if create the result HTML file
	 * 
	 * @param createHtml If creates the result HTML file?
	 */
	public void setCreateHtml(boolean createHtml) {
		_createHtml = createHtml;
	}
	
	/**
	 * Read the source file and the test file
	 * 
	 * @deprecated Use {@link #run}
	 */
	public void read() {
		run();
	}
	
	/**
	 * Read the source file and run the tests
	 */
	public void run() {
		// Read the source files
		_source.readFile(false, null);
		_source.readFile(true, _nameTest);
		
		// Parse source code
//		Parser parser = new Parser(_nameTest, _nameSrc, _source.getSourceList());
//		parser.parse(_source.getMethodList());
//		parser.debug();
		
		// Run tests
		for (int i = 0; i < CSConfig.getInstance().testCount(); i++) {
			try {
				SourceTest test = (SourceTest)getClass().getClassLoader()
						.loadClass(CSConfig.getInstance()
								.getPathCheckSourceTests()+ "." + 
								CSConfig.getInstance().getTestName(i))
						.newInstance();
				test.test(_source.getSourceList());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Prepare lines of source code for output.
		_source.prepaireSource();
	}
	
	/**
	 * Create the HTML output
	 */
	public void createHtmlOut() {
		if (isCreateHtml()) {
			HtmlOut html = new HtmlOut(_nameResult);
			html.createHtml(_source.getSourceList(), _source.getMethodList());
		}
	}
	
	/**
	 * Returns the list of method from SourceFile
	 * 
	 * @return The list of methods
	 */
	public List<CSMethod> getMethodList() {
		return _source.getMethodList();
	}
	
	/**
	 * Returns the list of source lines from SourceFile
	 * 
	 * @return The list of source lines
	 */
	public List<SourceLine> getSourceLineList() {
		return _source.getSourceList();
	}
}
