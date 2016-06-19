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

package org.testsuite.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Returns the version information.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public final class Version {
	/**
	 * Read the versions informations from file and returns as array.
	 * 
	 * @return Versions informations as array.
	 */
	public static int[] version() {
		int[] ret = {0, 0, 0, 0};
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					Version.class.getClassLoader().getResourceAsStream(
							"version")));
			String[] inp = br.readLine().split(" ");
			ret[0] = Integer.valueOf(inp[0]);
			ret[1] = Integer.valueOf(inp[1]);
			ret[2] = Integer.valueOf(inp[2]);
			ret[3] = Integer.valueOf(inp[3]);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * Returns the number of version major
	 * 
	 * @return Number of versions major
	 */
	public static int getMajor() {
		return version()[0];
	}
	
	/**
	 * Returns the number of version minor
	 * 
	 * @return Number of versions minor
	 */
	public static int getMinor() {
		return version()[1];
	}
	
	/**
	 * Return the number of version patch
	 * 
	 * @return Number of versions patch
	 */
	public static int getPatch() {
		return version()[2];
	}
	
	
	/**
	 * Return the number of version build
	 * 
	 * @return Number of versions build
	 */
	public static int getBuild() {
		return version()[3];
	}
	
	/**
	 * Returns the version string
	 * 
	 * @return The version string
	 */
	public static String getVersion() {
		return String.valueOf(getMajor()) + "." + String.valueOf(getMinor()) + 
				"." + String.valueOf(getPatch()) + "+" + 
				String.valueOf(getBuild());
	}

}
