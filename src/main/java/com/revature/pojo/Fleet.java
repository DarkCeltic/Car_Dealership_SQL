package com.revature.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.dao.CarDAO;
import com.revature.dao.CarDAOImpl;
import com.revature.pojo.Car;

public class Fleet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1687285853713279674L;
	ArrayList<Car> fleet = new ArrayList<>();
	CarDAOImpl cars = new CarDAOImpl();

	public void setFleet(ArrayList<Car> fleet) {
		this.fleet = fleet;
	}

	public ArrayList<Car> getFleet() {
		fleet = cars.selectAllCars();
		return fleet;
	}

	public void setFleet(Car car) {
		fleet.add(car);
	}

	public void removeCar(Car car) {
		fleet.remove(car);
	}

}
