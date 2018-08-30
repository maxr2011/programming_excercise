package exercise.jdbc;

import exercise.objects.Country;

import java.sql.*;
import java.util.ArrayList;

public class CountryDB {

	//Variablen

	//Tabellenname
	private static final String table = "country_table";

	// Methode um ein Country hinzuzufügen
	private static void insertCountry(Country c) {

		String insertSQL = "INSERT INTO " + table + " (name, weight) " + "VALUES (?,?);";

		try (Connection conn = database.JDBC.connect();
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getWeight());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (CountryDB)
	public static void writeDataToDatabase(ArrayList<Country> cl) {

		// Tabelle erstellen falls nicht existiert
		String createSQL = "CREATE TABLE IF NOT EXISTS " + table + " (name varchar(40), weight float(53));";
		database.JDBC.minimalQuery(createSQL);

		// Country Liste in Datenbanktabelle schreiben
		for (Country c : cl) {
			insertCountry(c);
		}

	}

	// Methode um Liste an Countries aus der Datenbank auszulesen
	public static ArrayList<Country> readFromDatabase() {

		ArrayList<Country> cl = new ArrayList<>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM " + table + ";";

		try (Connection conn = database.JDBC.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(selectSQL)) {

			while (rs.next()) {

				cl.add(new Country(rs.getString("name"), rs.getDouble("weight")));

			}

		} catch (SQLException e) {
			System.out.println("here");
			e.printStackTrace();
		}

		return cl;
	}

}
