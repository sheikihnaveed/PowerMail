package com.alfaminds.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserModel {
	
	@Id
	private String id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String company;
	
	private String fullname;
	
	private String mobile;
	
	private boolean enabled;
	
	private String verificationCode;
	
	
	public UserModel() {
		
	}
	
	public UserModel(String username, String email, String password, String company, String fullname, String mobile) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.company = company;
		this.fullname = fullname;
		this.mobile = mobile;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getVerificationCode() {
		return verificationCode;
	}


	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	
	

}
