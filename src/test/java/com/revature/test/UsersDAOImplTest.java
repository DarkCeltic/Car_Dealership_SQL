package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.revature.dao.UsersDAOImpl;
import com.revature.users.Customer;
import com.revature.users.Employee;

class UsersDAOImplTest {

	@BeforeClass
	static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	static void tearDownAfterClass() throws Exception {
	}

	@Before
	void setUp() throws Exception {

	}

	@After
	void tearDown() throws Exception {
	}

	@Test
	void testCreateAndSelectByUsername() {
		// this tests creating a customer and selecting by username and select all
		// customers
		UsersDAOImpl test = new UsersDAOImpl();
		Customer c = new Customer("Bob", "Dilly", "Bdil", "test");
		test.insertCustomer(c);
//			 = new Customer();
//			System.out.println(test.selectCustomerByUsername(c.getUsername()));
		Customer c2 = test.selectCustomerByUsername(c.getUsername());
		ArrayList<Customer> temps = new ArrayList<>();
		temps = test.selectAllCustomers();
		assertEquals(c.getUsername(), c2.getUsername());
		assertFalse("This should not return empty", temps.isEmpty());
	}

	@Test
	void testGetAllEmployees() {
		UsersDAOImpl test = new UsersDAOImpl();
		ArrayList<Employee> temps = new ArrayList<>();
		temps = test.selectAllEmployees();
		assertFalse("This should not return empty", temps.isEmpty());
	}
}
