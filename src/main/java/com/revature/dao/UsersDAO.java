package com.revature.dao;

import java.util.List;

import com.revature.users.Customer;
import com.revature.users.Employee;

public interface UsersDAO {

	// Customer Implementation
	// Add Customer
	public void insertCustomer(Customer c);

	// Find a customer by username
	public Customer selectCustomerByUsername(String username);

	// Return all of the Customers
	public List<Customer> selectAllCustomers();

	// Employee Implementations
	public Employee selectEmployeeByUsername(String username);

	// Returns an arraylist of all employees
	public List<Employee> selectAllEmployees();

}
