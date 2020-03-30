package com.raghav.thymeleaf.model.request;

public class PasswordRequestModel {

	private String password;
	private String ConfirmPassword;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return ConfirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}
	
	
}
