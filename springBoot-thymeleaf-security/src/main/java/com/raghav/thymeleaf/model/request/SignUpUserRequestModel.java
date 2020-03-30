package com.raghav.thymeleaf.model.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpUserRequestModel {

	/*
	 * 
	 * @NotNull is not working no any message displayed of error 
	 * 
	 */
	
	
	@Size(min =1, max = 50, message = "Enter your First Name")
	private String firstName;
	
	private String lastName;
	
	@Size(min = 1, message = "Enter your email address")
	@Email(message = "Enter a valid email")
	@NotNull(message = "Enter your email")
	private String email;
	
	@NotNull(message = "Enter a password")
	@Size(min = 4, message = "Enter a new password")
	private String password;
	
	@NotNull(message = "Enter birthday")
	@Size(min = 4, message = "Enter your birthday")
	private String dob;
	
	@NotNull(message = "Choose your gender")
	private String gender;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
}