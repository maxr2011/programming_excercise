package charts;

import charts.objects.Country;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Piechart {

	//Variablen

	//Datenbankanbindung
	private static final String url = "jdbc:postgresql://localhost/exercise";
	private static final String user = "mare";
	private static final String password = "toor";

	//Tabellenname
	private static final String table = "country_table";

	//Pfad zur xls Datei
	public static String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Methode um xls Datei einzulesen
	public static ArrayList<Country> readXLSFile() throws IOException {

		ArrayList<Country> countries = new ArrayList<Country>();

		InputStream ExcelFileToRead = new FileInputStream(EXAMPLE_XLS_FILE);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();
		int j = 0;

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			// Header überspringen (erste Zeile exel)
			j++;
			if(j == 1) continue;

			//Country Objekt erstellen
			Country c = new Country();
			boolean isNull = false;

			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {

					//funktioniert nur in diesem Fall
					String stringcell = "";
					stringcell += cell.getStringCellValue();
					c.setName(stringcell);

				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

					//funktioniert nur in diesem Fall
					c.setWeight(cell.getNumericCellValue());

				} else {
					//U Can Handel Boolean, Formula, Errors
				}

			}

			if(!isNull) {
				//letzte Zeile (null-Zeile überspringen)
				if(!c.nameIsNull()) {
					countries.add(c);
				}
			}

		}

		return countries;

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

		try(
				Connection testconn = connect();
				Statement stmt = testconn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL)
		){
			// nothing to do
		} catch (SQLException e) {
			// nothing to do
		}

	}

	// Methode um ein Country hinzuzufügen
	public static void insertCountry(Country c){

		String insertSQL = "INSERT INTO "+table+"(name, weight) "
				+ "VALUES (?,?)";

		long id = 0;

		try(
				Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getWeight());

			int affectedRows = pstmt.executeUpdate();

			if(affectedRows > 0){

				try(ResultSet rs = pstmt.getGeneratedKeys()) {

				}

			}

		} catch (SQLException e){
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (JDBC)
	public static void writeDataToDatabase(ArrayList<Country> cl){

		// Tabelle erstellen falls nicht existiert
		String createSQL = "CREATE TABLE IF NOT EXISTS "+table+" (name varchar(40), weight float(53));";
		minimal_query(createSQL);

		// Tabelle leeren
		String deleteSQL = "DELETE FROM "+table+";";
		minimal_query(deleteSQL);

		// Country Liste in Datenbanktabelle schreiben
		for(Country c : cl){
			insertCountry(c);
		}

	}

	// Methode um Liste an Countries auszulesen
	public static ArrayList<Country> readFromDatabase(){

		ArrayList<Country> cl = new ArrayList<Country>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM "+table+";";

		try(
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL)
		){
			while(rs.next()){

				cl.add(new Country(rs.getString("name"), rs.getDouble("weight")));

			}
		} catch (SQLException e){
			e.printStackTrace();
		}

		return cl;
	}

	public static void main(String[] args) throws IOException {

		/** TODO
		 * XLS einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Als Chart exportieren -> PDF
		 */

		// XLS einlesen -> Countries
		ArrayList<Country> countriesXLS = readXLSFile();

		// Daten in die Datenbank schrieben
		writeDataToDatabase(countriesXLS);

		// Daten von der Datenbank übergeben
		ArrayList<Country> countriesDB = readFromDatabase();

		for(Country c : countriesDB){
			System.out.println(c.getName() + " " +c.getWeight());
		}


	}
}
