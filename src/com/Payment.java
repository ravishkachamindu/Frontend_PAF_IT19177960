package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	// DB connection
	private Connection connect() {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paymentdb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readPayment() {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Username</th>" + "<th>Amount</th><th>Type</th>" + "<th>CrdType</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				String PaymentID = Integer.toString(rs.getInt("PaymentID"));
				String Username = rs.getString("Username");
				String Amount = rs.getString("Amount");
				String Type = rs.getString("Type");
				String CrdType = rs.getString("CrdType");

				// Add into the html table

				// output += "<tr><td>" + PaymentID + "</td>";//<input id='hidPaymentIDUpdate'
				// name='hidPaymentIDUpdate' type='hidden' value='"
				output += "<td>" + Username + "</td>"; // + PaymentID + "'>" + Username + "</td>";
				output += "<td>" + Amount + "</td>";
				output += "<td>" + Type + "</td>";
				output += "<td>" + CrdType + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-PaymentID='"
						+ PaymentID + "'>" + "</td></tr>";

			}

			con.close();

			// Complete the html table

			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Database.";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String insertPayment(String Username, String Amount, String Type, String CrdType) {

		String output = "";

		System.out.println("insert " + Username + Amount + Type + CrdType);

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into payment (`Username`,`Amount`,`Type`,`CrdType`)" + " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Username);
			preparedStmt.setString(2, Amount);
			preparedStmt.setString(3, Type);
			preparedStmt.setString(4, CrdType);

			// execute the statement
			preparedStmt.execute();
			con.close();

			// Create JSON Object to show successful message.
			String newPayment = readPayment();
			output = "{\"Status\":\"Success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			// Create JSON Object to show Error message.
			output = "{\"Status\":\"Error\", \"data\": \"Error while Inserting Payment Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatePayment(String Username, String Amount, String Type, String CrdType, int PaymentID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "update payment SET Username=?,Amount=?,Type=?,CrdType=? WHERE PaymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Username);
			preparedStmt.setString(2, Amount);
			preparedStmt.setString(3, Type);
			preparedStmt.setString(4, CrdType);
			preparedStmt.setInt(5, PaymentID);

			// execute prepared statement
			preparedStmt.execute();
			con.close();

			// create JSON object to show successful message
			String newPayment = readPayment();
			output = "{\"Status\":\"Success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"Status\":\"Error\", \"data\": \"Error while Updating Payment Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(String PaymentID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where PaymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, Integer.parseInt(PaymentID));
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayment();
			output = "{\"Status\":\"Success\", \"data\": \"" + newPayment + "\"}";

		} catch (Exception e) {

			output = "{\"Status\":\"Error\", \"data\": \"Error while Deleting Payment Details.\"}";
			System.err.println(e.getMessage());

		}

		return output;

	}
}