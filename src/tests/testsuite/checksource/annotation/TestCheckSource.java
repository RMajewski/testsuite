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

package tests.testsuite.checksource.annotation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testsuite.checksource.annotation.CheckSource;

public class TestCheckSource {

	/**
	 * Tests if no superclass is defined.
	 */
	@Test
	public void testNoSuperClass() {
		assertNull(CheckSource.class.getSuperclass());
	}
	
	/**
	 * Tests if there is an annotation.
	 */
	@Test
	public void testIsAnnotation() {
		assertTrue(CheckSource.class.isAnnotation());
	}
	
	/**
	 * Tests if method exists.
	 */
	@Test
	public void testMethod() throws Exception {
		assertEquals("public abstract java.lang.String" +
			" org.testsuite.checksource.annotation.CheckSource.method()",
				CheckSource.class.getDeclaredMethod("method").toString());
	}
	
	/**
	 * Tests if method exists.
	 */
	@Test
	public void testMethodList() throws Exception {
		assertEquals("public abstract java.lang.String[]" +
			" org.testsuite.checksource.annotation.CheckSource.methodList()",
				CheckSource.class.getDeclaredMethod("methodList").toString());
	}

}
