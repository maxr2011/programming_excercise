package exercise.jdbc;

import database.JDBC;
import exercise.objects.Company;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("CompanyDB")
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

	// Methode um Liste an Companies in Datenbank zu schreiben (CompanyDB)
	public static void writeDataToDatabase(List<Company> cl) {

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
	public static List<Company> readFromDatabase() {

		List<Company> cl = new ArrayList<>();

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

// --Commented out by Inspection START (04.09.18 11:06):
//	// Tabelle leeren
//	public static void clearTable(String table) {
//		JDBC.clearTable(table);
//	}
// --Commented out by Inspection STOP (04.09.18 11:06)

// --Commented out by Inspection START (04.09.18 11:06):
//	// Tabelle löschen
//	public static void dropTable(String table) {
//		JDBC.dropTable(table);
//	}
// --Commented out by Inspection STOP (04.09.18 11:06)

}
