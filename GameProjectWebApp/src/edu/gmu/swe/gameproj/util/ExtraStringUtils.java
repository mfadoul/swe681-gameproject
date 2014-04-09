package edu.gmu.swe.gameproj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraStringUtils {
	public static boolean hasWhiteSpace(String inputString) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(inputString);
		//System.out.println("Checked string [" + inputString + "].  Return value is " + matcher.find());
		return matcher.find();
	}
	public static boolean isValidEmail(String inputString) {	 
		final String emailPattern = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(inputString);

		return matcher.matches();
	}
}
