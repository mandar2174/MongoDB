package com.mongodb.example;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.MongoClient;
import com.mongodb.morphia.model.User;
import com.mongodb.morphia.model.UserAddress;

/**
 * Class for implement the morphia framework api
 * 
 * @author mandar
 * 
 */
public class MorphiaExample {
	public static void main(String[] args) throws UnknownHostException {

		// create the morhpia instance
		final Morphia morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("com.mongodb.morphia.model");

		// connect to the mongodb and get the client instance
		MongoClient mongoClient = new MongoClient(MongoDBConstant.MACHINE_IP,
				MongoDBConstant.PORT);
		Datastore ds = morphia.createDatastore(mongoClient, "morphiaDB1");

		// set the information into the user model for storing data into the
		// mongodb
		User user = new User();
		user.setName("Omkar");
		user.setSalary(30000);
		List<String> company = new ArrayList<String>();
		company.add("Ericssion");
		company.add("Oracle");
		company.add("Sunpharama");
		user.setCompany(company);
		List<Long> contactNo = new ArrayList<Long>();
		contactNo.add(9189213365l);
		contactNo.add(7856212465l);
		contactNo.add(8957212365l);
		user.setContactNo(contactNo);

		// set the user address
		UserAddress address = new UserAddress();
		address.setCity("Delhi");
		address.setCountry("India");
		address.setPincode(435345);
		user.setAddress(address);

		// stored the data into the db
		ds.save(user);
		System.out.println("Added the user");

		// read all the docuement from the mongodb

		final Query<User> query = ds.createQuery(User.class);

		final List<User> employees = query.asList();
		for (User element : employees) {
			System.out.println(element);
		}
		System.out.println("User whose salary less than or equal to 50000");
		List<User> salaryQuery = ds.createQuery(User.class).field("salary")
				.lessThanOrEq(50000).asList();
		for (User element : salaryQuery) {
			System.out.println(element);
			
		}
		System.out.println("User salary less than 15000 ");
		// find the user whose salary is less than 15000 than
		List<User> salaryLessQuery = ds.createQuery(User.class)
				.filter("salary <", 15000).asList();
		for (User element : salaryLessQuery) {
			System.out.println(element);
		}

		// update the salary where salary is greater than 40000

		final Query<User> updateCondition = ds.createQuery(User.class).filter(
				"salary >=", 30000);

		final UpdateOperations<User> updateOperations = ds
				.createUpdateOperations(User.class).inc("salary", 20000);

		final UpdateResults results = ds.update(updateCondition,
				updateOperations);
		System.out.println("Updated Record Count  : "
				+ results.getUpdatedCount());

		System.out.println("Updated Record with salary increment by 20000");
		// updated salary for those user whose  salary is greater than or equal to 30000
		List<User> result = ds.createQuery(User.class).asList();
		for (User element : result) {
			System.out.println(element);
		}

		// delete the user whose salary is less than or equal to 10000
		final Query<User> deleteQuery = ds.createQuery(User.class).filter(
				"salary <=", 10000);
		ds.delete(deleteQuery);
		System.out.println("Deleted Record");
		// updated value
		List<User> deleteResult = ds.createQuery(User.class).asList();
		for (User element : deleteResult) {
			System.out.println(element);
		}

		// get the document who have the Company name
		List<User> companyUserResult = ds.createQuery(User.class)
				.field("Company_Name").exists().asList();
		System.out.println("User who have company : "
				+ companyUserResult.size());

		// get the user whose salary is less than 120000 and greater than 100000
		Query<User> query1 = ds.createQuery(User.class);
		String criteriaQuery = query1.and(
				query1.criteria("salary").lessThan(120000),
				query1.criteria("salary").greaterThan(110000)).getFieldName();
		System.out.println("Criteria : " + criteriaQuery);
		List<User> andResult = query1.asList();
		System.out.println("And result");
		for (User element : andResult) {
			System.out.println(element);
		}

		List<User> andQuery = ds.createQuery(User.class).field("salary")
				.lessThan(200000).field("salary").greaterThan(120000).asList();
		for (User element : andQuery) {
			System.out.println(element);
		}

		// retrive only the name and salary from user document

		System.out.println("Retrive the only name and salary");
		List<User> retriveQuery = ds.createQuery(User.class)
				.retrievedFields(true, "salary").retrievedFields(true, "name")
				.asList();
		for (User element : retriveQuery) {
			System.out.println(element);
		}
		// Retrieve the 4 element from index 1(index start from 0 )
		List<User> limitQuery = ds.createQuery(User.class).offset(1).limit(4)
				.asList();

		System.out.println("Retrive the user from index 2- max 4");
		for (User element : limitQuery) {
			System.out.println(element);
		}

		System.out.println("Sort the name by ascending order");
		// sort the document by name (- descending order)
		List<User> sortedName = ds.createQuery(User.class)
				.order("name,-salary").asList();
		for (User element : sortedName) {
			System.out.println(element);
		}

		// use the and operation for finding the user whose salary greater than
		// 50000 and name is Mandar
		Query<User> queryOr = ds.find(User.class);

		queryOr.and(queryOr.criteria("salary").greaterThan(50000), queryOr
				.criteria("name").equal("Mandar"));

		List<User> orResult = queryOr.asList();
		System.out.println("Element inside the or result");
		for (User element : orResult) {
			System.out.println(element);
		}
	}
}
