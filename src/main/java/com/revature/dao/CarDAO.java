package com.revature.dao;

import java.util.ArrayList;

import com.revature.pojo.Car;

public interface CarDAO {
	// Car Implementation
	// Add a Car
	public void insertCar(Car c);

	// Find a Car by the VIN
	public Car selectCarByVin(String vin);

	// Return all of the Cars
	public ArrayList<Car> selectAllCars();

	public ArrayList<Car> selectCarByOwner(String username);

	void updateCarOwner(String username, String vin);

	public void getAllOffers();

	void insertOffers(String username, String vin, double amount);

}
