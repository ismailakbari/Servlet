<%@page import="com.slingshotdemo.akbari.DbManager"%>
<%@page import="java.io.PrintWriter" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Customer</title>
</head>
<body>
<%
	
	String[] customerInfo = request.getParameter("customerInfo").split(",");
	//System.out.print(customerInfo);
	//PrintWriter out = response.getWriter();
	DbManager dbm = new DbManager();
	int result = dbm.updateCustomer(customerInfo);
	response.getWriter().write(String.valueOf(result));      // Write response body.
	//out.write("1");
	
%>
</body>
</html>