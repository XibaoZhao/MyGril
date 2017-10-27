package com.example.domain;

public class AccessToken {
	private String token;
	private int expires;

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpires() {
		return this.expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}
}