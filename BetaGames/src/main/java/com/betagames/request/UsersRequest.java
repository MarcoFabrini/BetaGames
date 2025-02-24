package com.betagames.request;

/**
 *
 * @author FabriniMarco
 */
public class UsersRequest {
	private Integer id;
	private String username;
	private String email;
	private String pwd;
	private Boolean active;
	private Integer roleId;
	private Integer detailsShippingId;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDetailsShippingId() {
		return detailsShippingId;
	}

	public void setDetailsShippingId(Integer detailsShippingId) {
		this.detailsShippingId = detailsShippingId;
	}

}// class
