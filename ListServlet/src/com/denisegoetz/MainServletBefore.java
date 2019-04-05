package com.denisegoetz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Servlet - Main - Everything comes into here before going anywhere.
 */
@WebServlet("/MainServletBefore")
public class MainServletBefore extends HttpServlet {
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Do get reached.....");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	    ///////////////////////////////////////////////////////////////////
	    // The JSON comes into the Servlet from the Mobile phone. 
		// 
	    // 1) Get the incoming request 
		// 2) Decode/DeSerialize the JSON
		//
	    ///////////////////////////////////////////////////////////////////
		try {
			System.out.println("Hello from Server! Thank you for the JSON data.");
		   
            ////////////////////////// 
            //  INCOMING JSON!!    //
		    ////////////////////////  
		    PrintWriter out = response.getWriter();         //getting the response
		    
		    BufferedReader in = request.getReader();;       //read one line from the request and put into (in)
            String inputLine;                               //inputLine = holding area of incoming data
            StringBuffer input = new StringBuffer();        //input = Will be the entire accumulated buffer of what is sent over.
            while ((inputLine = in.readLine()) != null) {   //Copy the next line read in to the holding area and check if it's null
                input.append(inputLine);                    //if not null, append to the accumulated lines in the string buffer(input).
                System.out.println("Server read this... " + input);   
            }
            in.close();                                     //close buffered reader 
            
            
            /////////////////////////////////////////////////////
	        //   
            // 1)DeSerializing/Decoding incoming JSON via Jackson  - (this WAS getting a 500 error in servlet on Jackson commands)
            // 2)Puts data into a HashMap
            //
            /////////////////////////////////////////////////////                   
		    ObjectMapper mapper = new ObjectMapper();
		    HashMap<String, Object> responseMap = null;
            responseMap = mapper.readValue(input.toString(), HashMap.class);   //Jackson maps it into HashMap that was just created
            System.out.println("Server Desserialized this... " + responseMap); 
            
            /////////////////////////////////////////////////////
	        //   
            // 1)Get command (This is what the user wants to do) that the Mobile phone sent
            // 2)Get username and password sent in from Mobile
            //
            /////////////////////////////////////////////////////  
            // Search for a certain key 
             String cmd = "command";
             String u = "uname";
             String userName = null;
             String p = "pword";
             String userPass = null;
            
            
            HashMap<String, Object> jsonData = new HashMap<>();
            
            // Check if command(key in HashMap) is "Login".
            if(responseMap.get(cmd).equals("Login")) {
              // Need to verify this user - Are they a user?
          	  userName = (String) responseMap.get(u);
          	  userPass = (String) responseMap.get(p);
              System.out.println(userName + " wants to log in with this password " + userPass);
              
              /////////////////////////////////////////////////////
  	          //   
              // 1) Do the login
              // 2) Back from DAO, check if it found the record
              //
              /////////////////////////////////////////////////////  
              
              //do the login class - calling the startLogin method 
               Login doLogin = new Login();
               User backFromDAO = doLogin.startLogin(userName, userPass);
               
               if (backFromDAO !=null) {
            	   
                   jsonData.put("command", "Login");
                   jsonData.put("uname",   (backFromDAO.getUser_id() + " " + backFromDAO.getUser_lname()));
                   jsonData.put("pword",   backFromDAO.getUser_password());
                   jsonData.put("rtnRsp",  "OKreturn");
                   jsonData.put("Fname",   backFromDAO.getUser_Fname());
                   jsonData.put("Lname",   backFromDAO.getUser_lname());
                   jsonData.put("email",   backFromDAO.getUser_email());
                   
               }else {
                   jsonData.put("command", "Login");
                   jsonData.put("uname",        "");
                   jsonData.put("pword",        "");
                   jsonData.put("rtnRsp",  "BADreturn");
                   jsonData.put("Fname",        "");
                   jsonData.put("Lname",        "");
                   jsonData.put("email",        "");
                   
            	  
               }
               
            } else {
                System.out.println("This is not a login command.");
            }
            
       /////////////////////////////////////////////////////////////////////////////
       // This sends back a response to the Mobile phone.
       //
       // 1) create the output to send
       // 2) Tell it you are sending JSON
       // 3) Put the formatted JSON in the out.print
       //
       ////////////////////////////////////////////////////////////////////////////		
      //Sample JSON:   String jsonString2 ="{\"id\":123Denise,\"firstname\":\"Susan\",\"lastname\":\"Whitmore\"}";
       
           
       //Raw data being converted and written out here via JACKSON --> JSON
       ObjectMapper mapIt = new ObjectMapper();                  //ObjectMapper is Jackson’s serialization mapper
       String jsonResult = mapIt.writeValueAsString(jsonData);
       
	    System.out.println("Trying to send back now..!");
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    out.print(jsonResult);
	    out.flush();
	    out.close(); 
            
         
	  }catch (IOException e) {
			e.printStackTrace();
	  }
	}

	private Object isEqual(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
