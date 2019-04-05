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

// MainServlet - Everything comes into here before going anywhere.

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	     
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
		    PrintWriter out = response.getWriter();         //getting the response from HttpServlet
		    
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
            // SETUP ACP - APPLICATION CONTROLLER PATTERN
            //
            // 1) Create HashMap to the outgoing mobile.
            // 2) Get command (This is what the user wants to do) that the Mobile phone sent
            // 3) call AppControler with request.
            // 4) Put result from handler into jsonData,which in the next step will be "JSONised"
            /////////////////////////////////////////////////////  
            
            //this hashMap is for the returning JSON to Mobile phone
            HashMap<String, Object> jsonData = new HashMap<>();
            
            String cmd = (String) responseMap.get("command");
            //add commands to HashMap
            AppControl.mapCommand("Login",  new Login());      
            AppControl.mapCommand("Register", new Register());
//          AppControl.mapCommand("GetList",  new GetList());
            
            jsonData = AppControl.handleRequest(cmd, responseMap);
                                   
       /////////////////////////////////////////////////////////////////////////////
       // This sends back a response to the Mobile phone.
       //
       // 1) Take the passed JSON HashMap 
       // 2) Tell it you are sending JSON
       // 3) Put the formatted JSON in the out.print
       //
       ////////////////////////////////////////////////////////////////////////////		
          
     //jsonData is raw data being converted and written out here via JACKSON --> JSON
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
}
