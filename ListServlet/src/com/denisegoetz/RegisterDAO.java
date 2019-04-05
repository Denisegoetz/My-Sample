package com.denisegoetz;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class RegisterDAO {
	
	public User insertUserRegisterInfo(String userName, String userPass, String fname, String lname, String email) {
		
	// Founder of Hibernate is Gavin King.
	// SessionFactory is an Interface. That means we can't create object of SessionFactory.
	// Configuration is a class and we need to create an object of configuration.
	// ***********************************************************************************************************
	// Set to configure() to get it to use the hibernate.cfg.xml file
	// Set the class you are going to use to tell Hibernate which objects you are using to do things to DB.
		
	Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class);
	  // ***********************************************************************************************************
	  // This next line was done because without it buildSessionFactory turns into a deprecated method.
	ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();
	  // ***********************************************************************************************************
	SessionFactory sf = con.buildSessionFactory(reg);
	
    //***************************************************************************************
    // HQL - Hibernate Query Language. open session, format USER object, then insert it.      
    //***************************************************************************************

	Session session = sf.openSession();
	Transaction tx = null;
	
	System.out.println("\n HQL - Inserting row into user_table. Creating new REGISTRATION!");
	
	 User newReg = new User();
     newReg.setUser_login(userName);
     newReg.setUser_password(userPass);
     newReg.setUser_Fname(fname);
     newReg.setUser_lname(lname);
     newReg.setUser_email(email);
     
     try {
         tx = session.beginTransaction();
       //Saves the data
         int result = (int) session.save(newReg);
         System.out.println("Row of " + result + " user Id affected.");
         tx.commit();
         
         System.out.println("Show User database information here.. " + newReg);
     
	 } catch (HibernateException e) {
         if (tx != null) {tx.rollback();}
         e.printStackTrace();
     
        
    }finally {
        session.close(); //close session
        sf.close();     //close  the session factory
    }
	return newReg;
  }	
}

