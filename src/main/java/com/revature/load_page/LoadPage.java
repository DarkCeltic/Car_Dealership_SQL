package com.revature.load_page;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.UsersDAOImpl;
import com.revature.pojo.Users;
import com.revature.users.Customer;
import com.revature.users.Employee;

public class LoadPage {
	Users user = new Users();
	UsersDAOImpl userAdd = new UsersDAOImpl();
	Scanner input = new Scanner(System.in);
	private static Logger log = Logger.getRootLogger();

	public void loginPage() {

		String option = "";

		while (!option.equals("3")) {
			System.out.println("Welcome to the Tattoine Used Starfighter Emporium");
			System.out.println("Please select an option to continue.");
			System.out.println("Type 1 to login.");
			System.out.println("Type 2 to Register for an account");
			System.out.println("Type 3 to exit this website");

			option = input.nextLine();
			switch (option) {
			case "1":
				login();
				break;
			case "2":
				register();
				break;
			case "3":
				input.close();
				System.out.println("Thank you for checking out Tattoine Used Starfighter Emporium, please come again!");
				System.exit(0);
				break;
			default:
				System.out.println("That is not an option. Please try again.");
				break;
			}
		}
	}

	public void login() {
		String username = "", password = "";

		String option = "";
		System.out.println("Welcome to the login page.");
		System.out.println("Please select from an option below.");
		System.out.println("Type 1 if you are a customer.");
		System.out.println("Type 2 if you are an Employee. ");
		System.out.println("Type 3 if you want to exit to the previous screen");
		option = input.nextLine();
		if (option.equals("1") || option.equals("2")) {
			System.out.println("Enter your username");
			username = input.nextLine();
			System.out.println("Enter your password");
			password = input.nextLine();
		}
		switch (option) {
		case "1":
			ArrayList<Customer> cus = new ArrayList<>();
			cus = user.getCustomers();
			for (Customer c : cus) {
				if (c.getUsername().equals(username)) {
					if (c.getPassword().equals(password)) {
						c.CustomerOptions();
					} else {
						log.info("The password you provided was incorrect");
						login();
					}
				} else {
					log.info("The username you provided does not exist");
					login();
				}
			}

			break;
		case "2":
			ArrayList<Employee> emp = new ArrayList<>();
			emp = user.getEmployees();
			for (Employee e : emp) {
				if (e.getUsername().equals(username)) {
					if (e.getPassword().equals(password)) {
						e.EmployeeOptions();
					} else {
						log.info("The password you provided was incorrect");
						login();
					}
				} else {
					log.info("The username you provided does not exist");
					login();
				}

			}
			break;
		case "3":
			loginPage();
			break;
		default:
			System.out.println("That is not an option. Please try again.");
			login();
			break;
		}

	}

	public void register() {
		input = new Scanner(System.in);
		String first, last, userName, password, option;
		System.out.println("Welcome to the registration page");
		System.out.println("Please enter your first name");
		first = input.nextLine();
		System.out.println("Please enter your last name");
		last = input.nextLine();
		System.out.println("Please enter a Username");
		userName = input.nextLine();
		System.out.println("Please enter a password, this is case sensitive.");
		password = input.nextLine();

		ArrayList<Customer> cus = new ArrayList<>();
		cus = user.getCustomers();
		for (Customer c : cus) {
			if (userName.equals(c.getUsername())) {
				System.out.println("This username already exists, please try again");
				register();
			} else {
				Customer cust = new Customer(first, last, userName, password);
				userAdd.insertCustomer(cust);
				log.info(cust.getUsername() + " profile created");
				System.out.println();
				System.out.println("Your account has been created " + cust.getFirstName());
				System.out.println("If you would like to view your account Type 1");
				System.out.println("If you would like to exit the program then Type 2");
				option = input.nextLine();
				switch (option) {
				case "1":
					cust.CustomerOptions();
					break;
				case "2":
					input.close();
					System.out.println(
							"Thank you for checking out Tattoine Used Starfighter Emporium, please come again!");
					System.exit(0);
					break;
				default:
					loginPage();
					break;

				}
			}
		}
	}
}
