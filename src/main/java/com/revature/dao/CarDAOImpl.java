package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.revature.pojo.Car;

public class CarDAOImpl implements CarDAO {

	private static String url = "jdbc:postgresql://localhost:5432/Car_Dealership";
	private static String username = "postgres";
	private static String password = "postgres";

	@Override
	public void insertCar(Car c) {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Cars VALUES(?,?,?,?)");
			ps.setString(1, c.getMake());
			// we are setting the first question mark to be the name that belongs to our pet
			// object
			ps.setString(2, c.getModel());
			ps.setString(3, c.getYear());
			ps.setDouble(4, c.getPrice());
			// we are setting the second question mark to be the type that belongs to our
			// pet object
			ps.execute();
			// allows us to execute a query
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Car selectCarByVin(String vin) {
		Car car = null;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cars WHERE vin=?");
			ps.setString(1, vin);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				car = new Car(rs.getDouble("price"), rs.getString("vin"), rs.getString("make"), rs.getString("model"),
						rs.getString("year"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}

	@Override
	public ArrayList<Car> selectAllCars() {
		ArrayList<Car> car = new ArrayList<Car>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cars WHERE owner IS null");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				car.add(new Car(rs.getDouble("price"), rs.getString("vin"), rs.getString("make"), rs.getString("model"),
						rs.getString("year")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}

	@Override
	public ArrayList<Car> selectCarByOwner(String username) {
		ArrayList<Car> car = new ArrayList<Car>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cars WHERE owner=?");
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				car.add(new Car(rs.getDouble("price"), rs.getString("vin"), rs.getString("make"), rs.getString("model"),
						rs.getString("year")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}

	@Override
	public void updateCarOwner(String username, String vin) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("UPDATE Cars SET owner=? WHERE vin=?");
			ps.setString(1, username);
			ps.setString(2, vin);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllOffers() {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT vin, username, amount FROM offers)");
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				String vin = results.getString("vin");
				String username = results.getString("username");
				Double amount = results.getDouble("amount");
				System.out.println("Vin:" + vin + " Username:" + username + " Amount:$" + amount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public void insertOffers(String username, String vin, double amount) {
//		try (Connection conn = DriverManager.getConnection(url, username, password)) {
//			PreparedStatement ps = conn.prepareStatement("INSERT INTO offers VALUES(?,?,?,?)");
//			ps.setString(1, vin);
//			ps.setString(3, username);
//			ps.setDouble(4, amount);
//			ps.setBoolean(5, false);
//			ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void insertOffers(String username, String vin, double amount) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM offers WHERE username= ? AND vin=?");
			ps.setString(1, username);
			ps.setString(2, vin);
			ResultSet result = ps.executeQuery();
			
			if (!result.next()) {
				ps = conn.prepareStatement("INSERT INTO offers(vin, username, amount, active) VALUES(?,?,?,?)");
				ps.setString(1, vin);
				ps.setString(2, username);
				ps.setDouble(3, amount);
				ps.setBoolean(4, false);
				ps.executeUpdate();
			} else {
				System.out.println("You have already submitted an offer for this vehicle");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
