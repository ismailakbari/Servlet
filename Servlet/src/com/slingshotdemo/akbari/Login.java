package com.slingshotdemo.akbari;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to login to the platform.
 * @author Ismail Akbari
 *
 */
public class Login {
	
	//private Map<String, String> DB = new HashMap<String, String>();
	
	public Login(){
		
	}
	
	/**
	 * This method first hashes the username and password and then checks them against the records in the database. 
	 * @return 
	 *  1 : successful login
	 * -1 : authentication failed
	 * -2 : db connection failed
	 *  0 : other errors  
	 */
	public int login(String username, String password){
		
		
		int authCode = 0 ;//authentication code

		// remember to use the same SALT value use used while storing password
		// for the first time.
		Encryption enc =  new Encryption();	
		String hashedusername = enc.generateHash(username);
		String hashedPassword = enc.generateHash(password);		

		//authenticate against db
		//connect to db
		DbManager dbm = new DbManager();
		authCode = dbm.authenticate(hashedusername, hashedPassword);
		
		return authCode;
	}

}
