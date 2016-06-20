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

import java.util.Arrays;

/**
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public final class HelperJavaKeywords {
	/**
	 * Returns all primitive.
	 * 
	 * @return All primitive of java
	 */
	public static final String[] getPrimitive() {
		String[] ret = {"boolean", "byte", "char", "double", "false", "float",
				"int", "long", "null", "short", "true"};
		return ret;
	}
	
	/**
	 * Return all keywords for classes, methods and attribute.
	 * 
	 * @return All keyword for classes, methods and attribute.
	 */
	public static final String[] getKeywordsForClasses() {
		String[] ret = {"abstract", "class", "const", "enum", "extends", 
				"final", "implements", "import", "interface", "native", 
				"package", "private", "protected", "public", "static", 
				"strictfp", "super", "synchronized", "this", "throws",
				"transient", "void", "volatile"};
		return ret;
	}
	
	/**
	 * Return all keywords for runtime
	 * 
	 * @return All keywords for runtime
	 */
	public static final String[] getKeywordsForRuntime() {
		String[] ret = {"assert", "break", "case", "catch", "continue", 
				"default", "do", "else", "finally", "for", "goto", "if", 
				"instanceof", "new", "return", "switch", "throw", "try", 
				"while"};
		return ret;
	}
	
	/**
	 * Returns all keywords.
	 * 
	 * @return All keywords of java
	 */
	public static final String[] getAllKeywords() {
		String[] ret = copyArray(getKeywordsForRuntime(), 
				getKeywordsForClasses());
		ret = copyArray(ret, getPrimitive());
		return ret;
	}
	
	/**
	 * Copy source array 1 and source array 2 into a new array an returns.
	 * 
	 * @param source1 Source array 1
	 * 
	 * @param source2 Source array 2
	 * 
	 * @return The new array
	 */
	private static final <T> T[] copyArray(T[]source1, T[] source2) {
		T[] ret = Arrays.copyOf(source1, source1.length + source2.length);
		System.arraycopy(source2, 0, ret, source1.length, source2.length);
		return ret;
	}
}
