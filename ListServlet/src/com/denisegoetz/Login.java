package com.denisegoetz;

import java.util.HashMap;

public class Login implements Handler {
	
    @Override
    public HashMap<String, Object> handleIt(HashMap<String, Object> responseMap ) {
	   
    String u = "uname";
    String userName = null;
    String p = "pword";
    String userPass = null;
    //create hashmap to return -> MainServlet
    HashMap<String, Object> jsonData = new HashMap<>();
    
    userName = (String) responseMap.get(u);
  	userPass = (String) responseMap.get(p);
  	
    System.out.println( userName + " wants to log in with this password " + userPass );
    
    /////////////////////////////////////////////////////
    //   
    // 1) Do the login
    // 2) Back from DAO, check if it found the record
    //
    /////////////////////////////////////////////////////  
    
    User backFromDAO = startLogin(userName, userPass);   
   

    if (backFromDAO !=null) {
 	   
        jsonData.put("command", "Login");
        jsonData.put("uname",   backFromDAO.getUser_login());
        jsonData.put("pword",   backFromDAO.getUser_password());
        jsonData.put("rtnResp",  "OKreturn");
        jsonData.put("Fname",   backFromDAO.getUser_Fname());
        jsonData.put("Lname",   backFromDAO.getUser_lname());
        jsonData.put("email",   backFromDAO.getUser_email());
        
    }else {
        jsonData.put("command", "Login");
        jsonData.put("uname",        "");
        jsonData.put("pword",        "");
        jsonData.put("rtnResp", "BADreturn");
        jsonData.put("Fname",        "");
        jsonData.put("Lname",        "");
        jsonData.put("email",        "");
        
 	  
    }
    
    return jsonData;
    	
 }
	public User startLogin (String userName, String userPass) {

	System.out.println("You are now in Login Class about to go to LoginDAO to get a record");
	
	System.out.println("The passed user is... " + userName + " and the password is... " + userPass);
	
	LoginDAO getDAO = new LoginDAO();
    return getDAO.getUserLogin(userName, userPass);
  
	
	}
  
}
