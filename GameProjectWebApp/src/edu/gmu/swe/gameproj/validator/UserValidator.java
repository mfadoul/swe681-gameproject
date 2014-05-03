package edu.gmu.swe.gameproj.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.util.ExtraStringUtils;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;

public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> arg0) {
		return User.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User user = (User)obj;

		if ((user.getEmail()) == null || (user.getEmail().equals(""))) {
			errors.rejectValue("email", "requiredvalue", "The value is required.");

		} else {
			GameProjectRemote gameProject = SessionBeanHelper.getGameProjectSessionBean();
			if (gameProject.doesUserExist(user.getEmail())) {
				errors.rejectValue("email", "uniquevalue", "A user with this e-mail already exists.");
			} else {
				if (ExtraStringUtils.hasWhiteSpace(user.getEmail())) {
					errors.rejectValue("email", "haswhitespace", "Please remove all whitespace from the user name.");
				} else {
					if (!ExtraStringUtils.isValidEmail(user.getEmail())) {
						errors.rejectValue("email", "notvalidemail", "The e-mail does not appear to be valid.");
						System.err.println("Regex rejected the email field contents.");
					} else {
						if (user.getEmail().length() > 128) {
							errors.rejectValue("email", "stringtoolong", "The e-mail is too long.");							
						}
					}
				}
			}
		}
		// Last name
		if ((user.getLastname()) == null || (user.getLastname().equals(""))) {
			errors.rejectValue("lastname", "requiredvalue", "Please provide your last name.");
		} else {
			if (user.getLastname().length() > 32) {
				errors.rejectValue("lastname", "stringtoolong", "The last name is too long.");
			} else {
				if (false == ExtraStringUtils.isValidName(user.getLastname())) {
					errors.rejectValue("lastname", "invalidname", "The last name is not valid.");
					System.err.println("Regex rejected the lastname field contents.");
				}
			}
		}
		// First name
		if ((user.getFirstname()) == null || (user.getFirstname().equals(""))) {
			errors.rejectValue("firstname", "requiredvalue", "Please provide your first name.");
		} else {
			if (user.getFirstname().length() > 32) {
				errors.rejectValue("firstname", "stringtoolong", "The first name is too long.");
			} else {
				if (false == ExtraStringUtils.isValidName(user.getFirstname())) {
					errors.rejectValue("firstname", "invalidname", "The first name is not valid.");
					System.err.println("Regex rejected the firstname field contents.");
				}
			}
		}
		
		// Check the salutation
		if (user.getSalutation() != null) {
			if (false == ExtraStringUtils.isValidSalutation(user.getSalutation())) {
				errors.rejectValue("salutation", "invalidsalutation", "The salutation is not valid.");
				System.err.println("Regex rejected the salutation field contents.");
			}
		}

		// Check the "about me" field
		if (user.getAboutme() != null) {
			if (false == ExtraStringUtils.isValidTextField(user.getAboutme())) {
				errors.rejectValue("aboutme", "invalidaboutme", "The contents of about me are not valid.");
				System.err.println("Regex rejected the aboutme field contents.");
			}
		}

		// Password
		if ((user.getPassword()) == null || (user.getPassword().equals(""))) {
			errors.rejectValue("password", "requiredvalue", "A password is required is required.");
		} else {
			if (ExtraStringUtils.hasWhiteSpace(user.getPassword())) {
				errors.rejectValue("password", "haswhitespace", "A valid password cannot have whitespace.");
			} else {
				if (user.getPassword().length() > 32) {
					errors.rejectValue("password", "stringtoolong", "A valid password should be no more than 32 characters.");
				} else {
					if (user.getPassword().length() < 8) {
						errors.rejectValue("password", "stringtooshort", "A valid password should be no less than 8 characters.");
					}
				}
			}
		}
	}
}
