package com.denisegoetz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

// This @Entity is what is called an annotation. It tells Hibernate that this class is allowed to store its
// object inside the database.
//
// Table name is derived from the @Entity name if @Table isn't specified.

@Entity
@Table(name= "user_table")
public class User implements Serializable {

	// ****************************************************************************************************
	// OTHER ANNOTAIONS: @Column to change a column name,
	// @Transient will take a column out of the table. (actually creates table w/out it.
	// ****************************************************************************************************

	// @Id is an annotation that specifics you are getting a primary key.
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	private int user_id;
	
	@Column(name = "user_login")
	private String user_login;
	
	@Column(name = "user_password")
	private String user_password;
	
	@Column(name = "user_Fname")
	private String user_Fname;
	
	@Column(name = "user_Lname")
	private String user_Lname;
	
	@Column(name = "user_email")
	private String user_email;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_login() {
		return user_login;
	}

	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_Fname() {
		return user_Fname;
	}

	public void setUser_Fname(String user_Fname) {
		this.user_Fname = user_Fname;
	}

	public String getUser_lname() {
		return user_Lname;
	}

	public void setUser_lname(String user_lname) {
		this.user_Lname = user_lname;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_login=" + user_login + ", user_password=" + user_password
				+ ", user_Fname=" + user_Fname + ", user_Lname=" + user_Lname + ", user_email=" + user_email + "]";
	}

}
