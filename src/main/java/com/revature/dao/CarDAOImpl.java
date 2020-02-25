package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Car;

public class CarDAOImpl implements CarDAO {

	private static String url = "jdbc:postgresql://localhost:5432/Car_Dealership";
	private static String username = "postgres";
	private static String password = "postgres";

	@Override
	public void insertCar(Car c) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Cars VALUES(?,?,?,?)");
			int year = Integer.parseInt(c.getYear());
			ps.setString(1, c.getMake());
			ps.setString(2, c.getModel());
			ps.setInt(3, year);
			ps.setDouble(4, c.getPrice());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void removeCar(String vin) {
		int vinInteger = Integer.parseInt(vin);
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM cars WHERE vin=? AND owner is null");
			ps.setInt(1, vinInteger);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public ArrayList<Car> selectCarByOwner(String ownerUsername) {
		ArrayList<Car> car = new ArrayList<Car>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cars WHERE owner=?");
			ps.setString(1, ownerUsername);

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
	public ArrayList<Car> selectAllOwnedCars() {
		ArrayList<Car> car = new ArrayList<Car>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cars WHERE owner IS NOT null");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				car.add(new Car(rs.getDouble("price"), rs.getString("owner"), rs.getString("vin"), rs.getString("make"),
						rs.getString("model"), rs.getString("year")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}
	// THis is was overridded by the stored procedure
	// @Override
	// public void updateCarOwner(String ownerUsername, String vin, Double amount) {
	// try (Connection conn = DriverManager.getConnection(url, username, password))
	// {
	// PreparedStatement ps = conn.prepareStatement("UPDATE Cars SET owner=?,
	// price=? WHERE vin=?");
	// ps.setString(1, ownerUsername);
	// ps.setDouble(2, amount);
	// ps.setString(3, vin);
	// ps.executeUpdate();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public ArrayList<String> getAllOffers() {
		ArrayList<String> tempArray = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT vin, username, amount FROM offers WHERE active=true");
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				String vin = results.getString("vin");
				String username = results.getString("username");
				Double amount = results.getDouble("amount");
				String temp = vin + " " + username + " " + amount;
				tempArray.add(temp);
			}
			return tempArray;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insertOffers(String customerUsername, String vin, double amount) {
		int vinInteger = Integer.parseInt(vin);
		String name = null;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT username FROM offers WHERE username= ? AND vin=?");
			ps.setString(1, customerUsername);
			ps.setInt(2, vinInteger);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				name = result.getString("username");
			}
			if (name == null) {
				ps = conn.prepareStatement("INSERT INTO offers(vin, username, amount, active) VALUES(?,?,?,?)");
				ps.setInt(1, vinInteger);
				ps.setString(2, customerUsername);
				ps.setDouble(3, amount);
				ps.setBoolean(4, true);
				ps.executeUpdate();
			} else {
				System.out.println("You have already submitted an offer for this vehicle");
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeOffer(String customerUsername, String vin) {
		int vinInteger = Integer.parseInt(vin);
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM offers WHERE username=? AND vin=?");
			ps.setString(1, customerUsername);
			ps.setInt(2, vinInteger);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void acceptOffer(String vin, String customerUsername, Double amount) {
		int vinInteger = Integer.parseInt(vin);
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			CallableStatement callStmnt = conn.prepareCall("{CALL accept_offer(?, ?, ?)}");
			callStmnt.setInt(1, vinInteger);
			callStmnt.setString(2, customerUsername);
			callStmnt.setDouble(3, amount);
			callStmnt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Double getOfferAmount(String customerUsername, String vin) {
		int vinInteger = Integer.parseInt(vin);
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT amount FROM offers WHERE username=? AND vin=?");
			ps.setString(1, customerUsername);
			ps.setInt(2, vinInteger);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				Double amount = results.getDouble("amount");
				return amount;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
