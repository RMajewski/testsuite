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

package tests.testsuite.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import org.testsuite.helper.HelperCalendar;

/**
 * Test the class {@link org.testsuite.helper.HelperCalendar}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestHelperCalendar {
	/**
	 * Save an instance of the Calendar
	 */
	private GregorianCalendar _cal;
	
	/**
	 * Saves the year
	 */
	private int _year;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_year = 2016;
		_cal = HelperCalendar.createCalendar(_year);
	}

	/**
	 * Tests if the calendar instance is not null.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreatecalendarReturnNotNull() {
		assertNotNull(_cal);
	}
	
	/**
	 * Tests whether the year was correctly set in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightYear() {
		assertEquals(_year, _cal.get(GregorianCalendar.YEAR));
	}

	
	/**
	 * Tests if the month has been set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightMonth() {
		assertEquals(GregorianCalendar.JANUARY, 
				_cal.get(GregorianCalendar.MONTH));
	}
	
	/**
	 * Tests if the day has been set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightDay() {
		assertEquals(1, _cal.get(GregorianCalendar.DAY_OF_MONTH));
	}
	
	/**
	 * Tests if the hour has been set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightHour() {
		assertEquals(0, _cal.get(GregorianCalendar.HOUR));
	}
	
	/**
	 * Tests if the minute has been set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightMinute() {
		assertEquals(0, _cal.get(GregorianCalendar.MINUTE));
	}
	
	/**
	 * Tests whether the second has been set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightSecond() {
		assertEquals(0, _cal.get(GregorianCalendar.SECOND));
	}
	
	/**
	 * Tests if the millisecond is set correctly in the Calendar instance.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#createCalendar(int)
	 */
	@Test
	public void testCreateCalendarReturnHasRightHourMilliSecond() {
		assertEquals(0, _cal.get(GregorianCalendar.MILLISECOND));
	}
	
	/**
	 * Tests if the specified long value right into a readable string converted.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#dateToString(long)
	 */
	@Test
	public void testDateToString() {
		assertEquals("01.01.2016", 
				HelperCalendar.dateToString(_cal.getTimeInMillis()));
	}
	
	/**
	 * Tests if the specified long value right into a readable string converted.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#timeToString(long)
	 */
	@Test
	public void testTimeToString() {
		long time = 1000;
		assertEquals("00:00:01.000", HelperCalendar.timeToString(time));
	}
	
	/**
	 * Tests if the value returned is correct. It tested 12 different ways.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enStringToMonth(String)
	 */
	@Test
	public void testEnStringToMonthReturnRigth() {
		assertEquals(0, HelperCalendar.enStringToMonth("January"));
		assertEquals(1, HelperCalendar.enStringToMonth("February"));
		assertEquals(2, HelperCalendar.enStringToMonth("March"));
		assertEquals(3, HelperCalendar.enStringToMonth("April"));
		assertEquals(4, HelperCalendar.enStringToMonth("May"));
		assertEquals(5, HelperCalendar.enStringToMonth("June"));
		assertEquals(6, HelperCalendar.enStringToMonth("July"));
		assertEquals(7, HelperCalendar.enStringToMonth("August"));
		assertEquals(8, HelperCalendar.enStringToMonth("September"));
		assertEquals(9, HelperCalendar.enStringToMonth("October"));
		assertEquals(10, HelperCalendar.enStringToMonth("November"));
		assertEquals(11, HelperCalendar.enStringToMonth("December"));
	}
	
	/**
	 * Tests if returned with a false name month -1.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enStringToMonth(String)
	 */
	@Test
	public void testEnStringToMonthWrongParameterReturnMinusOne() {
		assertEquals(-1, HelperCalendar.enStringToMonth("test"));
	}
	
	/**
	 * Tests if given with an empty string as a parameter returns -1.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enStringToMonth(String)
	 */
	@Test
	public void testEnStringToMonthEmptyAsParameterReturnMinusOne() {
		assertEquals(-1, HelperCalendar.enStringToMonth(new String()));
	}
	
	/**
	 * Tests if at a <b>null</b> is returned as parameter -1.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enStringToMonth(String)
	 */
	@Test
	public void testEnStringToMonthNullAsParameterReturnMinusOne() {
		assertEquals(-1, HelperCalendar.enStringToMonth(null));
	}
	
	/**
	 * Tests if the correct month names are returned.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enMonthToString(int)
	 */
	@Test
	public void testEnMonthToStringReturnRight() {
		assertEquals("January", HelperCalendar.enMonthToString(0));
		assertEquals("February", HelperCalendar.enMonthToString(1));
		assertEquals("March", HelperCalendar.enMonthToString(2));
		assertEquals("April", HelperCalendar.enMonthToString(3));
		assertEquals("May", HelperCalendar.enMonthToString(4));
		assertEquals("June", HelperCalendar.enMonthToString(5));
		assertEquals("July", HelperCalendar.enMonthToString(6));
		assertEquals("August", HelperCalendar.enMonthToString(7));
		assertEquals("September", HelperCalendar.enMonthToString(8));
		assertEquals("October", HelperCalendar.enMonthToString(9));
		assertEquals("November", HelperCalendar.enMonthToString(10));
		assertEquals("December", HelperCalendar.enMonthToString(11));
	}
	
	/**
	 * Tests if an empty string is returned if the month is outside the range.
	 * 
	 * @see org.testsuite.helper.HelperCalendar#enMonthToString(int)
	 */
	@Test
	public void testEnMonthToStringWithWrongParameterReturnIsEmptyString() {
		assertTrue(HelperCalendar.enMonthToString(-1).isEmpty());
		assertTrue(HelperCalendar.enMonthToString(12).isEmpty());
	}

}
