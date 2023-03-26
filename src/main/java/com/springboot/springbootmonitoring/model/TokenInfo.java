package com.springboot.springbootmonitoring.model;

public class TokenInfo {
	private String username;
	private long expireTime;

	public TokenInfo(String username, long time) {
		this.username = username;
		this.expireTime = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "TokenInfo [username=" + username + ", expireTime=" + expireTime + "]";
	}

}
