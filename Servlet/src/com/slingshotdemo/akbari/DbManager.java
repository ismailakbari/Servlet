package com.slingshotdemo.akbari;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for any kind of communication with the database.
 * @author Ismail Akbari
 *
 */
public class DbManager {
	
	public DbManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * This method returns connection to the {@code dbName} db;
	 * @param dbName
	 * db to connect to
	 * @return
	 * Returns a connection to the {@code dbName} db;
	 */
	private Connection getConnection(String dbName){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/"+dbName, "root", "root");
			return conn;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	/**
	 * connects to the 'slingshot' database and checks the hashed username and password against the records in the 
	 * credentials table.
	 * @param hashedUsername
	 * @param hashedPassword
	 * @return
	 * returns -2 if there was a database error, 1 if the authentication is successful and -1 otherwise.
	 */
	public int authenticate(String hashedUsername, String hashedPassword) {
		// TODO Auto-generated method stub
		int authCode = 0; //authentication code
		Connection conn = getConnection("slingshot");
		if(conn==null)
			authCode = -2; // could not connect to db
		
		try {
			// SQL SELECT query.	    
		    String query = "SELECT COUNT(username) FROM credentials WHERE username='"+hashedUsername+"' "
		    		+ "AND password='"+hashedPassword+"' ;";	   
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			ResultSet rs = st.executeQuery(query);
			int result = 0;
			while (rs.next())
			  result = rs.getInt(1);
			System.out.println("reslt set size = "+ result);
			if(result==0)
				authCode = -1;
			else
				authCode = 1;		
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		
		    // iterate through the java resultset
		   /*while (rs.next()){
		        int id = rs.getInt("id");
		        String firstName = rs.getString("first_name");
		        String lastName = rs.getString("last_name");
		        Date dateCreated = rs.getDate("date_created");
		        boolean isAdmin = rs.getBoolean("is_admin");
		        int numPoints = rs.getInt("num_points");
		        
		        // print the results
		        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
		    }*/
		
	
		
		return authCode;
	}
	/**
	 * This method adds an admin manager to the credentials table.
	 * @param info
	 * manager information including a map of (credentialsID, firstName, lastName, username, password, email) key/values. 
	 * @return
	 * returns true if manager was added successfully. 
	 */
	public boolean addManager(Map<String, String> info){
		boolean add = false; 
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return false; // could not connect to db
		
		Encryption enc =  new Encryption();	
		String hashedusername = enc.generateHash(info.get("username").toLowerCase()); //username (lower/upper) case should not matter
		String hashedPassword = enc.generateHash(info.get("password"));	
		
		try {
			// SQL SELECT query.	    
		    String query = "INSERT INTO credentials (firstName, lastName, username, password, email) VALUES"
		    		+ " ('"+info.get("firstName")+"','"+info.get("lastName")+"','"+hashedusername+"','"+hashedPassword+"','"+info.get("email")+"');";   
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			int result = st.executeUpdate(query);
			
			if(result==0)
				add = false;
			else
				add = true;		
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
	
		return add;		
	}
	
	/**
	 * This method searches customer(s) based on the fields in the search form in manageCustomer.jsp page.
	 * @param parameters
	 *  a map includes key/values of search parameters.
	 * @return
	 * returns the customers matched to the criteria (parameters)
	 * The parameters argument includes the following customer info:
	 * ID, first name, middle name, last name,
	 * address, city, province, country, postal code,
	 * cell, home, work, email and alternative email. 
	 * 
	 */
	public List<Customer> search(Map<String, String> parameters){
		
		
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return null; // could not connect to db
		
		// SQL SELECT query.	    
	    String query = "SELECT customers.customerID, firstName, middleName, lastName,"
	    		+ " address, city, province, country, postalCode, "
	    		+ "cellPhone, homePhone, workPhone, email, emailAlt from customers, address, contacts WHERE "
	    		+ "customers.customerID=" + parameters.get("customerID") +" AND ";
	    
	   // if(parameters.get("customerID").length()>0)
	    //	query += " customerID = " + parameters.get("customerID") +" AND " ;	    		    
	    if(parameters.get("firstName").length()>0)
	    	query += " firstName = '" + parameters.get("firstName") +"' AND " ;	  
	    if(parameters.get("middleName").length()>0)
	    	query += " middleName = '" + parameters.get("middleName") +"' AND " ;	 
	    if(parameters.get("lastName").length()>0)
	    	query += " lastName = '" + parameters.get("lastName") +"'" ;	 
	    query = query.trim();
	    if(query.endsWith("AND"))
	    	query = query.substring(0, query.length()-3);
	    query = query.trim() + " AND (customers.customerID = address.customerID) AND (customers.customerID = contacts.customerID);";
	    System.out.println("query="+query);
	    List<Customer> customerList = new ArrayList<Customer>();
	    
   	
		try {
				   
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				Customer cust= new Customer();
				cust.address = new Address();
				cust.contact = new Contact();
			  cust.customerID = rs.getInt("customerID");
			  cust.firstName = rs.getString("firstName");
			  cust.middleName = rs.getString("middleName");
			  cust.lastName = rs.getString("lastName");
			  cust.address.address = rs.getString("address");
			  cust.address.city = rs.getString("city");
			  cust.address.province = rs.getString("province");
			  cust.address.country = rs.getString("country");
			  cust.address.postalCode = rs.getString("postalCode");
			  cust.contact.cellPhone = rs.getString("cellPhone");
			  cust.contact.homePhone = rs.getString("homePhone");
			  cust.contact.workPhone = rs.getString("workPhone");
			  cust.contact.email = rs.getString("email");
			  cust.contact.emailAlt = rs.getString("emailAlt");
			  customerList.add(cust);				
			}	
			System.out.println("reslt set size = "+ customerList.size());
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		
		    // iterate through the java resultset
		   /*while (rs.next()){
		        int id = rs.getInt("id");
		        String firstName = rs.getString("first_name");
		        String lastName = rs.getString("last_name");
		        Date dateCreated = rs.getDate("date_created");
		        boolean isAdmin = rs.getBoolean("is_admin");
		        int numPoints = rs.getInt("num_points");
		        
		        // print the results
		        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
		    }*/
		
	
	      
		
		return customerList;
	}
	
	/**
	 * This method searches customer(s) based on the fields in the search form in manageCustomer.jsp page.
	 * @param parameters
	 *  a map includes key/values of search parameters.
	 * @return
	 * returns the customers matched to the criteria (parameters)
	 * The parameters argument includes the following customer info:
	 * ID, first name, middle name, last name,
	 * address, city, province, country, postal code,
	 * cell, home, work, email and alternative email. 
	 * 
	 */
	public List<Customer> search2(Map<String, String> parameters){
		
		
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return null; // could not connect to db
		System.out.println(parameters);
		// SQL SELECT query.	    
	    String query = "SELECT customerID, firstName, middleName, lastName "
	    		+" FROM customers  WHERE " ;
	    	    
	    if(parameters.get("customerID").length()>0)
	    	query += " customerID = " + parameters.get("customerID") +" AND " ;	    		    
	    if(parameters.get("firstName").length()>0)
	    	query += " firstName = '" + parameters.get("firstName") +"' AND " ;	  
	    if(parameters.get("middleName").length()>0)
	    	query += " middleName = '" + parameters.get("middleName") +"' AND " ;	 
	    if(parameters.get("lastName").length()>0)
	    	query += " lastName = '" + parameters.get("lastName") +"'" ;	 
	    query = query.trim();
	    if(query.endsWith("AND"))
	    	query = query.substring(0, query.length()-3);
	    query = query.trim();
	    if(query.endsWith("WHERE"))
	    query = query.substring(0, query.lastIndexOf("WHERE")) +";";
	    System.out.println("query="+query);
	    List<Customer> customerList = new ArrayList<Customer>();
	   
   	
		try {
				   
		    // create the java statement
		    Statement st1 = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			ResultSet rs1 = st1.executeQuery(query);
			
			String query2, query3;
			Statement st2 = conn.createStatement();
			Statement st3 = conn.createStatement();
			ResultSet rs2;// = st.executeQuery(query);
			ResultSet rs3;// = st.executeQuery(query);
			
			while (rs1.next()){
				Customer cust= new Customer();
				cust.address = new Address();
				cust.contact = new Contact();
			  cust.customerID = rs1.getInt("customerID");
			  cust.firstName = rs1.getString("firstName");
			  cust.middleName = rs1.getString("middleName");
			  cust.lastName = rs1.getString("lastName");
			  
			  // SQL SELECT query.	    
			  query2 = "SELECT address, city, province, country, postalCode " //query should return not more than one record
			   		+" FROM address  WHERE customerID="+cust.customerID +";" ;
			  rs2 = st2.executeQuery(query2); 
			  if(rs2.next()){
				  cust.address.address = rs2.getString("address");
				  cust.address.city = rs2.getString("city");
				  cust.address.province = rs2.getString("province");
				  cust.address.country = rs2.getString("country");
				  cust.address.postalCode = rs2.getString("postalCode");
			  }
			  
			  query3 = "SELECT cellPhone, homePhone, workPhone, email, emailAlt "  //query should return not more than one record
				   		+" FROM contacts  WHERE customerID="+cust.customerID +";" ;		
			  rs3 = st3.executeQuery(query3);
			  if(rs3.next()){
				  cust.contact.cellPhone = rs3.getString("cellPhone");
				  cust.contact.homePhone = rs3.getString("homePhone");
				  cust.contact.workPhone = rs3.getString("workPhone");
				  cust.contact.email = rs3.getString("email");
				  cust.contact.emailAlt = rs3.getString("emailAlt");
			  }
			  
			  customerList.add(cust);				
			}//while	
			System.out.println("reslt set size = "+ customerList.size());			
			st1.close();
			st2.close();
			st3.close();
			conn.close();
		}	
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 	
		
		return customerList;
	}
	/**
	 * This method updates a customer.
	 * @param customerInfo
	 *  customer info in an array.The argument includes the following customer info:
	 * ID, first name, middle name, last name,
	 * address, city, province, country, postal code,
	 * cell, home, work, email and alternative email. 
	 * @return
	 * returns 1 if update was successful and 0 otherwise.
	 * 
	 * 
	 */
	public int updateCustomer(String [] customerInfo){
		/*int nl=0;
		for(int i=0; i<customerInfo.length; i++)
			if(customerInfo[i].equalsIgnoreCase("null")){
				customerInfo[i] = null;
				nl++;
			}
		System.out.println("nulls="+nl);*/
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return 0; // could not connect to db
		int result1=0, result2=0, result3=0;
		// SQL SELECT query.	    
		String query1 = "UPDATE customers as c "
				+ " SET c.customerID= "+(Integer.valueOf(customerInfo[0]))+", c.firstName='"+customerInfo[1]+"', c.middleName="+checkNULL(customerInfo[2])+" , c.lastName='"+customerInfo[3]+"'"
				+ " WHERE c.customerID= "+(Integer.valueOf(customerInfo[0])) +";" ;
	    String query2 = "UPDATE address as a "
	    		+ " SET a.customerID= "+(Integer.valueOf(customerInfo[0]))+", a.address='"+customerInfo[4]+"', a.city="+checkNULL(customerInfo[5])+" , a.province="+checkNULL(customerInfo[6])
	    		+ ", a.country="+checkNULL(customerInfo[7])+", a.postalCode="+checkNULL(customerInfo[8])+" WHERE a.customerID = "+(Integer.valueOf(customerInfo[0])) +";" ;
	    String query3 = "UPDATE contacts as con "
	    		+ " SET con.customerID= "+(Integer.valueOf(customerInfo[0]))+", con.cellPhone="+checkNULL(customerInfo[9])+", con.homePhone="+checkNULL(customerInfo[10])+" , con.workPhone="+checkNULL(customerInfo[11])
	    		+ ", con.email="+checkNULL(customerInfo[12])+", con.emailAlt="+checkNULL(customerInfo[13])+" WHERE con.customerID = "+(Integer.valueOf(customerInfo[0])) +";" ;
	    
	    System.out.println(query1);
	    System.out.println(query2);
	    System.out.println(query3);
	    try{
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			result1 = st.executeUpdate(query1);
			result2 = st.executeUpdate(query2);
			result3 = st.executeUpdate(query3);
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    System.out.println("result="+result1+result2+result3);
		return (result1+result2+result3 ==3) ? 1 : 0;
	}
	/**
	 * This method adds a customer to the existing customers. customerID must be unique. 
	 * @param customerInfo
	 *  customer info in an array.The argument includes the following customer info:
	 * ID, first name, middle name, last name,
	 * address, city, province, country, postal code,
	 * cell, home, work, email and alternative email. 
	 * @return
	 * returns 1 if addition was successful and 0 otherwise.
	 * 
	 */
	public int addCustomer(String [] customerInfo){
		System.out.println("add length="+ customerInfo.length);
		/*int nl=0;
		for(int i=0; i<customerInfo.length; i++)
			if(customerInfo[i].equalsIgnoreCase("null")){
				customerInfo[i] = null;
				nl++;
			}
		System.out.println("nulls="+nl);*/
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return 0; // could not connect to db
		int result1=0, result2=0, result3=0;
		// SQL SELECT query.	    
		String query1 = "INSERT INTO customers "
				+"(customerID, firstName, middleName, lastName) VALUES ("
				+(Integer.valueOf(customerInfo[0]))+", '" + customerInfo[1] + "' , " + checkNULL(customerInfo[2])+", '"+ customerInfo[3] +"');" ;
		
	    String query2 = "INSERT INTO address "
	    		+"(customerID, address, city, province, country, postalCode) VALUES ( "
	    		+ (Integer.valueOf(customerInfo[0]))+", '" + customerInfo[4] +"', "+ checkNULL(customerInfo[5]) +", " + checkNULL(customerInfo[6]) + ", "
	    		+ checkNULL(customerInfo[7]) +", " + checkNULL(customerInfo[8]) + ") ; " ; 
	    		
	    		
	    String query3 = "INSERT INTO contacts "
	    		+ "(customerID, cellPhone, homePhone, workPhone, email, emailAlt) VALUES ("
	    		+ (Integer.valueOf(customerInfo[0]))+", " + checkNULL(customerInfo[9]) +", "+ checkNULL(customerInfo[10]) +", " + checkNULL(customerInfo[11]) + ", "
	    		+ checkNULL(customerInfo[12]) +", " + checkNULL(customerInfo[13]) + ") ; " ;
	    
	    System.out.println(query1);
	    System.out.println(query2);
	    System.out.println(query3);
	    try{
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			result1 = st.executeUpdate(query1);
			result2 = st.executeUpdate(query2);
			result3 = st.executeUpdate(query3);
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    System.out.println("result="+result1+result2+result3);
		return (result1+result2+result3 ==3) ? 1 : 0;
	}
	
	/**
	 * This method removes a customer from the list of existing customers. 
	 * @param customerInfo
	 *  customer info in an array.The argument includes the following customer info:
	 * ID, first name, middle name, last name,
	 * address, city, province, country, postal code,
	 * cell, home, work, email and alternative email. 
	 * @return
	 * returns 1 if deletion was successful and 0 otherwise.
	 * 
	 */
	public int deleteCustomer(String [] customerInfo){
		System.out.println("add length="+ customerInfo.length);
		/*int nl=0;
		for(int i=0; i<customerInfo.length; i++)
			if(customerInfo[i].equalsIgnoreCase("null")){
				customerInfo[i] = null;
				nl++;
			}
		System.out.println("nulls="+nl);*/
		Connection conn = getConnection("slingshot");
		if(conn==null)
			return 0; // could not connect to db
		int result=0;//, result2=0, result3=0;
		// SQL SELECT query.	    
		String query = "DELETE FROM customers WHERE "
				+ " customerID="+ Integer.valueOf(customerInfo[0]) +" ;" ;
	
	    System.out.println(query);
	
	    try{
		    // create the java statement
		    Statement st = conn.createStatement();			     
		    // execute the query, and get a java resultset		   
			result = st.executeUpdate(query);
		
			
			st.close();
			conn.close();
		}	
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    System.out.println("result="+result);
		return result;
	}
	/**
	 * This is a method that processes a potentially null table field. 
	 * @param field
	 * @return
	 * if the argument is null it returns 'NULL'
	 * otherwise it returns the argument itself.
	 *
	 */
	public String checkNULL(String field){		 
		if (field.equalsIgnoreCase("null") || field==null || field.length()==0)
			return "NULL";
		return "'" + field+"'";
	}
	
}
