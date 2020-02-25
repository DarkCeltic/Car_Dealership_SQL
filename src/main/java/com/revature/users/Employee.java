package com.revature.users;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.CarDAOImpl;
import com.revature.load_page.LoadPage;
import com.revature.pojo.Car;

public class Employee {
	private static Logger log = Logger.getRootLogger();
	ArrayList<Car> allCars = new ArrayList<>();
	CarDAOImpl carDatabase = new CarDAOImpl();
	ArrayList<Car> ownedCars = new ArrayList<>();

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

	public void employeeOptions() {
		Scanner input = new Scanner(System.in);
		String option;
		System.out.println("Welcome " + this.firstName + " to your account.");
		System.out.println("Type 1 to see a list of vehicles");
		System.out.println("Type 2 to see offers for vehicles");
		System.out.println("Type 3 to accept or reject an offer for a vehicle");
		System.out.println("Type 4 to add a vehicle");
		System.out.println("Type 5 to remove a vehicle");
		System.out.println("Type 6 to view all payments");
		System.out.println("Type 7 to return to the login page.");
		option = input.nextLine();
		employeeDecision(option);
	}

	public void employeeDecision(String option) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		allCars = carDatabase.selectAllCars();
		ownedCars = carDatabase.selectAllOwnedCars();
		switch (option) {
		case "1":
			System.out.println("Year\t\tMake\t\tModel\t\tPrice\t\tVIN");
			for (Car c : allCars) {
				if (c.getOwner() == null) {
					System.out.println(c);
				}
			}
			employeeOptions();
			option = input.nextLine();
			break;
		case "2":
			ArrayList<String> temp = carDatabase.getAllOffers();
			if (temp.isEmpty()) {
				System.out.println("There are no offers yet!");
			} else {
				// TODO check to make sure that this works
				String[] username = new String[10];
				String[] vin = new String[10];
				Double[] amount = new Double[10];
				int i = 0;
				for (String s : temp) {
					String[] temps = s.split(" ");
					username[i] = temps[1];
					vin[i] = temps[0];
					amount[i] = Double.parseDouble(temps[2]);
					i++;
				}
				System.out.println("Year\t\tMake\t\tModel\t\tVIN\t\tUsername\t\tOffer");
				for (Car c : allCars) {
					for (i = 0; i < username.length; i++) {
						if (vin[i] == null) {
							break;
						} else {
							if (vin[i].equals(c.getVIN())) {
								String price = String.format("%.2f", amount[i]);
								System.out.format("%4s%20s%18s%12s%15s%251s", c.getYear(), c.getMake(), c.getModel(),
										vin[i], username[i], "$" + price);
							}
						}
					}
				}
			}
			System.out.println();
			employeeOptions();
			break;
		case "3":
			System.out.println("To accept an offer Type 1");
			System.out.println("To reject an offer Type 2");
			System.out.println("To retrun to the previous menu Type 3");
			option = input.nextLine();
			acceptOrRejectoffer(option);
			System.out.println();
			employeeOptions();
			break;
		case "4":
			addVehicle();
			System.out.println();
			employeeOptions();
			break;
		case "5":
			removeVehicle();
			System.out.println();
			employeeOptions();
			break;
		case "6":
			// TODO
			System.out.println("Year\t\tMake\t\tModel\t\tVIN\t\tOwner\t\tMonthly Payment");
			for (Car c : ownedCars) {
				String formatedPrice = String.format("%,.2f", c.getMonthlyPayments());
				System.out.format("%4s%20s%18s%10s%15s%25s", c.getYear(), c.getMake(), c.getModel(), c.getVIN(),
						c.getOwner(), "$" + formatedPrice + " X 60");
			}
			System.out.println();
			employeeOptions();
			break;
		case "7":
			LoadPage login = new LoadPage();
			login.loginPage();
		default:
			System.out.println("That is not a valid option, please select again");
			break;
		}
	}

	public void acceptOrRejectoffer(String option) {
		Scanner input = new Scanner(System.in);
		String vin, username;
		System.out.println("What is the VIN of the vehicle offer");
		vin = input.nextLine();
		System.out.println("What is the username of the vehicle offer");
		username = input.nextLine();
		switch (option) {
		case "1":
			Double amount = carDatabase.getOfferAmount(username, vin);
			carDatabase.acceptOffer(vin, username, amount);
			System.out.println("Year\t\tMake\t\tModel\t\tVIN");
			for (Car c : allCars) {
				if (c.getVIN().equals(vin)) {
					String price = String.format("%,.2f", amount);
					log.info(String.format("%4s%20s%18s%10s", c.getYear(), c.getMake(), c.getModel(), c.getVIN())
							+ " was sold to customer " + username + " for $" + price);
				}
			}
			employeeOptions();
		case "2":
			carDatabase.removeOffer(username, vin);
			employeeOptions();
		case "3":
			break;
		default:
			System.out.println("That is not a valid option, please try again");
			break;
		}
	}

	// TODO Fix this for SQL
	public void addVehicle() {
		Scanner input = new Scanner(System.in);
		double price;
		String make, model, year;
		System.out.println("Please enter the Year of the vehicle.");
		year = input.nextLine();
		System.out.println("Please enter the Make of the vehicle.");
		make = input.nextLine();
		System.out.println("Please enter the Model of the vehicle.");
		model = input.nextLine();
		System.out.println("Please enter the Price of the vehicle.");
		price = Double.parseDouble(input.nextLine());
		Car c = new Car(price, make, model, year);
		carDatabase.insertCar(c);
		log.info(c.getYear() + " " + c.getMake() + " " + c.getModel() + " " + "$" + c.getPrice() + " was created by "
				+ this.username);
	}

	// TODO fix this for SQL
	public void removeVehicle() {
		Scanner input = new Scanner(System.in);
		String vin;
		System.out.println("Please enter the VIN of the vehicle you would like to remove");
		vin = input.nextLine();
		carDatabase.removeCar(vin);
		for (Car c : allCars) {
			if (c.getVIN().equals(vin)) {
				log.info(c.getYear() + " " + c.getMake() + " " + c.getModel() + " " + c.getVIN()
						+ " was removed by employee " + this.username);
			}
		}
	}
}