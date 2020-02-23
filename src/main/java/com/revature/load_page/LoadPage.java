package com.revature.load_page;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.*;
import com.revature.pojo.Database;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;
import com.revature.users.Customer;
import com.revature.users.Employee;

public class LoadPage {

	DatabaseDAO dat = new DatabaseSerializationDAO();
	Scanner input = new Scanner(System.in);
	private static Logger log = Logger.getRootLogger();

	public void loginPage(Users users, Fleet fleet) {

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
				login(users, fleet);
				break;
			case "2":
				register(users, fleet);
				break;
			case "3":
				saveData(users, fleet);
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

	public void login(Users users, Fleet fleet) {
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
			for (Customer cus : users.getCustomers()) {
				System.out.println(cus.getUsername());
				System.out.println(cus.getPassword());
				if (cus.getUsername().equalsIgnoreCase(username) && cus.getPassword().equals(password)) {
					log.info(username + " logged in");
					cus.CustomerOptions(users, fleet);
				}
			}
			log.info("Sorry, your information was incorrect");
			break;
		case "2":
			for (Employee emp : users.getEmployees()) {
				System.out.println(emp.getUsername());
				if (emp.getUsername().equalsIgnoreCase(username) && emp.getPassword().equals(password)) {
					log.info(username + " logged in");
					emp.employeeDecision(users, fleet);
				}
			}
			log.info("Sorry, your information was incorrect");
			break;
		case "3":
			loginPage(users, fleet);
			break;
		default:
			System.out.println("That is not an option. Please try again.");
			login(users, fleet);
			break;
		}
	}

	public void register(Users users, Fleet fleet) {
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
		for (Customer cus : users.getCustomers()) {
			if (userName.equals(cus.getUsername())) {
				System.out.println("This username already exists, please try again");
				register(users, fleet);
			} else {
				Customer cust = new Customer(first, last, userName, password);
				users.setCustomers(cust);
				log.info(cust + " created");
				System.out.println();
				System.out.println("Your account has been created " + cust.getFirstName());
				System.out.println("If you would like to view your account Type 1");
				System.out.println("If you would like to exit the program then Type 2");
				option = input.nextLine();
				switch (option) {
				case "1":
					cust.CustomerOptions(users, fleet);
					break;
				case "2":
					saveData(users, fleet);
					input.close();
					System.out.println(
							"Thank you for checking out Tattoine Used Starfighter Emporium, please come again!");
					System.exit(0);
					break;
				default:
					loginPage(users, fleet);
					break;

				}
			}
		}
	}

	public void saveData(Users user, Fleet fleet) {
		Database data = new Database(user, fleet);
		dat.createDatabase(data);
	}

	public void loadData() {
		Database data;
		data = dat.loadDatabase();
		loginPage(data.getUsers(), data.getFleet());
	}
}
