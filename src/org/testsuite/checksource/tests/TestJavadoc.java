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
	 * 
	 * @deprecated No longer used.
	 */
	@SuppressWarnings("unused")
	private int _begin;
	
	/**
	 * Saves the end of Javadoc comment;
	 * 
	 * @deprecated No longer used.
	 */
	@SuppressWarnings("unused")
	private int _end;
	
	/**
	 * Saves the line is a annotation
	 * 
	 * @deprecated No longer used.
	 */
	@SuppressWarnings("unused")
	private boolean _annotation;
	
	/**
	 * Initialize the data
	 * 
	 * @deprecated No longer used.
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
		int annotation = 0;
		int begin = -1;
		int end = -1;
		for (int i = 0; i < list.size(); i++) {
			if ((begin == -1) && list.get(i).isJavadoc())
				begin = list.get(i).getLineNumber();
			else if (list.get(i).isJavadoc())
				end = list.get(i).getLineNumber();
			else {
				boolean test1 = (begin == -1) && (end == -1);
				boolean test2 = (begin > -1) && (end > -1) && 
						(end == (list.get(i).getLineNumber() - 1 - annotation));
				
				// Annotation
				if (list.get(i).getLine().matches(
						"^[\\s\\w]*@[\\w\\s(){}= \"]*$"))
					annotation++;
				
				// Class
				else if (list.get(i).getLine().matches(
						"^[\\s\\w]*(class)[\\s\\w{}]*$")) {
					
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocClass"), 
								HelperUsedColor.WARNING));
						begin = -1;
						end = -1;
						annotation = 0;
					}
				}
				
				// Interface
				else if (list.get(i).getLine().matches(
						"^[\\s\\w]*(interface)[\\s\\w{}]*$")) {
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocInterface"), 
								HelperUsedColor.WARNING));
						begin = -1;
						end = -1;
						annotation = 0;
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
						begin = -1;
						end = -1;
						annotation = 0;
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
						begin = -1;
						end = -1;
						annotation = 0;
					}
				}
				
				// Method
				else if (list.get(i).isBeginMethod()) {
					if (test1 || !test2) {
						list.get(i).addMessage(new MessageColor(
								ResourceBundle.getBundle(BUNDLE_FILE)
								.getString("emptyJavadocMethod"),
								HelperUsedColor.WARNING));
						begin = -1;
						end = -1;
						annotation = 0;
					}
				}
			}
		}
		
		return true;
	}
}
