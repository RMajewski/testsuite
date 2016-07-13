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

package tests.testsuite.checksource;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.testsuite.checksource.Parser;

import javassist.Modifier;

public class TestParser {

	@Test
	public void testIsInterface() {
		assertTrue(Parser.class.isInterface());
	}
	
	@Test
	public void testParse() throws Exception {
		String name = "parse";
		Method method = Parser.class.getMethod(name);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}
	
	@Test
	public void testPrepaireSource() throws Exception {
		String name = "prepaireSource";
		Method method = Parser.class.getMethod(name);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}
	
	@Test
	public void testSetSources() throws Exception {
		String name = "setSources";
		Method method = Parser.class.getMethod(name, List.class);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}
	
	@Test
	public void testSetMethods() throws Exception {
		String name = "setMethods";
		Method method = Parser.class.getMethod(name, List.class);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}

	@Test
	public void testSetSourceFileName() throws Exception {
		String name = "setSourceFileName";
		Method method = Parser.class.getMethod(name, String.class);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}
	
	@Test
	public void testSetTestFileName() throws Exception {
		String name = "setTestFileName";
		Method method = Parser.class.getMethod(name, String.class);
		assertEquals(name, method.getName());
		assertEquals(Modifier.PUBLIC + Modifier.ABSTRACT,
				method.getModifiers());
		assertEquals("void", method.getReturnType().getName());
	}
}
