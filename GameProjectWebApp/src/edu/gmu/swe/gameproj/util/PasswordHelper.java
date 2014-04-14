package edu.gmu.swe.gameproj.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordHelper {	
	
	// Return an encrypted password for persistence.
	public static String getEncryptedPassword (String plaintextPassword) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(plaintextPassword);
		return hashedPassword;
	}
	
	// If the plaintext password matches the hashed password, return true.
	public static boolean comparePasswords (String plaintextPassword, String hashedPassword) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(plaintextPassword, hashedPassword);
	}
}
