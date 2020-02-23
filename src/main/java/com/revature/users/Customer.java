package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.CarDAOImpl;
import com.revature.load_page.LoadPage;
import com.revature.pojo.Car;
import com.revature.pojo.Fleet;

public class Customer implements Serializable {
	private static Logger log = Logger.getRootLogger();
	Fleet fleet = new Fleet();
	ArrayList<Car> allCars = new ArrayList<>();
	CarDAOImpl getOwnerCars = new CarDAOImpl();
	/**
	 * 
	 */
	private static final long serialVersionUID = -2902279992146909132L;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private ArrayList<Car> myCars = new ArrayList<Car>();

//	public public Customer() {
//		super();
//	}
	public Customer() {
		super();
	}

	public Customer(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
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

	public ArrayList<Car> getMyCars() {
		return myCars;
	}

	public void setMyCars(Car car) {
		myCars.add(car);
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", myCars="
				+ myCars + "]";
	}

	public void CustomerOptions() {
		String option;
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome " + this.firstName + " to your account.");
		System.out.println("Type 1 to see a list of vehicles available for sale");
		System.out.println("Type 2 to bid on a vehicle that you are interested in");
		System.out.println("Type 3 to see vehicles that you own");
		System.out.println("Type 4 to see how many payments that you have left on your vehicles");
		System.out.println("Type 5 to go back to the main login screen.");
		option = input.nextLine();
		customerDecision(option);
	}

	public void customerDecision(String option) {
		Scanner input = new Scanner(System.in);
		allCars = fleet.getFleet();
		switch (option) {
		case "1":
			// TODO make sysout look cleaner
			for (Car c : allCars) {
				if (c.getOwner() == null) {
					System.out.println(c);
				}
			}
			System.out.println();
			CustomerOptions();
			break;
		case "2":
			bid();
			System.out.println();
			CustomerOptions();
			break;
		case "3":
			// TODO make sysout look cleaner
			for (Car c : getOwnerCars.selectCarByOwner(this.username))
			if (myCars.isEmpty()) {
				System.out.println("You do not own any vehicles yet.");
			} else {
				System.out.println(myCars.toString());
				System.out.println();
			}
			CustomerOptions();
			break;
		case "4":
			// TODO make sysout look cleaner
			if (myCars.isEmpty()) {
				System.out.println("You have not purchased any vehicles yet");
			} else {
				for (Car c : myCars) {
					System.out.println(
							"You have " + " payments of $" + c.getMonthlyPayments() + "left on " + c.toString());
				}
			}
			System.out.println();
			CustomerOptions();
			break;
		case "5":
			LoadPage login = new LoadPage();
			login.loginPage();
			break;
		default:
			System.out.println("That is not a vaild option, please try again");
			CustomerOptions();
			break;
		}
	}

	public void bid() {
		Scanner input = new Scanner(System.in);
		String carVIN;
		Double offerPrice;
		System.out.println("What is the VIN of the vehicle that you want to bid on");
		carVIN = input.nextLine();
		System.out.println("How much do you want to offer for the vehicle");
		offerPrice = Double.parseDouble(input.nextLine());
		for (Car c : allCars) {
			if (c.getVIN().equals(carVIN)) {

//				c.getOffers().put(this.username, offerPrice);
				log.info(this.username + " made an offer on " + c.toString());
				break;
			}
		}
	}
}
