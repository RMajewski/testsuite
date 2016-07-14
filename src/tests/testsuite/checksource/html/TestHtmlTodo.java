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

package tests.testsuite.checksource.html;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.html.Html;
import org.testsuite.checksource.html.HtmlTodo;
import org.testsuite.data.Config;
import org.testsuite.data.TodoData;
import org.testsuite.helper.HelperHtmlCodeJava;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlTodo.class})
public class TestHtmlTodo {

	@Before
	public void setUp() throws Exception {
		HtmlTodo.getInstance().clear();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(HtmlTodo.getInstance());
	}
	
	@Test
	public void testHtmlDerived() {
		assertEquals(Html.class.getName(),
				HtmlTodo.class.getSuperclass().getName());
	}

	@Test
	public void testCreateHtml() throws Exception {
		Method method = HtmlTodo.class.getDeclaredMethod("createTodoList");
		method.setAccessible(true);
		
		Config.getInstance().addToDoWord("This");
		
		String name = "test/Testtest.java";
		String absolute = "/" + name;
		int number = 100;
		String line = "// This is a test";
		
		TodoData data = mock(TodoData.class);
		when(data.getFileName()).thenReturn(name);
		when(data.getNumber()).thenReturn(number);
		when(data.getLine()).thenReturn(line);
		HtmlTodo.getInstance().addTodo(data);
		
		File file = mock(File.class);
		when(file.getAbsolutePath()).thenReturn(absolute);
		when(file.exists()).thenReturn(true);
		when(file.getName()).thenReturn(name);
		
		PowerMockito.whenNew(File.class)
			.withArguments(name)
			.thenReturn(file);
		
		ResourceBundle bundle = ResourceBundle.getBundle(HtmlTodo.BUNDLE_FILE);
		String result = "\t\t\t<div class=\"todoList\">" +
				System.lineSeparator() + "\t\t\t\t<p>" +
				bundle.getString("todo_list") + "</p>" +
				System.lineSeparator() + "\t\t\t\t<table>" +
				System.lineSeparator() + "\t\t\t\t\t<tr>" +
				System.lineSeparator() + 
				
				"\t\t\t\t\t\t<th style=\"width: 300px;\">" + 
				bundle.getString("todo_file") + "</th>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t\t<th style=\"width: 140px;\">" + 
				bundle.getString("todo_row") + "</th>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t\t<th>" + 
				bundle.getString("todo_source") + "</th>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t</tr>" + System.lineSeparator() + 
				"\t\t\t\t\t<tr>" + System.lineSeparator() +
				
				"\t\t\t\t\t\t<td><a href=\"" + absolute +"\">" +
				name.substring(name.indexOf("Test") + 4) + "</a></td>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t\t<td>" + String.valueOf(number) + "</td>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t\t<td>" + HelperHtmlCodeJava.formatString(
						line, false, false, -1, null) + "</td>" +
				System.lineSeparator() +
				
				"\t\t\t\t\t</tr>" + System.lineSeparator() + 
				"\t\t\t\t</table>" + System.lineSeparator() + "\t\t\t</div>" +
				System.lineSeparator();
		
		assertEquals(result, method.invoke(HtmlTodo.getInstance()));
		
	}

	@Test
	public void testCreateHtmlWithoutTodoData() throws Exception {
		Method method = HtmlTodo.class.getDeclaredMethod("createTodoList");
		method.setAccessible(true);
		assertEquals(new String(), method.invoke(HtmlTodo.getInstance()));
	}

	@Test
	public void testAddTodo() {
		assertEquals(0, HtmlTodo.getInstance().todoCount());
		
		TodoData data = mock(TodoData.class);
		HtmlTodo.getInstance().addTodo(data);
		
		assertEquals(1, HtmlTodo.getInstance().todoCount());
		assertEquals(data, HtmlTodo.getInstance().getTodo(0));
	}

	@Test
	public void testTodoCount() {
		assertEquals(0, HtmlTodo.getInstance().todoCount());
	}

	@Test
	public void testGetTodo() {
		TodoData data = mock(TodoData.class);
		HtmlTodo.getInstance().addTodo(data);
		assertEquals(data, HtmlTodo.getInstance().getTodo(0));
	}

}
