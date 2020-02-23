package com.revature.users;

import java.io.Serializable;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.load_page.LoadPage;
import com.revature.pojo.Car;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;

public class Employee implements Serializable {
	private static Logger log = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = -5370445995563054109L;
	private String firstName;
	private String lastName;
	private String username;
	private String password;

	public Employee() {
		super();
	}

	public Employee(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void EmployeeOptions() {
		System.out.println("Welcome " + this.firstName + " to your account.");
		System.out.println("Type 1 to see a list of vehicles");
		System.out.println("Type 2 to see offers for vehicles");
		System.out.println("Type 3 to accept or reject an offer for a vehicle");
		System.out.println("Type 4 to add a vehicle");
		System.out.println("Type 5 to remove a vehicle");
		System.out.println("Type 6 to view all payments");
		System.out.println("Type 7 to return to the login page.");
	}

	public void employeeDecision(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		EmployeeOptions();
		String option = input.nextLine();
		while (!option.equals("7")) {
			switch (option) {
			case "1":
				for (Car c : fleet.getFleet()) {
					System.out.println(c);
				}
				EmployeeOptions();
				option = input.nextLine();
				break;
			case "2":
				for (Car c : fleet.getFleet()) {
					if (!c.getOffers().isEmpty()) {
						c.toStringOffer();
					}
				}
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			case "3":
				System.out.println("To accept an offer Type 1");
				System.out.println("To reject an offer Type 2");
				System.out.println("To retrun to the previous menu Type 3");
				option = input.nextLine();
				fleet = acceptOrRejectoffer(option, users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			case "4":
				addVehicle(users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			case "5":
				fleet = removeVehicle(users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			case "6":
				for (Customer e : users.getCustomers()) {
					if (!e.getMyCars().isEmpty()) {
						for (Car c : e.getMyCars()) {
							System.out.println(e.getUsername() + " has " + " Monthly Payments of: $"
									+ c.getMonthlyPayments() + " left");
						}
					}

//				for (Car c : fleet.getFleet()) {
//					System.out.println(c.isSold());
//					if (c.isSold() == true) {
//						System.out.println(c.getOwner() +" has "+ c.getRemainingPayments()+" Monthly Payments of: $" + c.getMonthlyPayments());
//					}
				}
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			default:
				System.out.println("That is not a valid option, please select again");
				break;
			}
		}
		LoadPage login = new LoadPage();
//		login.loginPage(users, fleet);
	}

	public Fleet acceptOrRejectoffer(String option, Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		String vin, username;
		System.out.println("What is the VIN of the vehicle offer");
		vin = input.nextLine();
		System.out.println("What is the username of the vehicle offer");
		username = input.nextLine();
		switch (option) {
		case "1":
			for (Car c : fleet.getFleet()) {
				if (c.getVIN().equals(vin)) {
					for (Customer cus : users.getCustomers()) {
						if (cus.getUsername().equalsIgnoreCase(username)) {
							c.setSold(true);
							c.setPrice(c.getOffers().get(username));
							cus.setMyCars(c);
//							c.setOwner(cus);
							c.getOffers().clear();
							fleet.removeCar(c);
							log.info(c.toString() + " was sold to customer " + cus.toString());
							return fleet;
						}
					}
				}
			}
			System.out.println("There is no vehicle with VIN " + vin);
			return fleet;
		case "2":
			for (Car c : fleet.getFleet()) {
				if (c.getOffers().containsKey(username)) {
					c.getOffers().remove(username);
					log.info("Offers were removed for user " + username);
				}
			}
			return fleet;
		case "3":
			break;
		default:
			System.out.println("That is not a valid option, please try again");
			break;
		}
		return fleet;
	}

	public Fleet addVehicle(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		double price;
		String vIN, make, model, mileage, year;
		System.out.println("Please enter the VIN of the vehicle.");
		vIN = input.nextLine();
		for (Car c : fleet.getFleet()) {
			if (c.getVIN().equals(vIN)) {
				System.out.println("There is already a vehicle with this VIN, please try again");
			}
		}
		System.out.println("Please enter the Year of the vehicle.");
		year = input.nextLine();
		System.out.println("Please enter the Make of the vehicle.");
		make = input.nextLine();
		System.out.println("Please enter the Model of the vehicle.");
		model = input.nextLine();
		System.out.println("Please enter the Mileage of the vehicle.");
		mileage = input.nextLine();
		System.out.println("Please enter the Price of the vehicle.");
		price = Double.parseDouble(input.nextLine());
		Car c = new Car(price, vIN, make, model, year);
		fleet.setFleet(c);
		log.info(c.toString() + " was created by " + this.username);
		return fleet;

	}

	public Fleet removeVehicle(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		String vin;
		System.out.println("Please enter the VIN of the vehicle you would like to remove");
		vin = input.nextLine();
		for (Car c : fleet.getFleet()) {
			if (c.getVIN().equals(vin)) {
				fleet.removeCar(c);
				log.info(c.toString() + " was removed by employee " + this.username);
				return fleet;
			}
		}
		System.out.println("The car with VIN " + vin + " was not found");
		return fleet;
	}
}