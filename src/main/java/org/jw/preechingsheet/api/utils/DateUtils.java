package org.jw.preechingsheet.api.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {
	private DateUtils() {
		
	}
	
	public static String getMonthDescription(LocalDate date) {
		return date.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.of("es", "ES"))
                .substring(0, 1).toUpperCase() +
                date.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.of("es", "ES"))
                .substring(1);
	}
}
