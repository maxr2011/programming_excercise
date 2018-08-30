package charts;

import charts.objects.Country;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
class Piechart extends ApplicationFrame {

	//Variablen

	private static java.util.List<Country> countries = new ArrayList<>();

	private static final int HEIGHT = 750;
	private static final int WIDTH = 537;

	//Datenbankanbindung
	private static final String url = "jdbc:postgresql://localhost/exercise";
	private static final String user = "mare";
	private static final String password = "toor";

	// Java Logger
	private static final Logger LOGGER = Logger.getLogger( Piechart.class.getName() );

	//Tabellenname
	private static final String table = "country_table";

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Pfad der PNG-Datei
	private static final String PNG_FILE = "piechart-example.png";

	//Pfad der PDF-Datei
	private static final String PDF_FILE = "piechart-example.pdf";

	//Konstruktor
	private Piechart(String title) {
		super(title);

		JPanel cp = createDemoPanel();

		//cp.setBackground(Color.WHITE);

		setContentPane(cp);
	}

	private static PieDataset createDataset(java.util.List<Country> cl) {
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
				true, false);

		// Pieplot
		PiePlot plot = (PiePlot) chart.getPlot();

		// Hintergrund weis (entfernt den grauen Standard Hintergrund)
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.white);

		// Entfernt den Shatten
		plot.setShadowPaint(Color.white);
		plot.setShadowXOffset(0);
		plot.setShadowYOffset(0);

		// Entfernt die Default Labels
		plot.setLabelGenerator(null);

		// Sektionen färben
		ArrayList<Color> colorlist = new ArrayList<>();
		colorlist.add(new Color(0x7ED096));
		colorlist.add(new Color(0x299D87));
		colorlist.add(new Color(0x1990D4));
		colorlist.add(new Color(0x5F5519));
		colorlist.add(new Color(0x165A3F));
		colorlist.add(new Color(0x867D19));
		colorlist.add(new Color(0xDFC70D));
		colorlist.add(new Color(0xEEA615));
		colorlist.add(new Color(0xF5C933));

		int j = 0;

		for (Country c : countries) {
			plot.setSectionPaint(c.getName(), colorlist.get(j++));
		}

		plot.setSimpleLabels(true);

		// Sektion hervorheben
		//plot.setExplodePercent("Deutschland", 0.3);

		// Sektionen Outline
		plot.setSectionOutlinePaint(o -> 0, Color.white);

		// Legende mit quadratischen Colorboxen
		plot.setLegendItemShape(new java.awt.Rectangle(15,15));

		// Legende
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.getLegend().setItemLabelPadding(new RectangleInsets(5.0, 2.0, 10.0, 900.0));
		chart.getLegend().setPadding(new RectangleInsets(10.0, 10.0, 0.0, 0.0));

		return chart;
	}

	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset(countries));

		// PNG export
		exportPNG(new File(PNG_FILE), chart, WIDTH, HEIGHT);

		// PDF export
		exportPDF(new File(PDF_FILE), chart, WIDTH, HEIGHT);

		return new ChartPanel(chart);
	}

	//Methode um xls Datei einzulesen
	private static ArrayList<Country> readXLSFile() {

		ArrayList<Country> countries = new ArrayList<>();

		try {

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

				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();

					if (cell.getCellTypeEnum() == CellType.STRING) {

						//funktioniert nur in diesem Fall
						String stringcell = "";
						stringcell += cell.getStringCellValue();
						c.setName(stringcell);

					} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {

						//funktioniert nur in diesem Fall
						c.setWeight(cell.getNumericCellValue());

					} else {
						//U Can Handel Boolean, Formula, Errors
						throw new InvalidParameterException("Hier sollte man nicht hingelangen!");
					}

				}

				//letzte Zeile (null-Zeile überspringen)
				if (!c.nameIsNull()) {
					countries.add(c);
				}


			}

		} catch (IOException e){
			// IOException abfangen
			e.printStackTrace();
		}

		return countries;

	}

	// Datenbankverbindung aufbauen JDBC
	private static Connection connect() {
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
	private static void minimal_query(String SQL) {

		try (Connection testconn = connect();
			 Statement stmt = testconn.createStatement();
			 ResultSet rs = stmt.executeQuery(SQL)) {

			// nothing to do
			LOGGER.log(Level.FINE, "Connection successful!");

		} catch (SQLException e) {

			// nothing to do
			LOGGER.log(Level.FINE, e.getMessage());

		}

	}

	// Methode um ein Country hinzuzufügen
	private static void insertCountry(Country c) {

		String insertSQL = "INSERT INTO " + table + " (name, weight) " + "VALUES (?,?);";

		try (Connection conn = connect();
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getWeight());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Methode um Liste an Countries in Datenbank zu schreiben (JDBC)
	private static void writeDataToDatabase(ArrayList<Country> cl) {

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
	private static ArrayList<Country> readFromDatabase() {

		ArrayList<Country> cl = new ArrayList<>();

		// Daten auslesen
		String selectSQL = "SELECT * FROM " + table + ";";

		try (Connection conn = connect();
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

	// Methode zum Speichern als PNG
	private static void exportPNG(File name, JFreeChart chart, int x, int y) {

		try {
			ChartUtilities.saveChartAsPNG(name, chart, x, y);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	/*
	// Methode zum Exportieren als GIF
	public static void exportGIF(File name, JFreeChart chart, int x, int y){

		try {

			exportPNG(name, chart, x, y);
			FileInputStream fin = new FileInputStream(name);
			BufferedImage image = ImageIO.read(fin);
			String sPath = name.getPath();
			ImageIO.write(image, "GIF", new File(sPath));

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException f){
			f.printStackTrace();
		}

	}
	*/

	// Methode zum Exportieren als PDF
	private static void exportPDF(File name, JFreeChart chart, int x, int y) {

		try {

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

		} catch (DocumentException | FileNotFoundException e){

			// Fehler mit dem Dokument
			e.printStackTrace();

		}

	}

	// 2 PDFs zusammenfügen
	private static void mergePDF(String source1, String source2, String destination){

		try {
			PDFMergerUtility ut = new PDFMergerUtility();
			ut.addSource(new File(source1));
			ut.addSource(source2);
			ut.setDestinationFileName(destination);
			ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	// Mainmethode
	public static void main(String[] args) {

		/* TODO
		 * XLS einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Als Chart exportieren -> PDF
		 */

		// XLS einlesen -> Countries
		ArrayList<Country> countriesXLS = readXLSFile();

		// Daten in die Datenbank schrieben
		writeDataToDatabase(countriesXLS);

		// Globale Variable
		// Daten von der Datenbank übergeben
		countries = readFromDatabase();

		// Daten sortieren
		countries = countries.stream()
							   .sorted(Comparator.comparing(Country::getWeight).reversed())
							   .collect(Collectors.toList());

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
		demo.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

		// 2 PDFs zusammenfügen
		mergePDF(PDF_FILE, "ringchart-example.pdf","piechart-ringchart-example.pdf");

	}
}
