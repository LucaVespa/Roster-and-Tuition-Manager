package com.tuitionpackage.demo;
import java.util.Calendar;

/**
 * This class creates a properly formatted date and checks whether the date inputted by the user is valid.
 * 
 */
public class Date implements Comparable<Date> {

    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final int EQUAL_TO = 0;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;
    private static final int MIN_YEAR = 1;
    private static final int MAX_DAYS = 31;
    private static final int MIN_DAYS = 1;
    private static final int JANUARY = 1;
    private static final int DECEMBER = 12;
    private static final int DAYS_IN_JANUARY = 31;
    private static final int DAYS_IN_FEBRUARY = 28;
    private static final int DAYS_IN_FEBRUARY_LEAP = 29;
    private static final int DAYS_IN_MARCH = 31;
    private static final int DAYS_IN_APRIL = 30;
    private static final int DAYS_IN_MAY = 31;
    private static final int DAYS_IN_JUNE = 30;
    private static final int DAYS_IN_JULY = 31;
    private static final int DAYS_IN_AUGUST = 31;
    private static final int DAYS_IN_SEPTEMBER = 30;
    private static final int DAYS_IN_OCTOBER = 31;
    private static final int DAYS_IN_NOVEMBER = 30;
    private static final int DAYS_IN_DECEMBER = 31;
    private static final int REQUIRED_AGE = 16;
    private static final int FEBRUARY = 2;
    private static final int MARCH = 3;
    private static final int APRIL = 4;
    private static final int MAY = 5;
    private static final int JUNE = 6;
    private static final int JULY = 7;
    private static final int AUGUST = 8;
    private static final int SEPTEMBER = 9;
    private static final int OCTOBER = 10;
    private static final int NOVEMBER = 11;
    private static final int CALENDAR_CLASS_OFFSET = 1; // This variable accounts for the fact that months in the Calendar class go from 0(Jan)-11(December)

    private int year;
    private int month;
    private int day;

    /**
     * Creates an instance of Date which represents the present date.
     * Constructor for when no parameters are give.
     */
    public Date() {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get((Calendar.YEAR));
        this.month = calendar.get(Calendar.MONTH) + CALENDAR_CLASS_OFFSET; // months in calendar go from 0(Jan)-11(December)
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Creates an instance of Date which represents the date provided in the String.
     * @param date the String representation of the date.
     */
    public Date(String date) {
        String[] splitDate = date.split("/");
        this.month = Integer.parseInt(splitDate[0]);
        this.day = Integer.parseInt((splitDate[1]));
        this.year = Integer.parseInt(splitDate[2]);
    }

    /**
     * Gets the year associated with the date.
     * @return this date's year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the month associated with the date.
     * @return this date's month.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Gets the day associated with the date.
     * @return this date's day.
     */
    public int getDay() {
        return this.day;
    }

    /**
     * An override of the equals method.
     * @param object of type Date.
     * @return true if the two dates are identical.
     */
    @Override
    public boolean equals (Object object) {
        if (!(object instanceof Date)) {
            return false;
        }
        Date checkDate = (Date) object;
        return ((this.day == checkDate.getDay()) && (this.month == checkDate.getMonth()) && (this.year == checkDate.year));
    }

    /**
     * An override of the compareTo method.
     * @param date the object to be compared.
     * @return 0 if the two dates are identical, 1 if this date is an older calendar date than the date provided, -1 otherwise.
     */
    @Override
    public int compareTo(Date date) {
        if (date.year > this.year) {
            return GREATER_THAN;
        }
        if (date.year < this.year) {
            return LESS_THAN;
        }
        if (date.month > this.month) {
            return GREATER_THAN;
        }
        if (date.month < this.month) {
            return LESS_THAN;
        }
        if (date.day > this.day) {
            return GREATER_THAN;
        }
        if (date.day < this.day) {
            return LESS_THAN;
        }
        return EQUAL_TO;
    }

    /**
     * An override of the toString method.
     * @return a String representation of the date.
     */
    @Override
    public String toString() {
        String date = "";
        date = date + Integer.toString(this.month) + "/";
        date = date + Integer.toString(this.day) + "/";
        date = date + Integer.toString(this.year);
        return date;
    }

    /**
     * Checks if this year is a leap year.
     * @return true if this year is a leap year, false otherwise.
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL != 0) {
                return true; // Leap Year: Divisible by 4 and not 100
            }
            if (year % QUATERCENTENNIAL == 0) {
                return true; // Divisible by 4 and 100 - Leap Year if divisible by 400 not Leap Year otherwise
            }
        }
        return false;
    }

    /**
     * Checks if the date is a valid calendar date, meaning that it uses existing months and days that exist within those months.
     * Also checks that the date is not too old of a calendar date.
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() {
        Calendar calendar = Calendar.getInstance();
        if ((month < JANUARY || month > DECEMBER) || (day > MAX_DAYS || day < MIN_DAYS) || (year > calendar.get(Calendar.YEAR) || year < MIN_YEAR)) {
            return false;
        }
        if (month == JANUARY) return (day <= DAYS_IN_JANUARY);
        if (month == FEBRUARY) { // have to check for leap year
            if (isLeapYear()) {
                return (day <= DAYS_IN_FEBRUARY_LEAP);
            } else {
                return (day <= DAYS_IN_FEBRUARY);
            }
        }
        if (month == MARCH) return (day <= DAYS_IN_MARCH);
        if (month == APRIL) return (day <= DAYS_IN_APRIL);
        if (month == MAY) return (day <= DAYS_IN_MAY);
        if (month == JUNE) return (day <= DAYS_IN_JUNE);
        if (month == JULY) return (day <= DAYS_IN_JULY);
        if (month == AUGUST) return (day <= DAYS_IN_AUGUST);
        if (month == SEPTEMBER) return (day <= DAYS_IN_SEPTEMBER);
        if (month == OCTOBER) return (day <= DAYS_IN_OCTOBER);
        if (month == NOVEMBER) return (day <= DAYS_IN_NOVEMBER);
        if (month == DECEMBER) return (day <= DAYS_IN_DECEMBER);
        return true;
    }

    /**
     * Checks if the given date is at least 16 years from the current date.
     * @return true if the date is at least 16 years from te current date, false otherwise.
     */
    public boolean isOldEnough () {
        Date today = new Date();
        if(today.getYear() - this.getYear() > REQUIRED_AGE) {
            return true;
        }else if(today.getYear() - this.getYear() == REQUIRED_AGE) {
            if(today.getMonth() > this.getMonth()) {
                return true;
            }else if(today.getMonth() == this.getMonth() && today.getDay() >= this.getDay()) {
                return true;
            }
        }
        return false;
    }
}
