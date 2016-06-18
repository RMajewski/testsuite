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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.Read;
import org.testsuite.checksource.ReadSource;
import org.testsuite.checksource.annotation.CheckSource;

/**
 * Tests the class {@link org.testsuite.checksource.ReadSource}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestReadSource {
	/**
	 * Saves the instance of ReadSource
	 */
	private ReadSource _source;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_source = new ReadSource();
	}
	
	/**
	 * Tests if implemented the Read interface.
	 */
	@Test
	public void testImplementedReadInterface() {
		assertEquals(Read.class.getName(), 
				ReadSource.class.getInterfaces()[0].getName());
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadPublicClassName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "public class " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadPrivateClassName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private class " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadProtectedClassName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "protected class " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadAbstractClassName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "protected abstract class " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadClassNameWithExtends() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private class " + className + " extends TestClass {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadClassNameWithImplements() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private class " + className + 
				" implements TestInterface {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadClassNameWithExtendsAndImplements() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private class " + className + 
				" extends TestClass implements TestInterface {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadPublicInterfaceName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "public interface " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadPrivateInterfaceName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private interface " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadProtectedInterfaceName() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "protected interface " + className + " {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadInterfaceNameWithExtends() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private interface " + className + " extends TestClass {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadInterfaceNameWithImplements() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private interface " + className + 
				" implements TestInterface {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the read() has read the right class name.
	 */
	@Test
	public void testReadInterfaceNameWithExtendsAndImplements() 
			throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String className = "Test";
		String line = "private interface " + className + 
				" extends TestClass implements TestInterface {";
		int lineNumber = 44;
		
		_source.read(lineNumber, line, list);
		
		assertTrue(list.isEmpty());

		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals(className, String.valueOf(field.get(_source)));
	}

	/**
	 * Tests if the readMethod() has created the right method.
	 */
	@CheckSource(method="readMethod")
	@Test
	public void testReadMethodWithTwoParamter() throws Exception {
		Method method = ReadSource.class.getDeclaredMethod("readMethod", 
				int.class, String[].class, List.class);
		method.setAccessible(true);
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String line = "public long test(int index, String start) {";
		String[] read = line.split(" ");
		int lineNumber = 87;
		
		method.invoke(_source, lineNumber, read, list);
		
		assertEquals(1, list.size());
		assertEquals("test", list.get(0).getName());
		assertEquals("long", list.get(0).getType());
		assertEquals("public", list.get(0).getModifier());
		assertEquals(lineNumber, list.get(0).getLine());
		assertEquals(-1, list.get(0).getLastLineNumber());
		
		assertEquals(2, list.get(0).parametersCount());
		assertEquals("int", list.get(0).getParameter(0).getType());
		assertEquals("index", list.get(0).getParameter(0).getName());
		
		assertEquals("String", list.get(0).getParameter(1).getType());
		assertEquals("start", list.get(0).getParameter(1).getName());
	}

	/**
	 * Tests if the readMethod() has created the right method.
	 */
	@CheckSource(method="readMethod")
	@Test
	public void testReadMethodWithoutParameter() throws Exception {
		Method method = ReadSource.class.getDeclaredMethod("readMethod", 
				int.class, String[].class, List.class);
		method.setAccessible(true);
		
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String line = "public long test() {";
		String[] read = line.split(" ");
		int lineNumber = 44;
		
		method.invoke(_source, lineNumber, read, list);
		
		assertEquals(1, list.size());
		assertEquals("test", list.get(0).getName());
		assertEquals("long", list.get(0).getType());
		assertEquals("public", list.get(0).getModifier());
		assertEquals(lineNumber, list.get(0).getLine());
		assertEquals(-1, list.get(0).getLastLineNumber());
		
		assertEquals(0, list.get(0).parametersCount());
	}
	
	@Test
	public void testReadMethodLastLine() {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String line1 = "public class Test {";
		String line2 = "  public int test() {";
		String line3 = "  }";
		String line4 = "}";
		
		_source.read(1, line1.trim(), list);
		_source.read(2, line2.trim(), list);
		_source.read(3, line3.trim(), list);
		_source.read(4, line4.trim(), list);

		assertEquals(1, list.size());
		assertFalse(list.isEmpty());
		assertEquals("test", list.get(0).getName());
		assertEquals("int", list.get(0).getType());
		assertEquals("public", list.get(0).getModifier());
		assertEquals(2, list.get(0).getLine());
		assertEquals(3, list.get(0).getLastLineNumber());
		
		assertEquals(0, list.get(0).parametersCount());
	}
	
	@Test
	public void testReadClassTwoLines() throws Exception {
		List<CSMethod> list = new ArrayList<CSMethod>();
		
		String line1 = "  public class ToolBarMain extends JToolBar ";
		String line2 = "      implements InternalFrameListener, ToolBarDbElementListener {";
		
		_source.read(1, line1.trim(), list);
		_source.read(2, line2.trim(), list);

		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		
		Field field = ReadSource.class.getDeclaredField("_className");
		field.setAccessible(true);
		assertEquals("ToolBarMain", String.valueOf(field.get(_source)));
	}
}
