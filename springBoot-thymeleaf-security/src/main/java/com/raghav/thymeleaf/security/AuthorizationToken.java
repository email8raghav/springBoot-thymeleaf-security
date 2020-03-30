package com.raghav.thymeleaf.security;

public class AuthorizationToken {
	
	private String publicUserId;
	private String authorizationToken;
	
	public String getPublicUserId() {
		return publicUserId;
	}
	public void setPublicUserId(String publicUserId) {
		this.publicUserId = publicUserId;
	}
	public String getAuthorizationToken() {
		return authorizationToken;
	}
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	
	

}
