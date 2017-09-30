<%@page import="java.util.ArrayList"%>
<%@page import="com.slingshotdemo.akbari.DbManager, com.slingshotdemo.akbari.Customer"%>
<%@page import="java.util.HashMap, java.util.Map, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

    
<!DOCTYPE html PUBLIC "-//W3C//Dth HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dth">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Result</title>

<script type="text/javascript" src="js/search.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

	<% /* read the search form values*/
		Map<String, String[]> parameters = request.getParameterMap(); 
		Map<String, String> parameters2 = new HashMap<String, String>();
		//List<String> values = new ArrayList<String>();
		String value="";
		String td="";
		
		for(String parameter : parameters.keySet()) {		    
	        parameters2.put(parameter, parameters.get(parameter)[0]);	        
	    }		
		DbManager dbm = new DbManager();
		/*for(String parameter : parameters2.keySet()) {		    
			System.out.println(parameter +"="+parameters2.get(parameter));	        
	    }	
		System.out.println("p2="+parameters2.size());*/
		List<Customer> customerList = dbm.search2(parameters2);
		int size = customerList.size();
	%>
	<center id="searchCenterTop">search returned <%= size %> result(s)</center> 
	<table id="tableResult">
		<thead>
	    	<tr class="head">
	    		<th style="background-color:#45B39D">#</th><th>ID</th><th>First Name</th><th>Middle Name</th><th>Last Name</th>   
	    		<th>Address</th><th>City</th><th>Province</th><th>Country</th><th>Postal Code</th>
	    		<th>Cell</th><th>Home</th><th>Work</th><th>email</th><th>Alternative email</th><th colspan="2" style="background-color:#45B39D">ACTIONS</th> 	
	    	</tr>
	 	</thead>
		<tbody>		
	 		
 			<%
 				int i=0;
				for(i=0; i<size; i++){
					Customer cust = customerList.get(i);
					%><tr>
						  <td style="color:white; background-color:#45B39D"><%= i+1 %></td><td><%= cust.customerID %></td><td><%= cust.firstName %></td><td><%= cust.middleName %></td>
					  	  <td><%= cust.lastName %></td><td><%= cust.address.address %></td><td><%= cust.address.city %></td>
					  	  <td><%= cust.address.province %></td><td><%= cust.address.country %></td><td><%= cust.address.postalCode %></td>
					  	  <td><%= cust.contact.cellPhone %></td><td><%= cust.contact.homePhone %></td><td><%= cust.contact.workPhone %></td>
					  	  <td><%= cust.contact.email %></td><td><%= cust.contact.emailAlt %></td>
					  	  <td style="background-color:#45B39D">
					  	  	<button name="editButton" style="font-size:36px; color:#45B39D" onclick="editCustomer(this)"> <i class="material-icons">mode_edit</i></button>
					  	  	<button name="saveButton" style="font-size:36px; color:#45B39D; display: none;" onclick="saveCustomer(this)"> <i class="material-icons">save</i></button>
					  	  
					  	  </td>
					  	  <td style="background-color:#45B39D">
					  	  	<button name="deleteButton" style="font-size:36px; color:#45B39D" onclick="deleteCustomer(this)"> <i class="material-icons">delete</i></button>
					  	  </td>
					  </tr>
					  					
			 <%}%>
			 <tr>
				  <td style="color:white; background-color:#45B39D"><%= i+1 %></td>
				  <td><input type="text" placeholder="required" required/></td><td><input type="text" placeholder="required" required/></td><td><input type="text" /></td>
			  	  <td><input type="text" placeholder="required" required/></td><td><input type="text" placeholder="required" required/></td><td><input type="text"/></td>
			  	  <td><input type="text"/></td><td><input type="text"/></td><td><input type="text"/></td>
			  	  <td><input type="text"/></td><td><input type="text"/></td><td><input type="text"/></td>
			  	  <td style=" text-align: center;"><input type="text" /></td><td style=" text-align: center;"><input type="text"/></td>
			  	  <td style="background-color:#45B39D;" colspan="2">
			  	  	<button name="addButton" style="font-size:36px; color:#45B39D; width: 100%;" onclick="addCustomer(this)"> <i class="material-icons">add</i></button>	  
			  	  </td>			  	  
			 </tr>
			
		</tbody>
	</table>	
			
</body>
</html>