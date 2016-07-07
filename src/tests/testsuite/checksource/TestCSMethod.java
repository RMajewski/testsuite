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
import org.junit.Ignore;
import org.junit.Test;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.CSParameter;

/**
 * Tests the class {@link org.testsuite.checksource.CSMethod}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestCSMethod {
	/**
	 * Saves the instance of CSMethod
	 */
	private CSMethod _cs;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_cs = new CSMethod();
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testCSMethod() {
		assertEquals(-1, _cs.getLine());
		assertEquals(new String(), _cs.getValue());
		assertEquals(new String(), _cs.getType());
		assertEquals(new String(), _cs.getModifier());
		assertEquals(0, _cs.callsCount());
		assertEquals(-1, _cs.getLastLineNumber());
		assertEquals(new String(), _cs.getName());
		assertEquals(new String(), _cs.getClassName());
		assertFalse(_cs.isDeprecated());
		assertEquals(new String(), _cs.getHtmlOutputFile());
		assertFalse(_cs.isIgnore());
		assertEquals(-1, _cs.getBreakpoint());
	}
	
	/**
	 * Tests if returned the number of parameters correctly.
	 * 
	 * @deprecated The method is deprecated.
	 */
	@Test
	public void testParamtersCount() {
		assertEquals(0, _cs.callsCount());
	}

	/**
	 * Tests if added a parameter correctly
	 */
	@Test
	public void testAddParameter() {
		String type = "int";
		String name = "test";
		CSParameter parameter = new CSParameter(type, name);
		assertEquals(0, _cs.parametersCount());
		_cs.addParameter(parameter);
		assertEquals(1, _cs.parametersCount());
		assertEquals(parameter, _cs.getParameter(0));
	}
	
	/**
	 * Tests if returned the specified paramter correctly.
	 */
	@Test
	public void testGetParameter() {
		String type = "int";
		String name = "test";
		CSParameter parameter = new CSParameter(type, name);
		_cs.addParameter(parameter);
		assertEquals(parameter, _cs.getParameter(0));
	}
	
	/**
	 * Tests if was the number of last line returned correctly.
	 */
	@Test
	public void testGetLastLineNumber() {
		assertEquals(-1, _cs.getLastLineNumber());
	}
	
	/**
	 * Tests if was the number of last line set correctly.
	 */
	@Test
	public void testSetLastLineNumber() {
		int last = 10;
		_cs.setLastLineNumber(last);
		assertEquals(last, _cs.getLastLineNumber());
	}

	/**
	 * Tests if the line number is returned correctly.
	 */
	@Test
	public void testGetLine() {
		assertEquals(-1, _cs.getLine());
	}

	/**
	 * Tests if the line number is set correctly.
	 */
	@Test
	public void testSetLine() {
		int line = 10;
		_cs.setLine(line);
		assertEquals(line, _cs.getLine());
	}

	/**
	 * Tests if the value is returned correctly.
	 */
	@Test
	public void testGetValue() {
		assertEquals(new String(), _cs.getValue());
	}

	/**
	 * Tests if the value is set correctly.
	 */
	@Test
	public void testSetValue() {
		String value = "Test";
		_cs.setValue(value);
		assertEquals(value, _cs.getValue());
	}

	/**
	 * Tests whether the error IllegalArgumentException occurs when null as a
	 * parameter.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetValueWithNullAsParameter() {
		_cs.setValue(null);
	}

	/**
	 * Tests if the type is returned correctly
	 */
	@Test
	public void testGetType() {
		assertEquals(new String(), _cs.getType());
	}

	/**
	 * Tests if the type is set correctly.
	 */
	@Test
	public void testSetType() {
		String type= "Object";
		_cs.setType(type);
		assertEquals(type, _cs.getType());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetTypeWithNullAsParamter() {
		_cs.setType(null);
	}

	/**
	 * Tests if the modifier is returned correctly
	 */
	@Test
	public void testGetModifier() {
		assertEquals(new String(), _cs.getModifier());
	}

	/**
	 * Tests if the modifier is set correctly
	 */
	@Test
	public void testSetModifier() {
		String modifier = "private";
		_cs.setModifier(modifier);
		assertEquals(modifier, _cs.getModifier());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetModifierWithNullAsParamter() {
		_cs.setModifier(null);
	}
	
	/**
	 * Tests if the number of calls returned correctly.
	 * 
	 * @deprecated The method is deprecated.
	 */
	@Test
	public void testCallsCount() {
		assertEquals(0, _cs.callsCount());
	}

	/**
	 * Tests if added a new call correctly to the list of calls.
	 *
	 * @deprecated The method
	 * {@link org.testsuite.checksource.CSMethod#addCall(int)} is deprecated.
	 */
	@Ignore
	@Test
	public void testAddCall() {
		int call = 100;
		assertEquals(0, _cs.callsCount());
		_cs.addCall(call);
		assertEquals(1, _cs.callsCount());
		assertEquals(call, _cs.getCall(0));
	}
	
	/**
	 * Tests if returned a call correctly from the list of calls.
	 * 
	 * @deprecated The method
	 * {@link org.testsuite.checksource.CSMethod#getCall(int)} is deprecated.
	 */
	@Ignore
	@Test
	public void testGetCall() {
		int call = 100;
		_cs.addCall(call);
		assertEquals(call, _cs.getCall(0));
	}

	/**
	 * Tests if the name is returned correctly
	 */
	@Test
	public void testGetName() {
		assertEquals(new String(), _cs.getName());
	}

	/**
	 * Tests if the name is set correctly.
	 */
	@Test
	public void testSetName() {
		String name = "Test";
		_cs.setName(name);
		assertEquals(name, _cs.getName());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameWithNullAsParamter() {
		_cs.setName(null);
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameWithEmptyStringAsParamter() {
		_cs.setName(new String());
	}

	/**
	 * Tests if the name of class is returned correctly
	 */
	@Test
	public void testGetClassName() {
		assertEquals(new String(), _cs.getClassName());
	}

	/**
	 * Tests if the name of class is set correctly.
	 */
	@Test
	public void testSetClassName() {
		String name = "Test";
		_cs.setClassName(name);
		assertEquals(name, _cs.getClassName());
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetClassNameWithNullAsParamter() {
		_cs.setClassName(null);
	}

	/**
	 * Tests if the error occurs IllegalArgumentException.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetClassNameWithEmptyStringAsParamter() {
		_cs.setClassName(new String());
	}
	
	/**
	 * Tests if deprecated method is returned correctly
	 */
	@Test
	public void testIsDeprecated() {
		assertFalse(_cs.isDeprecated());
	}
	
	/**
	 * Tests if deprecated method is set correctly.
	 */
	@Test
	public void testSetDeprecated() {
		_cs.setDeprecated(true);
		assertTrue(_cs.isDeprecated());
	}
	
	/**
	 * Tests if ignored method is returned correctly.
	 */
	@Test
	public void testIsIgnore() {
		assertFalse(_cs.isIgnore());
	}
	
	/**
	 * Test if ignored method is returned correctly.
	 */
	@Test
	public void testSetIgnore() {
		_cs.setIgnore(true);
		assertTrue(_cs.isIgnore());
	}
	
	/**
	 * Test if returns the name of result file is correctly.
	 */
	@Test
	public void testGetHtmlOutputFile() {
		assertEquals(new String(), _cs.getHtmlOutputFile());
	}
	
	/**
	 * Test if sets the name of result file is correctly.
	 */
	@Test
	public void testSetHtmlOutputFile() {
		String test = "test";
		_cs.setHtmlOutputFile(test);
		assertEquals(test, _cs.getHtmlOutputFile());
	}
	
	/**
	 * Test if added a list of parameters to the parameter list
	 */
	@Test
	public void testAddParameterList() {
		List<CSParameter> list = new ArrayList<CSParameter>();
		CSParameter param1 = mock(CSParameter.class);
		CSParameter param2 = mock(CSParameter.class);
		CSParameter param3 = mock(CSParameter.class);
		_cs.addParameter(param1);
		list.add(param2);
		list.add(param3);
		_cs.addParameterList(list);
		assertEquals(param1, _cs.getParameter(0));
		assertEquals(param2, _cs.getParameter(1));
		assertEquals(param3, _cs.getParameter(2));
	}
	
	/**
	 * If returned a the breakpoint correctly.
	 */
	@Test
	public void testGetBreakpoint() {
		assertEquals(-1, _cs.getBreakpoint());
	}
	
	/**
	 * If set the breakpoint correctly.
	 */
	@Test
	public void testSetBreakpoint() {
		int bp = 100;
		_cs.setBreakpoint(bp);
		assertEquals(bp, _cs.getBreakpoint());
	}
	
	/**
	 * Tests if the method was tested.
	 */
	@Test
	public void testIsTested() {
		assertFalse(_cs.isTested());
	}
	
	/**
	 * Tests if the method has been properly marked as tested.
	 */
	@Test
	public void testSetTested() {
		_cs.setIsTested(true);
		assertTrue(_cs.isTested());
	}
}