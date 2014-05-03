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
	
	// For Salutation
	public static boolean isValidSalutation(String inputString) {
		final String salutationPattern = 
				"^[A-Z][a-z]{0,3}.?$";
		 
			Pattern pattern = Pattern.compile(salutationPattern);
			Matcher matcher = pattern.matcher(inputString);

			return matcher.matches();
	}
	
	// For first or last names
	public static boolean isValidName(String inputString) {
		final String namePattern = 
				"^[A-Za-z'-]{2,32}$";
		 
			Pattern pattern = Pattern.compile(namePattern);
			Matcher matcher = pattern.matcher(inputString);

			return matcher.matches();
	}
	
	// For "About me" field
	public static boolean isValidTextField(String inputString) {
		final String textFieldPattern = 
				"^[A-Za-z',.?!\\s-]{0,256}$";
		 
			Pattern pattern = Pattern.compile(textFieldPattern);
			Matcher matcher = pattern.matcher(inputString);

			return matcher.matches();
	}
	
    //Check if string is null or only contains whitespace
    public static boolean isNullOrBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
