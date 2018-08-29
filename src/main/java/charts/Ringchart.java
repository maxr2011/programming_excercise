package charts;

import charts.objects.Company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import streams.Fund;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ringchart {

	//Variablen

	//Datenbankanbindung
	private static final String url = "jdbc:postgresql://localhost/exercise";
	private static final String user = "mare";
	private static final String password = "toor";

	//Tabellenname
	private static final String table = "company_table";

	//CSV file path
	private static final String SAMPLE_CSV_FILE_PATH = "Ring Chart Data.csv";

	//CSV file header
	private static final String[] FILE_HEADER_MAPPING = {"Date", "Security", "Weighting"};

	public static ArrayList<Company> readCsvFile(String fileName) {

		ArrayList<Company> companies = new ArrayList<Company>();

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		//header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING).withSkipHeaderRecord();

		try {

			//Liste erstellen
			List<Fund> funds = new ArrayList<Fund>();

			//FileReader initialisieren
			fileReader = new FileReader(fileName);

			//CSVParser initialisieren
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			//Liste der csvRecords holen
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			for (CSVRecord c : csvRecords) {

				Company Com = new Company(c.get("Date"), c.get("Security"), Double.parseDouble(c.get("Weighting")));
				companies.add(Com);

			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}

		return companies;

	}

	// Datenbankverbindung aufbauen JDBC
	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			//System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	// Hilfsmethode für JDBC
	public static void minimal_query(String SQL) {

		try (Connection testconn = connect();
			 Statement stmt = testconn.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL)) {
			// nothing to do
		} catch (SQLException e) {
			// nothing to do
		}

	}

	// Methode um eine Company hinzuzufügen
	public static void insertCompany(Company c) {

		String insertSQL = "INSERT INTO " + table + "(Date, Security, Weighting) " + "VALUES (?,?,?)";

		long id = 0;

		try (Connection conn = connect();
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, c.getDate());
			pstmt.setString(2, c.getSecurity());
			pstmt.setDouble(3, c.getWeighting());

			int affectedRows = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (JDBC)
	public static void writeDataToDatabase(ArrayList<Company> cl) {

		// Tabelle erstellen falls nicht existiert
		String createSQL = "CREATE TABLE IF NOT EXISTS " + table
				+ " (Date varchar(40), Security varchar(100), weighting float(53));";
		minimal_query(createSQL);

		// Tabelle leeren
		String deleteSQL = "DELETE FROM " + table + ";";
		minimal_query(deleteSQL);

		// Company Liste in Datenbanktabelle schreiben
		for (Company c : cl) {
			insertCompany(c);
		}

	}

	// Methode um Liste an Companies aus der Datenbank auszulesen
	public static ArrayList<Company> readFromDatabase() {

		ArrayList<Company> cl = new ArrayList<Company>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM " + table + ";";

		try (Connection conn = connect();
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

	// Mainmethode
	public static void main(String[] args) {

		/** TODO
		 * CSV einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Als Chart exportieren -> PDF
		 */

		// CSV einlesen -> Companies
		ArrayList<Company> companiesCSV = readCsvFile(SAMPLE_CSV_FILE_PATH);

		// Daten in die Datenbank schreiben
		writeDataToDatabase(companiesCSV);

		// Daten von der Datenbank übergeben
		ArrayList<Company> companiesDB = readFromDatabase();

		for (Company c : companiesDB) {
			System.out.println(c.getSecurity());
		}

	}

}
