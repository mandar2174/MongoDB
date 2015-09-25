package com.mongodb.morphia.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("users")
public class User {
	@Id
	private ObjectId id;

	private String name;
	
	private long salary;
	
	@Property("Company_Name")
	private List<String> company;
	
	@Property("Phone_Number")
	private List<Long> contactNo;
	
	@Embedded
	private UserAddress address;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId i) {
		id = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public List<String> getCompany() {
		return company;
	}

	public void setCompany(List<String> company) {
		this.company = company;
	}

	public List<Long> getContactNo() {
		return contactNo;
	}

	public void setContactNo(List<Long> contactNo) {
		this.contactNo = contactNo;
	}

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", salary=" + salary
				+ ", company=" + company + ", contactNo=" + contactNo
				+ ", address=" + address + "]";
	}

}
