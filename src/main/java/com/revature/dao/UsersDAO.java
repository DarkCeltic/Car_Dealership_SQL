package com.revature.dao;

import java.util.List;

import com.revature.users.Customer;
import com.revature.users.Employee;

public interface UsersDAO {
	
	//Customer Implementation
	//Add Customer
	public void insertCustomer(Customer c);
	//Find a customer by username
	public Customer selectCustomerByUsername(String username);
	//Return all of the Customers
	public List<Customer> selectAllCustomers();
	// When updating a customer
//	public void updateCustomers(Customer cust);

	// Employee Implementations
	public Employee selectEmployeeByUsername(String username);

	public List<Employee> selectAllEmployees();


}
