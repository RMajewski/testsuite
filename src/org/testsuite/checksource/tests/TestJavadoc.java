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

import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests if the classes, methods and attributes are also provided with Javadoc
 * comments.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestJavadoc implements SourceTest {
	/**
	 * Saves the begin of Javadoc comment
	 */
	private int _begin;
	
	/**
	 * Saves the end of Javadoc comment;
	 */
	private int _end;
	
	/**
	 * Saves the line is a annotation
	 */
	private boolean _annotation;
	
	/**
	 * Initialize the data
	 */
	public TestJavadoc() {
		_begin = -1;
		_end = -1;
		_annotation = false;
	}

	/**
	 * Tests if the classes, methods and attributes are also provided with
	 * Javadoc comments.
	 * 
	 * @param list The list of source lines.
	 * 
	 * @return True, if the test was successful. False if an error has occurred
	 * in the line.
	 */
	@Override
	public boolean test(List<SourceLine> list) {
		for (int i = 0; i < list.size(); i++) {
			if ((_begin == -1) && list.get(i).isJavadoc())
				_begin = list.get(i).getLineNumber();
			else if (list.get(i).isJavadoc())
				_end = list.get(i).getLineNumber();
			else {
				boolean test1 = (_begin == -1) && (_end == -1);
				boolean test2 = (_begin > -1) && (_end > -1) && 
						(_end == (list.get(i).getLineNumber()) - 1);
				
				// Annotation
				if (list.get(i).getLine().matches("^[\\s\\w]*@[\\w\\s(){}=]*$"))
					_end = list.get(i).getLineNumber();
				
				// Class
				else if (list.get(i).getLine().matches(
						"^[\\s\\w]*(class)[\\s\\w{}]*$")) {
					
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocClass"), 
								HelperUsedColor.WARNING));
						_begin = -1;
						_end = -1;
						_annotation = false;
					}
				}
				
				// Field
				else if (list.get(i).getLine().matches(
						"^\\s*(private|public|protected)[\\s\\w]*;$")) {
					
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocField"),
								HelperUsedColor.WARNING));
						_begin = -1;
						_end = -1;
						_annotation = false;
					}
				}
				
				// Const
				else if (list.get(i).getLine().matches(
						"^\\s*(private|public|protected)[\\s\\w=\\[\\]{}\";]*$")) {
					
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocConst"),
								HelperUsedColor.WARNING));
						_begin = -1;
						_end = -1;
						_annotation = false;
					}
				}
				
				// Method
				else if (list.get(i).getLine().matches(
						"^\\s*(private|public|protected)[\\s\\w\\[\\]\\(\\)]*\\{$")) {
					
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocMethod"),
								HelperUsedColor.WARNING));
						_begin = -1;
						_end = -1;
						_annotation = false;
					}
				}
			}
		}
		
		return true;
	}
}
