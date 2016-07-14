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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.html.Html;
import org.testsuite.checksource.html.HtmlDeprecated;
import org.testsuite.checksource.html.HtmlOutOverview;

public class TestHtmlDeprecated {

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		HtmlDeprecated.getInstance().clear();
		HtmlOutOverview.getInstance().clear();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(HtmlDeprecated.getInstance());
	}
	
	@Test
	public void testHtmlDerived() {
		assertEquals(Html.class.getName(),
				HtmlDeprecated.class.getSuperclass().getName());
	}

	@Test
	public void testCreateListOfDeprecatedWithNoEntries() throws Exception {
		Method method = HtmlDeprecated.class.getDeclaredMethod(
				"createListOfDeprecated");
		method.setAccessible(true);
		
		assertEquals(new String(), method.invoke(HtmlDeprecated.getInstance()));
	}

	@Test
	public void testCreateListOfDeprecated() throws Exception {
		Method method = HtmlDeprecated.class.getDeclaredMethod(
				"createListOfDeprecated");
		method.setAccessible(true);
		
		String nameClass1 = "test.Test1";
		HtmlDeprecated.getInstance().addDeprecatedClass(nameClass1);
		
		String nameClass2 = "test.Test2";
		String nameMethod = "isDeprecated";
		String modifier = "private";
		String type = "void";
		String file = "/result/test.html";
		CSMethod csm = mock(CSMethod.class);
		when(csm.isDeprecated()).thenReturn(true);
		when(csm.getClassName()).thenReturn(nameClass2);
		when(csm.getName()).thenReturn(nameMethod);
		when(csm.getModifier()).thenReturn(modifier);
		when(csm.getType()).thenReturn(type);
		when(csm.getHtmlOutputFile()).thenReturn(file);
		List<CSMethod> list = new ArrayList<CSMethod>();
		list.add(csm);
		HtmlOutOverview.getInstance().addMethods(list);
		
		String result = "\t\t\t<div class=\"deprecatedList\">" + 
				System.lineSeparator() + "\t\t\t\t<p>" +
				ResourceBundle.getBundle(HtmlDeprecated.BUNDLE_FILE)
				.getString("methods_deprecated") + "</p>" +
				System.lineSeparator() + "\t\t\t\t<ul>" +
				System.lineSeparator() + 
				
				"\t\t\t\t\t<li><a href=\"" + file + "#" + nameClass2 + "." +
				nameMethod + "\">" + nameClass2 + "." + nameMethod + 
				"()</a></li>" + System.lineSeparator() +
				
				"\t\t\t\t\t<li>class " + nameClass1 + "</li>" +
				System.lineSeparator() +
				
				"\t\t\t\t</ul>" + System.lineSeparator() + "\t\t\t</div>" +
				System.lineSeparator();
		
		assertEquals(result, method.invoke(HtmlDeprecated.getInstance()));
	}

	@Test
	public void testAddDeprecatedClass() throws Exception {
		Field field = HtmlDeprecated.class.getDeclaredField("_class");
		field.setAccessible(true);
		
		assertEquals(0, 
				((List<?>)field.get(HtmlDeprecated.getInstance())).size());
		
		String test = "test.Test";
		HtmlDeprecated.getInstance().addDeprecatedClass(test);
		assertEquals(1,
				((List<?>)field.get(HtmlDeprecated.getInstance())).size());
		assertEquals("class " + test, 
				((List<?>)field.get(HtmlDeprecated.getInstance())).get(0));
	}

}
