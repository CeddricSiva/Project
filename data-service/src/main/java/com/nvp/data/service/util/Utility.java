package com.nvp.data.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utility {

	public static boolean isEmptyOrNull(String parm) {		
		return parm == null || parm.trim().isEmpty();
	}
	
	public static String trimToEmpty(String str) {
		return (str != null) ? str.trim() : ""; 
	}
	
	/**
	 * Method to convert the String date to LocalDate object using provided date format
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static LocalDate parseDate(String date, String dateFormat) {
		if(date == null) return null;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat,Locale.US);
		return LocalDate.parse(date, dateTimeFormatter);
	}
	
	/**
	 * Method to convert the String dateTime to LocalDateTime object using provided date format
	 * @param dateTime
	 * @param dateFormat
	 * @return
	 */
	public static LocalDateTime parseDateTime(String dateTime, String dateFormat) {
		if(dateTime == null) return null;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat,Locale.US);
		return LocalDateTime.parse(dateTime, dateTimeFormatter);
	}
	
	/**
	 * Method to convert the LocalDate object to String Date using provided date format
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getDateAsString(LocalDate date, String dateFormat) {
		if(date == null) return null;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		return dateTimeFormatter.format(date);
	}
	
	/**
	 * Method to convert the LocalDateTime object to String DateTime using provided date format
	 * @param dateTime
	 * @param dateFormat
	 * @return
	 */
	public static String getDateAsString(LocalDateTime dateTime, String dateFormat) {
		if(dateTime == null) return null;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		return dateTimeFormatter.format(dateTime);
	}
}
