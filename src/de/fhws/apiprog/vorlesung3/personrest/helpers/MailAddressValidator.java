package de.fhws.apiprog.vorlesung3.personrest.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailAddressValidator {
	
	protected static Pattern mailPattern = Pattern.compile(
		"^[A-Z0-9._%+-]+@[A-Z0-9\\.-]+\\.[A-Z]+$",
		Pattern.CASE_INSENSITIVE
	);
	
	public static boolean isValid(String emailAddress)
	{
		Matcher mailMatcher = MailAddressValidator.mailPattern.matcher(emailAddress);
		return mailMatcher.matches();
	}
	
}
