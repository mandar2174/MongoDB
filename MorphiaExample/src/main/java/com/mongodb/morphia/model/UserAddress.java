package com.mongodb.morphia.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 6907046314397124150L;
	private String country;
	private String city;
	private long pincode;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "UserAddress [country=" + country + ", city=" + city
				+ ", pincode=" + pincode + "]";
	}

}
