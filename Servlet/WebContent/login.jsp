<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/login.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>Login</title>
</head>
<body onload="init()">
		<center id=topCenter>
		
			<form id="formLogin" method="post" action="CustomerServlet">
	            <center>
	            <table id="tableLogin">
	                <thead>
	                    <tr>
	                        <th colspan="2">Login Here</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                        <td>Username</td>
	                        <td><input class="fieldBackground" type="text" name="username" placeholder="Username" pattern="[a-zA-z]([0-9]|[a-zA-z]){5,}" 
	                        title="username must start with a letter, can contain letters and digits and should be at least of length 6 " 
	                        onblur="checkUsername(this)"   onclick="resetLoginMessage()" required/></td>                        
	                    </tr>
	                    <tr>
	                        <td>Password</td>
	                        <td><input class="fieldBackground" type="password" name="password" placeholder="Password" pattern=".{8,}"
	                        title="password should be at least 8 characters" 
	                        onblur="checkPassword(this)"  required/></td>
	                    </tr>
	                    <tr>
	                        <td colspan="2" align="center"><input type="submit" value="Login" />
	                            &nbsp;&nbsp;
	                            <input type="reset" value="Reset" />
	                        </td>                        
	                    </tr>                    
	                </tbody>
	            </table>
	            </center>
	        </form>
        
         <p id="errorMessage"></p>
        <p id="test"></p>
        <p id="loginMessage">
        	<% 
        	Integer loginStatus = (Integer)request.getAttribute("loginStatus");  
        	//int loginStatus = Integer.valueOf(ls);
        	if(loginStatus!=null){ //This should be checked otherwise it will get an exception
        		
	        	switch(loginStatus){
	        		case -1: %> 
	        			Incorrect username or password.
		        		<%
		        		break; 
	        		case -2: %>
	        			DB connection error. Please try again later.
	        			<%
	        			break;
	        		default: %>
	        			Unknown Error occurred.
	        			
	        	<%}}%>        	
        </p>
       </center>
       
</body>
</html>