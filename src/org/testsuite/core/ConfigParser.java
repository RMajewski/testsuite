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

import java.io.File;

import org.testsuite.data.Config;

public class ConfigParser {
	/**
	 * Saves the configuration.
	 */
	private Config _config;
	
	/**
	 * Saves the name of configuration file.
	 */
	private String _configFile;
	
	/**
	 * Initialis the data of the class.
	 */
	public ConfigParser(Config config, String configFile) {
		_config = config;
		_configFile = configFile;
	}
	
	/**
	 * 
	 */
	public boolean parse() {
		// Überprüfen, ob die Datei existiert
		File file = new File(_configFile);
		if (!file.exists())
			return false;
		
		// Standard Rückgabe-Wert
		return false;
	}
	
	/**
	 * Returns the configuration.
	 * 
	 * @return Configuration
	 */
	public Config getConfig() {
		return _config;
	}
	
	/**
	 * Sets the configuration.
	 * 
	 * @param config The new configuration.
	 */
	public void setConfig(Config config) {
		if (config == null)
			throw new IllegalArgumentException();
		_config = config;
	}
	
	public String getConfigFile() {
		return _configFile;
	}
	
	public void setConfigFile(String configFile) {
		if ((configFile == null) || configFile.isEmpty())
			throw new IllegalArgumentException();
		_configFile = configFile;
	}
}
