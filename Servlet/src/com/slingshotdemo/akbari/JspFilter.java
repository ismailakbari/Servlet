package com.slingshotdemo.akbari;

import java.io.IOException;
import java.util.Date;

//Import required java libraries
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This class is a servlet filter that is used for authentication purposes.
 * @author Ismail Akbari
 *
 */
//Implements Filter class
public class JspFilter implements Filter{
	 
	 public void  init(FilterConfig config) throws ServletException {
	      // Get init parameter 
	      String initParam = config.getInitParameter("init-param"); 
	      
	 
	      //Print the init parameter 
	      System.out.println("init Param: " + initParam); 
	 }
	 
	 @Override
	 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 // TODO Auto-generated method stub
		 
		 HttpServletResponse httpResponse = (HttpServletResponse) response;
		 HttpServletRequest httpRequest = (HttpServletRequest) request;
		 HttpSession session = httpRequest.getSession();
		 //session = httpRequest.getSession();
		 System.out.println("in filter="+ httpRequest.getRequestURI());
		 System.out.println("session attr>>isUserLoggedIn="+String.valueOf(session.getAttribute("isUserLoggedIn")));
		 //System.out.println("req uri="+ httpRequest.getRequestURI());
		 
		
		 
		 if((session.getAttribute("isUserLoggedIn")==null) && !httpRequest.getRequestURI().endsWith("CustomerServlet")){
			 //System.out.println(httpRequest.getRequestURI());
			 //httpResponse.sendRedirect("login.jsp");
			 //response.sendRedirect("login.jsp");
			 //RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/login.jsp");
			
			 //httpResponse.sendRedirect("login.jsp"); //successful login
			 RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			 rd.forward(request, response);
		 }
		 else{
			 if(httpRequest.getRequestURI().endsWith("manageCustomers.jsp")){
				 System.out.println("check mc uri="+httpRequest.getRequestURI());
				//prevent back button
				 httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				 httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				 httpResponse.setDateHeader("Expires", 0);
				 //httpResponse.sendRedirect("login.jsp"); 
			 }
			 chain.doFilter(request,response);
		 }	 
			
		/* HttpServletRequest req = (HttpServletRequest) request;
			
			//Get the IP address of client machine.
			String ipAddress = req.getRemoteAddr();
			
			//Log the IP address and current timestamp.
			System.out.println("IP "+ipAddress + ", Time " 
								+ new Date().toString());
			
			chain.doFilter(request, response);
		 */
	 }
	/*
	 public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		 if (false) {
		      //if user is logged in, complete request
		      chain.doFilter(request, response);
		 }
		 else {
		      //not logged in, go to login page
		      //response.sendRedirect("login.jsp");
			 response.sen
		 }
		 
		// Pass request back down the filter chain
	     
	  }
	*/

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}



}