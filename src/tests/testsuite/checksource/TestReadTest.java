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
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.ReadTest;

/**
 * Tests the class {@link org.testsuite.checksource.ReadTest}.
 * 
 * @author René Majewski
 *
 */
public class TestReadTest {
	/**
	 * Saves the instance if ReadTest
	 */
	private ReadTest _rt;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_rt = new ReadTest();
	}

	/**
	 * Tests if sets the name off class correctly.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		String name = "Test";
		_rt.addClassName(name);
		
		Field field = ReadTest.class.getDeclaredField("_classNames");
		field.setAccessible(true);
		
		assertEquals(1, ((HashSet<String>)field.get(_rt)).size());
		assertEquals(name, ((HashSet<String>)field.get(_rt)).iterator().next());
	}

	/**
	 * Tests if read the source code correctly.
	 */
	@Test
	public void testRead() {
		String line1 = "Test test = new Test();";
		String line2 = "test.test()";
		
		String className = "Test";
		String methodName = "test";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(methodName);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(className);
		methods.add(method2);
		
		_rt.read(1, line1, methods);
		_rt.read(2, line2, methods);
		
		verify(method1).addCall(2);
		verify(method2).addCall(1);
	}
}
