package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.revature.dao.CarDAOImpl;
import com.revature.dao.UsersDAOImpl;
import com.revature.pojo.Car;
import com.revature.users.Customer;

class CarDAOImplTest {
	CarDAOImpl test = new CarDAOImpl();
	UsersDAOImpl testCus = new UsersDAOImpl();

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
	void testCarDAOFuncionalities() {
		Car c = new Car(2500, "Ford", "Bronco", "1988");
		test.insertCar(c);
		ArrayList<Car> temps = new ArrayList<>();
		temps = test.selectAllCars();
		String vin = "5";
		for (Car c3 : temps) {
			if (c3.getMake().equals(c.getMake()) && c3.getModel().equals(c.getModel())) {
				vin = c3.getVIN();
			}
		}
		assertFalse("This makes sure that a car was entered and the array is not empty", temps.isEmpty());

		temps = test.selectAllOwnedCars();
		assertFalse("This tests to make sure that owned cars is not empty", temps.isEmpty());
		test.removeCar(vin);
		temps = test.selectAllCars();
		// This makes sure that the car was removed
		assertFalse(temps.contains(vin));
	}

	@Test
	void testOfferFuncionalities() {
		ArrayList<String> offers = new ArrayList<>();
		assertNull("This returns a list of all the usernames that have an active offer", test.getAllOffers());
		Car c = new Car(1800, "Ford", "Focus", "1999");
		Customer cus = new Customer("Bob", "Hope", "Bhop", "peach");
		testCus.insertCustomer(cus);
		test.insertCar(c);
		ArrayList<Car> tempCar = new ArrayList<>();
		tempCar = test.selectAllCars();
		String vin = null;
		for (Car c3 : tempCar) {
			if (c3.getMake().equals(c.getMake()) && c3.getModel().equals(c.getModel())) {
				vin = c3.getVIN();
			}
		}
		// Inserting the offer here
		test.insertOffers("Bhop", vin, 1500.0);

		// Getting all of the offers
		offers = test.getAllOffers();
//		 Concatenating the string to show what the output will display
		String con = vin + " Bhop 1500.0";
		assertTrue("This checks to make sure that the offer was submitted and that the method can print",
				offers.contains(con));
	
		Double offerAmount = test.getOfferAmount(cus.getUsername(), vin);
		System.out.println(offerAmount);
		assertEquals(1500.0, offerAmount, 0);
	
		test.removeOffer("Bhop", vin);
		offers = test.getAllOffers();

		con = vin + " Bhop 1800";
		assertNull("This checks to make sure that the offer was removed",
				test.getAllOffers());
	
//		Reinsert the offer to test the accept
		test.insertOffers(cus.getUsername(), vin, 1500);
		//Accept the offer
		test.acceptOffer(vin, "Bhop", 1800.0);

		 test.selectCarByOwner("Bhop");
		 
		 assertNotNull("This checks to make sure that the offer was accepted", test.selectCarByOwner("Bhop"));
	}

}
