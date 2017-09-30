package com.slingshotdemo.akbari;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to hash usernames and passwords. It uses a salt value and Java message digest to hash  a username/password.
 * The username/password is hashed using SHA-256 encryption method.
 * @author Ismail Akbari
 *
 */
public class Encryption {

	private static final String SALT = "slingshot-demo";
	
	public Encryption() {
		
	}
	
	/*
	public static void main(String [] args){
		//System.out.println(generateHash("hello"));
		//System.out.println(generateHash("hello"));
	}*/
	
	/**
	 * generates a SHA-256 hash of the input
	 * @param input
	 * @return
	 * returns the hashed value of the input as a string.
	 */
	public String generateHash(String input) {
		
		String saltedInput = SALT.concat(input);
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha.digest(saltedInput.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}

	
	
	

}
