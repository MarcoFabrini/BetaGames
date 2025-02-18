package com.betagames.dto;

public class DetailsShippingDTO {

	private Integer id;
	private String name;
	private String lastname;
	private String country; // nazione
	private String stateRegion; // regione o stato
	private String cap; // codice postale
	private String city; // città
	private String address; // indirizzo
	private Boolean active;

	public DetailsShippingDTO(Integer id, String name, String lastname, String country, String stateRegion, String cap,
			String city, String address, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.country = country;
		this.stateRegion = stateRegion;
		this.cap = cap;
		this.city = city;
		this.address = address;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStateRegion() {
		return stateRegion;
	}

	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DetailsShippingDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", country=" + country
				+ ", stateRegion=" + stateRegion + ", cap=" + cap + ", city=" + city + ", address=" + address
				+ ", active=" + active + "]";
	}

}// class
