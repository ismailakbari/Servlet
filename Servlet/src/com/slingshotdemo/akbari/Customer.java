package com.slingshotdemo.akbari;

/**
 * A class that defines customer details. This class has an equivalent table named 'customers' in 'slingshot' database.
 * The class/table fields include (customerID, firstName, middleName, lastName, address, contact). 
 * customer's address and contact are kept in 'address' and 'contacts' table from 'slingshot' database respectively. 
 * 'slingshot' DB includes (customer, address, contact, credentials) tables.
 * credentials table holds the manager authentication information.
 * database name : 'slingshot'
 * tables: 
 * customer(customerID, firstName, middleName, lastName, address, contact)
 * address(addressID, customerID, address, city, province, country, postalCode)
 * contacts(contactID, customerID, cellPhone, homePhone, workPhone, email, emailAlt)
 * credentials(credentialsID, firstName, lastName, username, password, email, dateCreated, dateAccessed, dateModified)
 * hash values of username and password are kept in the database instead of their actual values. 
 * @author ismail akbari
 *
 */
public class Customer {
	public Customer(){
		
	}
	public  int customerID;
	public String firstName, middleName, lastName;
	public Address address;
	public Contact contact;

}
