package de.fhws.apiprog.vorlesung3.personrest.helpers;

import java.util.regex.Pattern;

public class NumberHelper {

	protected static Pattern numberPattern = Pattern.compile(
			"^\\d+$",
			Pattern.CASE_INSENSITIVE
		);
	protected static Pattern doublePattern = Pattern.compile(
			"^\\d+\\.?\\d*$",
			Pattern.CASE_INSENSITIVE
		);
	
	public static boolean isNumber(String number)
	{
		return numberPattern.matcher(number).matches();
	}
	
	public static boolean isDouble(String value)
	{
		return doublePattern.matcher(value).matches();
	}
	
}
