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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.ListMethod;

/**
 * Tests the class {@link org.testsuite.checksource.ListMethod}
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestListMethod {
	/**
	 * Saves the instance of ListMethod
	 */
	private ListMethod _lm;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_lm = new ListMethod();
	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void testListMethod() {
		assertEquals(0, _lm.size());
	}

	/**
	 * Tests the method {@link org.testsuite.checksource.ListMethod#size()}
	 */
	@Test
	public void testSize() {
		assertEquals(0, _lm.size());
	}
	
	/**
	 * Tests the method {@link org.testsuite.checksource.ListMethod#get(int)}
	 */
	@Test
	public void testGet() {
		CSMethod method = mock(CSMethod.class);
		_lm.add(method);
		assertEquals(1, _lm.size());
		assertEquals(method, _lm.get(0));
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.ListMethod#add(CSMethod)}.
	 */
	@Test
	public void testAdd() {
		CSMethod method = mock(CSMethod.class);
		assertEquals(0, _lm.size());
		_lm.add(method);
		assertEquals(1, _lm.size());
		assertEquals(method, _lm.get(0));
	}

	/**
	 * Tests the method {@link org.testsuite.checksource.ListMethod#list()}.
	 */
	@Test
	public void testList() {
		CSMethod method1 = mock(CSMethod.class);
		CSMethod method2 = mock(CSMethod.class);
		
		_lm.add(method1);
		_lm.add(method2);
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		list.add(method1);
		list.add(method2);
		
		assertEquals(list, _lm.list());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.ListMethod#convertFromList(List)}.
	 */
	@Test
	public void testConvertFromList() {
		CSMethod method1 = mock(CSMethod.class);
		CSMethod method2 = mock(CSMethod.class);
		List<CSMethod>list = new ArrayList<CSMethod>();
		list.add(method1);
		list.add(method2);
		
		_lm = ListMethod.convertFromList(list);
		assertEquals(2, _lm.size());
		assertEquals(list, _lm.list());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.ListMethod#determineMethod(int)}.
	 */
	@Test
	public void testDetermineMethodWithNoMethodInList() {
		assertNull(_lm.determineMethod(100));
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.ListMethod#determineMethod(int)}.
	 */
	@Test
	public void testDetermineMethodWithoutMethodWithLine() {
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getLine()).thenReturn(100);
		_lm.add(method1);

		CSMethod method2 = mock(CSMethod.class);
		when(method2.getLine()).thenReturn(200);
		_lm.add(method2);
		
		CSMethod method3 = mock(CSMethod.class);
		when(method3.getLine()).thenReturn(300);
		_lm.add(method3);
		
		assertNull(_lm.determineMethod(400));
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.ListMethod#determineMethod(int)}.
	 */
	@Test
	public void testDetermineMethod() {
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getLine()).thenReturn(100);
		_lm.add(method1);

		CSMethod method2 = mock(CSMethod.class);
		when(method2.getLine()).thenReturn(200);
		_lm.add(method2);
		
		CSMethod method3 = mock(CSMethod.class);
		when(method3.getLine()).thenReturn(300);
		_lm.add(method3);
		
		assertEquals(method1, _lm.determineMethod(100));
		assertEquals(method2, _lm.determineMethod(200));
		assertEquals(method3, _lm.determineMethod(300));
	}

}
