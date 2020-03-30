package com.raghav.thymeleaf.utility;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateParser {
	
	/*
	 * 
	 * Parse String to LocalDate and then SqlDate
	 * 
	 */

	public static LocalDate localDateParser(String date) {

		// String date = "1987-03-22";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public static Date sqlDateParser(String date) {

		LocalDate localDate = localDateParser(date);
		Date sqlDate = Date.valueOf(localDate);
		return sqlDate;
	}
	
	public static java.util.Date utilDateParser(String strDate) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = formatter.parse(strDate);
		return date;
	}

}
