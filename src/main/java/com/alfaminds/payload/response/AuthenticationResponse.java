package com.alfaminds.payload.response;

public class AuthenticationResponse {

	private String responseMessage;
	private String token;
	private String username;
	
	
	public AuthenticationResponse() {

	}

	public AuthenticationResponse(String respMessage) {
			this.responseMessage = respMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
