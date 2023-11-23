package project3;
import java.lang.String;

/**
 * The Date class allows input as a string in the formats MM/DD/YYYY or MM/DD/YY
 * or as date parameters (month, day, year). 
 * @author  Mofoluwake Adesanya
 * @version 10/31/2023
 */
public class Date implements Comparable<Date>{
	// fields for saving parts of the date
	private int day, month, year;
	// Error message
	private String errorMessage = "This date is not valid.";
	/**
	 * Constructs a date string and matches it to the
	 * regular expressions MM/DD/YY or MM/DD/YYYY. A valid date must match the given regexes.
	 * The valid values for each part of the date are: month : 1 - 12 (inclusive);
	 * day : 1 to [31 for January, March, May, July, August, October and December,
	 * 30 for April, June, September and November, 29 for February in leap years, 
	 * and 28 for February in all other years]; year : 2000 - 2025 or 00 - 25 (YYYY/YY)
	 * Years in YY format would be converted to YYYY. Note that this
	 * does not affect the date because the date would always be printed 
	 * in MM/DD/YYYY form.
	 * @param String date 
	 * @throws IllegalArgumentException if date values are invalid.
	 */
 	public Date( String date ) throws IllegalArgumentException {
		// split to extract parts of the date
		String dateRegexA = "[0-1][0-9]/[0-3][0-9]/[0-2][0-9]"; 	// year is of YY format
		String dateRegexB = "[0-1][0-9]/[0-3][0-9]/20[0-2][0-9]";	// year is of YYYY format
		if ( date.matches(dateRegexA) || date.matches(dateRegexB) ) {
			String[] userValues = date.split("/");
			// save in temporary variable to perform operations
			this.month = Integer.parseInt(userValues[0]);
			this.day = Integer.parseInt(userValues[1]);
			this.year = Integer.parseInt(userValues[2]);
			// Prepend 20 if year is in form YY
			if ( (inRange(year, 0, 25)) ) { 
				String yearString = "20" + year; 
				year = Integer.parseInt(yearString);
			}
			if ( !(inRange(month, 1, 12)) || !(inRange(day, 1, 31)) || !(inRange(year, 2000, 2025))) 
			{ 	throw new IllegalArgumentException(errorMessage); }
			// Check dates if month is February
			if ( month == 2 ){ 
				// if year is a leap year, check day 
				if ( isLeapYear(year) ) {
					if (day > 29) { throw new IllegalArgumentException(errorMessage); }
				}
				else {
					if (day > 28) {	throw new IllegalArgumentException(errorMessage); }
				}
			}
			// If any other month besides February
			else {
				// April, June, September and November have only 30 days 
				int[] monthsWith30Days = {4, 6, 9, 11};
				for (int i : monthsWith30Days) {
					// compare given month
					if (month ==  i) {
						// throw exception if day > 30
						if (day > 30) {	throw new IllegalArgumentException(errorMessage); }
					}
				}
				// Other months have 31 days
				int[] monthsWith31Days = {1, 3, 5, 7, 8, 10, 12};
				// throw exception if day > 31
				for (int i : monthsWith31Days) {
					if (i > 31) { throw new IllegalArgumentException(errorMessage); }
				}
			}
		}		
		// Throw exception if date does not match given regex
		else {	throw new IllegalArgumentException(errorMessage); }
	}
	/**
	 * Constructs the numerical values of the date. The valid values for 
	 * each part of the date are:
	 * @param month : 1 - 12 (inclusive)
	 * @param day : 31 for January, March, May, July, August, October and December
	 * 				30 for April, June, September and November
	 * 				29 for February in leap years
	 * 				28 for February in all other years
	 * @param year : 2000 - 2025 ( does not accept years in form of YY)
	 * These are checked by the Date(String) constructor.
	 * @throws IllegalArgumentException if called with invalid values
	 */
	public Date (int month, int day, int year) throws IllegalArgumentException{
		this(month + "/" + day + "/" + year);
	}
	/**
	 * Checks if the given year is a leap year. 
	 * This is important for the month February thus it is only 
	 * called when checking dates with month "02".
	 * Mathematically, a leap year is a multiple of 400 and 
	 * a multiple of 4 but not 100.
	 * @param int year
	 * @return true if year is a leap year 
	 * 		   false otherwise
	 */
	private static boolean isLeapYear(int year) {
		// divisible by 400? Leap year!
		if (year % 400 == 0) return true;
		// else: divisible by 4 but not 100? 
		// Leap year!
		if ((year % 4 == 0) && (year % 100 != 0))
			return true;
		// False if no condition is met
		return false;
	}
	/**
	 * This function checks if a number is between a specified inclusive range.
	 * @param int number : number to check for
	 * @param int lower  : lower bound of range
	 * @param int upper : upper bound of range
	 * @return true if number is in range [lower, upper] : inclusive
	 */
	public static boolean inRange(int number, int lower, int upper) {
		return (number >= lower && number <= upper);
	}
	/**
	 * Compares two Date objects based on the equals() method. 
	 * @see equals()
	 *@param anotherDate Date instance
	 *@return 0 if equal i.e month, date and year are the same
	 *        1 if this date instance is earlier than anotherDate
	 *       -1 if this date instance is later than anotherDate
	 */
	@Override
	public int compareTo(Date anotherDate) throws ClassCastException {
		if ( !(anotherDate instanceof Date) ) {
			throw new ClassCastException("The input parameter must be a date.");
		}
		if (this == anotherDate) { return 0; }
		if (this != null && anotherDate == null) { return 1; }
		if (this == null && anotherDate != null){ return -1; }
		if ( this.year == anotherDate.getYear()) { 
			if ( this.month == anotherDate.getMonth() ) {
				if ( this.day == anotherDate.getDay() ) {
					return 0;
				}
				else if ( this.day < anotherDate.getDay() ) { return -1; }
				else { return 1; }
			}
			else if ( this.month < anotherDate.getMonth() ) { return -1; }
			else { return 1; }
		}
		else if ( this.year < anotherDate.getYear() ) { return -1; }
		else { return 1; }	
	}
	/**
	 * This function prints the date object.
	 * Years in YY format are already modified in both constructors.
	 * @return Date object in the format MM/DD/YYYY.
	 */
	@Override
	public String toString () {
		if (month < 10 ) {
			return "0" + month + "/" + day + "/" + year;
		}
		else if (day < 10) {
			return month + "/" + "0" +  day + "/" + year;
		}
		else if (month < 10 && day < 10) {
			return "0" + month + "/" + "0" + day + "/" + year;
		}
		return month + "/" + day + "/" + year;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return this.day;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return this.month;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return this.year;
	}
} 
