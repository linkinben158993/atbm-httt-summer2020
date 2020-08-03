package com.atbm.quanlybenhvien.entity;

public class User {
	private String userName;
	private String password;
	private String role;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User() {

	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

}
