package com.revature.dao;

import java.util.ArrayList;

import com.revature.pojo.Car;

public interface CarDAO {
	// Car Implementation
	// Add a Car
	public void insertCar(Car c);

	// Remove a Car
	public void removeCar(String vin);

	// Return all of the Cars
	public ArrayList<Car> selectAllCars();

	// Get all cars that are owned by a user
	public ArrayList<Car> selectCarByOwner(String customerUsername);

	// This is handled by the stored procedure
	// void updateCarOwner(String customerUsername, String vin, Double amount);
	// Get all current offers
	public ArrayList<String> getAllOffers();

	// Insert an offer
	public void insertOffers(String customerUsername, String vin, double amount);

	// Remove an offer
	public void removeOffer(String customerUsername, String vin);

	// Accept an offer
	public void acceptOffer(String vin, String customerUsername, Double amount);

	// Get the price of the offer that was accepted
	public Double getOfferAmount(String customerUsername, String vin);

	// Select all cars that are owned
	public ArrayList<Car> selectAllOwnedCars();

}
