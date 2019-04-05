package com.denisegoetz;


public class LoginBefore  {
	

	public User startLogin(String userName, String userPass) {

	System.out.println("You are now in Login Class about to do the login procedures");
	
	System.out.println("The passed user is... " + userName + " and the password is... " + userPass);
	
	LoginDAO getDAO = new LoginDAO();
    return getDAO.getUserLogin(userName, userPass);
  
	}

  
}