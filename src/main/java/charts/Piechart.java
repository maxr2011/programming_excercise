package charts;

import charts.objects.Country;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Piechart extends ApplicationFrame {

	//Variablen

	public static ArrayList<Country> countries;

	//Datenbankanbindung
	private static final String url = "jdbc:postgresql://localhost/exercise";
	private static final String user = "mare";
	private static final String password = "toor";

	//Tabellenname
	private static final String table = "country_table";

	//Pfad zur xls Datei
	public static String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Pfad der PNG-Datei
	public static String PNG_FILE = "piechart-example.png";

	//Pfad der PDF-Datei
	public static String PDF_FILE = "piechart-example.pdf";

	//Konstruktor
	public Piechart(String title) throws FileNotFoundException, DocumentException {
		super(title);

		JPanel cp = createDemoPanel(countries);

		//cp.setBackground(Color.WHITE);

		setContentPane(cp);
	}

	private static PieDataset createDataset(ArrayList<Country> cl) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Country c : cl) {
			dataset.setValue(c.getName(), c.getWeight());
		}

		return dataset;
	}

	private static JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("ANTEIL AM FONDSVERMÖGEN",   // chart title
				dataset,          // data
				true,             // include legend
				false, false);

		Plot plot = chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.white);

		return chart;
	}

	public static JPanel createDemoPanel(ArrayList<Country> cl) throws FileNotFoundException, DocumentException {
		JFreeChart chart = createChart(createDataset(countries));

		// PDF export
		export(new File(PDF_FILE), chart, 900, 500);

		return new ChartPanel(chart);
	}

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
			if (j == 1) {
				continue;
			}

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

			if (!isNull) {
				//letzte Zeile (null-Zeile überspringen)
				if (!c.nameIsNull()) {
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

		try (Connection testconn = connect();
			 Statement stmt = testconn.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL)) {
			// nothing to do
		} catch (SQLException e) {
			// nothing to do
		}

	}

	// Methode um ein Country hinzuzufügen
	public static void insertCountry(Country c) {

		String insertSQL = "INSERT INTO " + table + "(name, weight) " + "VALUES (?,?)";

		long id = 0;

		try (Connection conn = connect();
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getWeight());

			int affectedRows = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (JDBC)
	public static void writeDataToDatabase(ArrayList<Country> cl) {

		// Tabelle erstellen falls nicht existiert
		String createSQL = "CREATE TABLE IF NOT EXISTS " + table + " (name varchar(40), weight float(53));";
		minimal_query(createSQL);

		// Tabelle leeren
		String deleteSQL = "DELETE FROM " + table + ";";
		minimal_query(deleteSQL);

		// Country Liste in Datenbanktabelle schreiben
		for (Country c : cl) {
			insertCountry(c);
		}

	}

	// Methode um Liste an Countries aus der Datenbank auszulesen
	public static ArrayList<Country> readFromDatabase() {

		ArrayList<Country> cl = new ArrayList<Country>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM " + table + ";";

		try (Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(selectSQL)) {
			while (rs.next()) {

				cl.add(new Country(rs.getString("name"), rs.getDouble("weight")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cl;
	}

	// Methode zum Exportieren als PDF
	public static void export(File name, JFreeChart chart, int x, int y)
			throws FileNotFoundException, DocumentException {
		Rectangle pagesize = new Rectangle(x, y);
		Document document = new Document(pagesize, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name));
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfTemplate tp = cb.createTemplate(x, y);
		Graphics2D g2 = tp.createGraphics(x, y, new DefaultFontMapper());

		java.awt.Rectangle recta = new java.awt.Rectangle(x, y);
		chart.draw(g2, recta);
		g2.dispose();
		cb.addTemplate(tp, 0, 0);
		document.close();
	}

	// Mainmethode
	public static void main(String[] args) throws IOException, FileNotFoundException, DocumentException {

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

		// Globale Variable
		countries = countriesDB;

		double hundred_check = 0.0;

		// Ausgabe
		for (Country c : countries) {
			System.out.println(c.getName() + " " + c.getWeight());
			hundred_check += c.getWeight();
		}

		System.out.println(hundred_check);

		// Check if everything adds up to 100%
		if (hundred_check > 99 && hundred_check < 101) {
			System.out.println("sum = 100% : " + true);
		}

		// Piechart erstellen
		Piechart demo = new Piechart("Countries");
		demo.setSize(560, 367);
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
}
