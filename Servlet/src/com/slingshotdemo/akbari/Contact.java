package com.slingshotdemo.akbari;

/**
 * A class that defines customer's contact information. This class has an equivalent table with the 'contacts' name in the database.
 * this class is used to insert/retrieve contact info from the 'contacts' table in 'slingshot' database.  
 * The class/table fields include (contactID, customerID, cellPhone, homePhone, workPhone, email, emailAlt).
 * @author ismail akbari
 *
 */
public class Contact {
	public Contact(){
		
	}
	public  int contactID, customerID ;
	public String cellPhone, homePhone, workPhone, email, emailAlt;

}
