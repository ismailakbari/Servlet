<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Customers</title>

<link rel="stylesheet" type="text/css" href="css/styles.css">
	
</head>
<body>

<form name="logoutForm" action="logout.jsp" method="post">
	<button type="submit" name="logoutButton" id="logout" >Logout</button>
</form>


<div id="cmMain">
	<div style="color: green; font-size: 20px; font-weight: bold;">Search</div>
	<div style="padding: 5px;">* field(s) are required.</div>
	<div id ="customerSearch">	
	<!-- search table -->
	<form name="searchForm" method="post" action="search.jsp">
		<table id="searchTable">
			<thead>
		    	<tr>
		        	
		    	</tr>
		 	</thead>
		 	<tbody>
		 		<!-- customer info -->
		 		<tr>
		 			<td>ID</td><td>First Name</td><td>Middle Name</td><td>Last Name</td>
		 		</tr>
		 		<tr>
		 			<td><input name="customerID" type="text"/></td><td><input name="firstName" type="text"/></td><td><input name="middleName" type="text"/></td><td><input name="lastName" type="text"/></td>
		 		</tr>		 		
		 		<tr>
		 			<td/><td/><td><input type="submit" value="Search" onclick=" /*return checkSearch()*/" style="width:100%;  background-color: #aaffaa;"/></td><td><input type="reset" value="Reset" style="width:100%; background-color: #ffaaaa;"/></td>
		 		</tr>
		 	</tbody>    
		</table>
	</form>
	</div>
	
	
	<div id ="customerSearchResult">

	
	
	</div>
</div>

</body>
</html>