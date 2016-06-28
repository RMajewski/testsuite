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

package tests.testsuite.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.Version;

/**
 * Tests the class {@link org.testsuite.core.Version}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Version.class})
public class TestVersion {
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		InputStreamReader isr = mock(InputStreamReader.class);
		
		PowerMockito.whenNew(InputStreamReader.class)
			.withAnyArguments()
			.thenReturn(isr);
		
		BufferedReader br = mock(BufferedReader.class);
		when(br.readLine()).thenReturn("1 2 3 4");
		
		PowerMockito.whenNew(BufferedReader.class)
			.withArguments(isr)
			.thenReturn(br);
	}

	/**
	 * Tests if the version is properly loaded from the file.
	 */
	@Test
	public void testVersion() {
		assertEquals(1, Version.version()[0]);
		assertEquals(2, Version.version()[1]);
		assertEquals(3, Version.version()[2]);
		assertEquals(4, Version.version()[3]);
	}

	/**
	 * Tests if the major version is correct.
	 */
	@Test
	public void testGetMajor() {
		assertEquals(1, Version.getMajor());
	}

	/**
	 * Tests if the minor version is correct.
	 */
	@Test
	public void testGetMinor() {
		assertEquals(2, Version.getMinor());
	}

	/**
	 * Tests if the patch number is correct.
	 */
	@Test
	public void testGetPatch() {
		assertEquals(3, Version.getPatch());
	}

	/**
	 * Tests if the build number is correct.
	 */
	@Test
	public void testGetBuild() {
		assertEquals(4, Version.getBuild());
	}

	/**
	 * Tests if the version string is correct.
	 */
	@Test
	public void testGetVersion() {
		assertEquals("1.2.3+4", Version.getVersion());
	}

}
