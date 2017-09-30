package com.slingshotdemo.akbari;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is used for testing purposes and can be removed from the package.
 * @author Ismail Akbari
 *
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//add a manager
		DbManager dbm = new DbManager();
		Map<String, String> info = new HashMap<>();
		info.put("firstName", "ismail");
		info.put("lastName", "akbari");
		info.put("username", "ismail");
		info.put("password", "akbari123");
		info.put("email", "ismakbari@gmail.com");
		if(dbm.addManager(info))
			System.out.println("added successfully");
		else
			System.out.println("error occured");
		
		
		//authenticate the added manager
		Login lgn = new Login();
		int loginResult = lgn.login("ismail", "akbari123");
		switch (loginResult){
			case 1: System.out.println("logged in successfully"); break;
			case -1: System.out.println("authentication failed"); break;
			case -2: System.out.println("db error"); break;
			default:
			System.out.println("unknown error");
		}
		/*
		Customer c = new Customer();
		c.address = new Address();
		c.address.address ="jamshid"; */
		System.out.println("done");
	}

}
