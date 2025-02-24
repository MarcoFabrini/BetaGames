package com.betagames.request;

public class SignInRequest {

    private String userName;
	private String pwd;
	private Integer id;
	private String newPwd;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	@Override
	public String toString() {
		return "SignInRequest [userName=" + userName + ", pwd=" + pwd + ", id=" + id + ", newPwd=" + newPwd + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
