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
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.html.Html;
import org.testsuite.checksource.html.HtmlMenu;


@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlMenu.class})
public class TestHtmlMenu {
	/**
	 * Saves the instance of HtmlMenu 
	 */
	private HtmlMenu _menu;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_menu = new HtmlMenu();
	}

	/**
	 * Tests the initialize of the class.
	 */
	@Test
	public void testHtmlMenu() throws Exception {
		assertNull(_menu.getResultFile());		
	}

	@Test
	public void testOverview() throws Exception {
		assertNotNull(HtmlMenu.OVERVIEW);
	}
	
	@Test
	public void testNoneSourceFiles() throws Exception {
		assertNotNull(HtmlMenu.NONE_SOURCE_FILES);
	}
	
	@Test
	public void testTodoList() throws Exception {
		assertNotNull(HtmlMenu.TODO_LIST);
	}
	
	@Test
	public void testDeprecatedList() throws Exception {
		assertNotNull(HtmlMenu.DEPRECATED_LIST);
	}
	
	@Test
	public void testNoneExistFiles() throws Exception {
		assertNotNull(HtmlMenu.NONE_EXISTS_FILES);
	}
	
	@Test
	public void testTest() throws Exception {
		assertNotNull(HtmlMenu.TEST);
	}
	
	@Test
	public void testNoneMethods() throws Exception {
		assertNotNull(HtmlMenu.NONE_METHODS);
	}

	/**
	 * Tests
	 */
	@Test
	public void testCreateMenu() throws Exception {
		String path = "test/test.html";
		String result = "\t\t<div class=\"checkSourceMenu\">" +
				System.lineSeparator() + "\t\t\t<ul>" + System.lineSeparator() +
				"\t\t\t\t<li class=\"actual\"><a href=\"" + path + "\">" +
				HtmlMenu.OVERVIEW  + "</a></li>" + System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.NONE_METHODS  + "</a></li>" + System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.NONE_SOURCE_FILES  + "</a></li>" +
				System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.NONE_EXISTS_FILES  + "</a></li>" +
				System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.TODO_LIST  + "</a></li>" +
				System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.DEPRECATED_LIST  + "</a></li>" +
				System.lineSeparator() +
				"\t\t\t\t<li><a href=\"" + path + "\">" +
				HtmlMenu.TEST  + "</a></li>" + System.lineSeparator() + 
				"\t\t\t</ul>" + System.lineSeparator() + "\t\t</div>" +
				System.lineSeparator();
		
		File file = mock(File.class);
		when(file.getAbsolutePath()).thenReturn(path);
		PowerMockito.whenNew(File.class)
			.withAnyArguments()
			.thenReturn(file);
		
		assertEquals(result, HtmlMenu.createMenu(HtmlMenu.OVERVIEW));
	}

}
