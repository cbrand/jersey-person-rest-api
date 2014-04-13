package de.fhws.apiprog.vorlesung3.personrest.helpers;

import java.util.regex.Pattern;

public class NumberHelper {

	protected static Pattern numberPattern = Pattern.compile(
			"^\\d+$",
			Pattern.CASE_INSENSITIVE
		);
	
	public static boolean isNumber(String number)
	{
		return numberPattern.matcher(number).matches();
	}
	
}
