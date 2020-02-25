package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.users.Customer;
import com.revature.users.Employee;

public class UsersDAOImpl implements UsersDAO {
	private static String url = "jdbc:postgresql://localhost:5432/Car_Dealership_Test";
	private static String username = "postgres";
	private static String password = "postgres";

	@Override
	public void insertCustomer(Customer c) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?)");
			ps.setString(1, c.getFirstName());
			ps.setString(2, c.getLastName());
			ps.setString(3, c.getUsername());
			ps.setString(4, c.getPassword());
			ps.setLong(5, 1);

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer selectCustomerByUsername(String customerUsername) {
		Customer customer = null;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT* FROM users WHERE username=?");
			ps.setString(1, customerUsername);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				customer = new Customer(rs.getString("firstname"), rs.getString("lastname"), rs.getString("username"),
						rs.getString("password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public ArrayList<Customer> selectAllCustomers() {
		ArrayList<Customer> customer = new ArrayList<Customer>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT* FROM users WHERE userid=1");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				customer.add(new Customer(rs.getString("firstname"), rs.getString("lastname"), rs.getString("username"),
						rs.getString("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Employee selectEmployeeByUsername(String emplyeeUsername) {
		Employee employee = null;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT* FROM users WHERE username=?");
			ps.setString(1, emplyeeUsername);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				employee = new Employee(rs.getString("firstname"), rs.getString("lastname"), rs.getString("username"),
						rs.getString("password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public ArrayList<Employee> selectAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE userid=2");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				employees.add(new Employee(rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("username"), rs.getString("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
}
