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

package org.testsuite.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Implements methods that can assist in managing the cellar objects.
 * 
 * In version 0.2, a method is implemented, the formatted test time correctly.
 * 
 * @author René Majewski
 * 
 * @version 0.2
 */
public class HelperCalendar {

	/**
	 * Initialize the calendar. The date is set to January 1 of the given year.
	 * The hours, minutes, seconds and milliseconds will set to 0.
	 * 
	 * @param year Year that the calendar instance should be assigned.
	 * 
	 * @return Generated and initialized instance of the calendar
	 */
	public static GregorianCalendar createCalendar(int year) {
		// Kalender Instanz erzeugen
		GregorianCalendar ret = new GregorianCalendar(year, 
				GregorianCalendar.JANUARY, 1);
		
		// Kalender zurück geben
		return ret;
	}
	
	/**
	 * Converts a date (long value) in a readable string around.
	 * 
	 * @param date Long value to be converted
	 * 
	 * @return Readable string.
	 */
	public static String dateToString(long date) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(
				new Date(date));
	}
	
	/**
	 * Converts a long value in the date and time in a readable string.
	 * 
	 * @param datetime Long value to be converted
	 * 
	 * @return Readable string.
	 */
	public static String datetimeToString(long datetime) {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM).format(new Date(datetime));
	}
	
	/**
	 * Converts a long value from the time in a readable string.
	 * 
	 * @param time Long value to be converted
	 * 
	 * @return Readable String
	 */
	public static String timeToString(long time) {
		return new SimpleDateFormat("HH:mm:ss.SSS")
				.format(new Date(time - 3600000));
	}
	
	/**
	 * Calculated from the specified month number of the English month names. If
	 * the number is out of range, an empty string is returned.
	 * 
	 * @param month Month number to be converted to a month name.
	 * 
	 * @return English Month Name
	 */
	public static String enMonthToString(int month) {
		switch (month) {
		case 0:
			return "January";
			
		case 1:
			return "February";
			
		case 2:
			return "March";
			
		case 3:
			return "April";
			
		case 4:
			return "May";
			
		case 5:
			return "June";
			
		case 6:
			return "July";
			
		case 7:
			return "August";
			
		case 8:
			return "September";
			
		case 9:
			return "October";
			
		case 10:
			return "November";
			
		case 11:
			return "December";
		}
		
		// Standard Rückgabe
		return new String();
	}
	
	/**
	 * Calculated from the English name of the month the number of the month. If
	 * an incorrect month name is specified, -1 is returned.
	 * 
	 * @param name Month name to be converted into a monthly number.
	 * 
	 * @return Month number, which was determined.
	 */
	public static int enStringToMonth(String name) {
		if (name == null || name.isEmpty())
			return -1;
		
		switch(name) {
			case "January": 
				return 0;
				
			case "February": 
				return 1;
				
			case "March": 
				return 2;
				
			case "April":
				return 3;
					
			case "May":
				return 4;
				
			case "June":
				return 5;
			case "July":
				return 6;
				
			case "August":
				return 7;
				
			case "September":
				return 8;
				
			case "October":
				return 9;
				
			case "November":
				return 10;
				
			case "December":
				return 11;
		}
		
		// Standard Rückgabe
		return -1;
	}
}
