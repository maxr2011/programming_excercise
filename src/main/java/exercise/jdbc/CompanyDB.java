package exercise.jdbc;

import database.JDBC;
import exercise.objects.Company;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDB {

	// Variablen

	//Tabellenname
	private static final String table = "company_table";

	// Methode um eine Company hinzuzufügen
	private static void insertCompany(Company c) {

		String insertSQL = "INSERT INTO " + table + "(Date, Security, Weighting) " + "VALUES (?,?,?)";

		try (Connection conn = JDBC.connect();
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, c.getDate());
			pstmt.setString(2, c.getSecurity());
			pstmt.setDouble(3, c.getWeighting());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (CountryDB)
	public static void writeDataToDatabase(ArrayList<Company> cl) {

		// Tabelle erstellen falls nicht existiert
		String createSQL = "CREATE TABLE IF NOT EXISTS " + table
				+ " (Date varchar(40), Security varchar(100), weighting float(53));";
		JDBC.minimalQuery(createSQL);

		// Company Liste in Datenbanktabelle schreiben
		for (Company c : cl) {
			insertCompany(c);
		}

	}

	// Methode um Liste an Companies aus der Datenbank auszulesen
	public static ArrayList<Company> readFromDatabase() {

		ArrayList<Company> cl = new ArrayList<>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM " + table + ";";

		try (Connection conn = JDBC.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(selectSQL)) {
			while (rs.next()) {

				cl.add(new Company(rs.getString("date"), rs.getString("security"), rs.getDouble("weighting")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cl;
	}

}
