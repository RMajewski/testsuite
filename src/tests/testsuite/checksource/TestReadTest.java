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
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSCall;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.ReadTest;

/**
 * Tests the class {@link org.testsuite.checksource.ReadTest}.
 * 
 * @author René Majewski
 *
 * @deprecated
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ReadTest.class})
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
	public void testClassName() throws Exception {
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
	public void testRead() throws Exception {
		String line1 = "Test test = new Test();";
		String line2 = "test.test();";
		
		String className = "Test";
		String methodName = "test";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(className);
		when(method1.parametersCount()).thenReturn(0);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(methodName);
		when(method2.parametersCount()).thenReturn(0);
		methods.add(method2);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(0);
		
		CSCall call2 = mock(CSCall.class);
		when(call2.parameterCount()).thenReturn(0);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(1), eq(true))
			.thenReturn(call1);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(2), eq(true))
			.thenReturn(call2);
		
		_rt.read(1, line1, methods);
		_rt.read(2, line2, methods);
		
		verify(call1, never()).addParameter(Matchers.anyString());
		verify(call2, never()).addParameter(Matchers.anyString());
		
		verify(method1).addCall(Matchers.any(CSCall.class));
		verify(method2).addCall(Matchers.any(CSCall.class));
	}

	/**
	 * Tests if read the source code correctly.
	 */
	@Test
	public void testReadWithOneAttribute() throws Exception {
		String attribute1 = "\"test\"";
		String attribute2 = "new int [] = {10, 10}";
		
		String line1 = "public static Test test;";
		String line2 = "test = new Test(" + attribute1 + ");";
		String line3 = "test.test(" + attribute2 + ");";
		
		String className = "Test";
		String methodName = "test";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(className);
		when(method1.parametersCount()).thenReturn(1);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(methodName);
		when(method2.parametersCount()).thenReturn(1);
		methods.add(method2);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(1);
		
		CSCall call2 = mock(CSCall.class);
		when(call2.parameterCount()).thenReturn(1);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(2), eq(true))
			.thenReturn(call1);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(3), eq(true))
			.thenReturn(call2);
		
		_rt.read(1, line1, methods);
		_rt.read(2, line2, methods);
		_rt.read(3, line3, methods);
		
		verify(call1).addParameter(attribute1);
		verify(call2).addParameter(attribute2);
		
		verify(method1).addCall(Matchers.any(CSCall.class));
		verify(method2).addCall(Matchers.any(CSCall.class));
	}

	/**
	 * Tests if read the source code correctly.
	 */
	@Test
	public void testReadWithTwoAttribute() throws Exception {
		String attribute1 = "\"test\"";
		String attribute2 = "10";
		
		String attribute3 = "new int [] = {10, 10}";
		String attribute4 = "\"test\"";
		
		String line1 = "public static Test test;";
		String line2 = "test = new Test(" + attribute1 + ", " + attribute2 + 
				");";
		String line3 = "test.test(" + attribute3 + ", " + attribute4 + ");";
		
		String className = "Test";
		String methodName = "test";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(className);
		when(method1.parametersCount()).thenReturn(2);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(methodName);
		when(method2.parametersCount()).thenReturn(2);
		methods.add(method2);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(2);
		
		CSCall call2 = mock(CSCall.class);
		when(call2.parameterCount()).thenReturn(2);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(2), eq(true))
			.thenReturn(call1);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(3), eq(true))
			.thenReturn(call2);
		
		_rt.read(1, line1, methods);
		_rt.read(2, line2, methods);
		_rt.read(3, line3, methods);
		
		verify(call1).addParameter(attribute1);
		verify(call1).addParameter(attribute2);
		
		verify(call2).addParameter(attribute3);
		verify(call2).addParameter(attribute4);
		
		verify(method1).addCall(Matchers.any(CSCall.class));
		verify(method2).addCall(Matchers.any(CSCall.class));
	}

	/**
	 * Tests if read the source code correctly.
	 */
	@Test
	public void testReadWithTwoLines() throws Exception {
		String attribute1 = "\"test\"";
		String attribute2 = "10";
		
		String attribute3 = "new int [] = {10, 10}";
		String attribute4 = "\"test\"";
		
		String line1 = "public static Test test;";
		String line2 = "test = new Test(" + attribute1 + ",";
		String line3 = attribute2 + ");";
		String line4 = "test.test(" + attribute3 + ",";
		String line5 = attribute4 + ");";
		
		String className = "Test";
		String methodName = "test";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(className);
		when(method1.parametersCount()).thenReturn(2);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(methodName);
		when(method2.parametersCount()).thenReturn(2);
		methods.add(method2);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(2);
		
		CSCall call2 = mock(CSCall.class);
		when(call2.parameterCount()).thenReturn(2);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(3), eq(true))
			.thenReturn(call1);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(5), eq(true))
			.thenReturn(call2);
		
		_rt.read(1, line1, methods);
		_rt.read(2, line2, methods);
		_rt.read(3, line3, methods);
		_rt.read(4, line4, methods);
		_rt.read(5, line5, methods);
		
		verify(call1).addParameter(attribute1);
		verify(call1).addParameter(attribute2);
		
		verify(call2).addParameter(attribute3);
		verify(call2).addParameter(attribute4);
		
		verify(method1).addCall(Matchers.any(CSCall.class));
		verify(method2).addCall(Matchers.any(CSCall.class));
	}

	/**
	 * Tests if read correctly the check source annotation.
	 */
	@Test
	public void testReadAnnotationMethod() throws Exception {
		String className = "Test";
		String methodName = "test";

		String line1 = "@CheckSource(method=\"" + methodName + "\")";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(methodName);
		methods.add(method1);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(0);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(1), eq(true))
			.thenReturn(call1);
		
		_rt.read(1, line1, methods);

		verify(method1).addCall(Matchers.any(CSCall.class));
		
		PowerMockito.verifyNew(CSCall.class);
	}

	/**
	 * Tests if read correctly the check source annotation.
	 */
	@Test
	public void testReadAnnotationMethodList() throws Exception {
		String className = "Test";
		String methodName1 = "test1";
		String methodName2 = "test2";

		String line1 = "@CheckSource(methodList={\"" + methodName1 + "\", \"" + 
				methodName2 + "\"})";
		
		_rt.addClassName(className);
		
		List<CSMethod> methods = new ArrayList<CSMethod>();
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className);
		when(method1.getName()).thenReturn(methodName1);
		methods.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className);
		when(method2.getName()).thenReturn(methodName2);
		methods.add(method2);
		
		CSCall call1 = mock(CSCall.class);
		when(call1.parameterCount()).thenReturn(0);
		
		PowerMockito.whenNew(CSCall.class)
			.withArguments(eq(1), eq(true))
			.thenReturn(call1);
		
		_rt.read(1, line1, methods);

		verify(method1).addCall(Matchers.any(CSCall.class));
		verify(method2).addCall(Matchers.any(CSCall.class));
		
		PowerMockito.verifyNew(CSCall.class, times(2));
	}
}
