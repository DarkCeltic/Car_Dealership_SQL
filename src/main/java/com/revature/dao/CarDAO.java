package com.revature.dao;

import java.util.ArrayList;

import com.revature.pojo.Car;

public interface CarDAO {
	// Car Implementation
	// Add a Car
	public void insertCar(Car c);

	public void removeCar(String vin);

	// Find a Car by the VIN
//	public Car selectCarByVin(String vin);

	// Return all of the Cars
	public ArrayList<Car> selectAllCars();

	public ArrayList<Car> selectCarByOwner(String customerUsername);

	// This is handled by the stored procedure
//	void updateCarOwner(String customerUsername, String vin, Double amount);

	public ArrayList<String> getAllOffers();

	public void insertOffers(String customerUsername, String vin, double amount);

	public void removeOffer(String customerUsername, String vin);

	public void acceptOffer(String vin, String customerUsername, Double amount);

	public Double getOfferAmount(String customerUsername, String vin);

	public ArrayList<Car> selectAllOwnedCars();

}
