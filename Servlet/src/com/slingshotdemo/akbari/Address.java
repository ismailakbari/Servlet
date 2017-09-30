package com.slingshotdemo.akbari;

/**
 * A class that defines customer's address details. This class has a table with the same name in the database.
 * this class is used to insert/retrieve address from the 'address' table in 'slingshot' database.  
 * The class/table fields include (addressID, customerID, address, city, province, country, postalCode).
 * 
 * @author ismail akbari
 *
 */
public class Address {
	
	public Address(){		
	}
	
	public  int addressID, customerID;
	public String address, city, province, country, postalCode;

}
