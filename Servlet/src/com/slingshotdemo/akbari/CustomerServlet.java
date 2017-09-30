package com.slingshotdemo.akbari;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The servlet authenticates a manager and if everything is OK he/she will be redirected to the manager control panel.  
 * @author iakbari
 *
 */
//@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		/*
		
		//connect to db
		DbManager dbm = new DbManager();
		Connection conn = dbm.getConnection("slingshot");
		if(conn==null)
			response.sendRedirect("dberror.jsp");			
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Login lgn = new Login();
		if(lgn.login(username, password)){ //login successful
			response.sendRedirect("manageCustomers.jsp");
		}
		else{
			
		}*/
		
		System.out.println("in servlet");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username==null){ //check direct access to the page
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response); 
		}
		else{
			Login lgn = new Login();
			int loginStatus = lgn.login(username, password);
			System.out.println("login status="+loginStatus);
			HttpSession session = request.getSession();		
			if (loginStatus==1){
				session.setAttribute("isUserLoggedIn",true);
				response.sendRedirect("manageCustomers.jsp"); //successful login
				//RequestDispatcher rd = request.getRequestDispatcher("/manageCustomers.jsp");
				//rd.forward(request, response);
				//rd.include(request, response);
			}
			else{	
				session.setAttribute("isUserLoggedIn",false);
				request.setAttribute("loginStatus", loginStatus);
				//request.setAttribute("loginStatus", "password was " + password );
				//response.sendRedirect("login.jsp");		
				//RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
