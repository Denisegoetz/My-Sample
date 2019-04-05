package com.denisegoetz;

import java.util.HashMap;

public class Register implements Handler {

	@Override
	public HashMap<String, Object> handleIt(HashMap<String, Object> responseMap) {

		String u = "uname";
		String userName = null;
		String p = "pword";
		String userPass = null;
		String f = "Fname";
		String fname = null;
		String L = "Lname";
		String lname = null;
		String e = "email";
		String email = null;



		// create hashmap to return -> MainServlet
		HashMap<String, Object> jsonData = new HashMap<>();

		userName = (String) responseMap.get(u);
		userPass = (String) responseMap.get(p);
		fname    = (String) responseMap.get(f);
		lname    = (String) responseMap.get(L);
		email    = (String) responseMap.get(e);
		

		System.out.println(userName + " wants to log in with this password " + userPass);

		/////////////////////////////////////////////////////
		//
		// 1) Check if this person is already registered.
		// 2) Back from DAO, check if it found the record
		//
		/////////////////////////////////////////////////////

		User backFromDAO = startLogin(userName, userPass);

		if (backFromDAO != null) {

			jsonData.put("command", "Register");
			jsonData.put("uname", backFromDAO.getUser_login());
			jsonData.put("pword", backFromDAO.getUser_password());
			jsonData.put("rtnResp", "alreadyExistReturn");
			jsonData.put("Fname", backFromDAO.getUser_Fname());
			jsonData.put("Lname", backFromDAO.getUser_lname());
			jsonData.put("email", backFromDAO.getUser_email());

		} else {
			
			//try to insert the new User record
			
			User backFromDAOinsert = startRegister(userName, userPass,fname, lname, email);
			
			if (backFromDAOinsert != null) {
			 
			jsonData.put("command", "Register");
			jsonData.put("uname", backFromDAOinsert.getUser_login());
			jsonData.put("pword", backFromDAOinsert.getUser_password());
			jsonData.put("rtnResp", "okInsertreturn");
			jsonData.put("Fname", backFromDAOinsert.getUser_Fname());
			jsonData.put("Lname", backFromDAOinsert.getUser_lname());
			jsonData.put("email", backFromDAOinsert.getUser_email());

		   }else {
			    jsonData.put("command", "Register");
				jsonData.put("uname", "");
				jsonData.put("pword", "");
				jsonData.put("rtnResp", "BADinsertReturn");
				jsonData.put("Fname", "");
				jsonData.put("Lname", "");
				jsonData.put("email", "");
		   }
		}

		return jsonData;
		
	}

	public User startLogin(String userName, String userPass) {

		System.out.println("You are now in Register Class about to go to LoginDAO to get a record");

		System.out.println("The passed user is... " + userName + " and the password is... " + userPass);
		
		System.out.println("Checking to be sure this user isn't already registered.");
		
		LoginDAO getDAO = new LoginDAO();
		return getDAO.getUserLogin(userName, userPass);

	}
	
	public User startRegister(String userName,String userPass,String fname, String lname, String email ) {

		System.out.println("You are now in Register Class about to go to RegisterDAO to get a record");

		System.out.println("The user" + userName + " & password " + userPass + " & firstname " + fname + "& lastname " + lname + "& email " + email);
		
		System.out.println("Going to INSERT into USER now....");
		
		RegisterDAO insertUserDAO = new RegisterDAO();
		return insertUserDAO.insertUserRegisterInfo(userName, userPass, fname, lname, email);

	}

}
