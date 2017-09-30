<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		session.removeAttribute("isUserLoggedIn");
	 	//session.invalidate();
		//RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		//rd.forward(request, response);
		//System.out.println("session attr in logout="+session.getAttribute("isUserLoggedIn"));
		//prevent back button
		//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		//response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		//response.setDateHeader("Expires", 0);
		response.sendRedirect("login.jsp"); //successful login
		
	%>
</body>
</html>