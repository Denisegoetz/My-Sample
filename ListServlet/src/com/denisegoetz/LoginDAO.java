package com.denisegoetz;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



public class LoginDAO {

	public User getUserLogin(String userName, String userPass) {
	
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

	Session session = sf.openSession();
	
	try {
    //***************************************************************************************
    // HQL - Hibernate Query Language. Retrieve a single row (login and password)      
    //***************************************************************************************
	System.out.println("\n HQL - Query row for USER Login and Password");
	
	// Both these queries Work.  I need the second one.
    // Query q2 = session.createQuery("select u from User as u where u.user_id= 1000");
    
     Query q2 = session.createQuery("select u from User as u where u.user_login= :log and u.user_password= :pass");
     q2.setParameter("log", userName );
     q2.setParameter("pass", userPass );
     User usr = (User) q2.uniqueResult();

     
     if (usr != null) {
    	 System.out.println("Show User Model info here.. " + usr.getUser_email());
    	 System.out.println("Show User Model info here.. " + usr.getUser_lname());
    	 
     }
     
     System.out.println("Show User database information here.. " + usr);
     return usr;
     
    
    }finally {
        session.close(); //close session
        sf.close();     //close  the session factory
    }
  }
}
