package com.revature.pojo;

import java.util.ArrayList;

import com.revature.dao.UsersDAOImpl;
import com.revature.users.Customer;
import com.revature.users.Employee;

public class Users {

	ArrayList<Customer> customers = new ArrayList<Customer>();
	ArrayList<Employee> employees = new ArrayList<Employee>();
	UsersDAOImpl users = new UsersDAOImpl();

	public ArrayList<Customer> getCustomers() {
		customers = users.selectAllCustomers();
		return customers;
	}

	public void setCustomers(Customer cus) {
		customers.add(cus);
	}

	public ArrayList<Employee> getEmployees() {
		employees = users.selectAllEmployees();
		return employees;
	}

	public void setEmployees(Employee emp) {
		employees.add(emp);
	}
}
