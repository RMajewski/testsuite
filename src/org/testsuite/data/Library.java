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

package org.testsuite.data;

/**
 * Stores the information in a library.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class Library {

	/**
	 * Stores the name.
	 */
	private String _name;
	
	/**
	 * Stores the version.
	 */
	private String _version;
	
	/**
	 * Stores the directory.
	 */
	private String _path;
	
	/**
	 * Stores the filename.
	 */
	private String _fileName;
	
	/**
	 * Initialis the data of the class.
	 */
	public Library() {
		_fileName = new String();
		_name =  new String();
		_path = new String();
		_version = new String();
	}
	
	/**
	 * Returns the file name.
	 * 
	 * @return The file name.
	 */
	public String getFileName() {
		return _fileName;
	}
	
	/**
	 * Sets the file name re.
	 * 
	 * @param name The new file name.
	 */
	public void setFileName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_fileName = name;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return The library name.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Sets the name re.
	 * 
	 * @param name The new name.
	 */
	public void setName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_name = name;
	}
	
	/**
	 * Returns the directory.
	 *  
	 * @return The directory
	 */
	public String getPath() {
		return _path;
	}
	
	/**
	 * Sets the path re.
	 * 
	 * @param path The new path.
	 */
	public void setPath(String path) {
		if ((path == null) || path.isEmpty())
			throw new IllegalArgumentException();
		_path = path;
	}
	
	/**
	 * Returns the version.
	 * 
	 * @return The version.
	 */
	public String getVersion() {
		return _version;
	}
	
	/**
	 * Sets the version re.
	 * 
	 * @param version The new version.
	 */
	public void setVersion(String version) {
		if ((version == null) || version.isEmpty())
			throw new IllegalArgumentException();
		_version = version;
	}
}
