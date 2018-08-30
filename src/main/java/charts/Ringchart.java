package charts;

import charts.objects.Company;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import streams.Fund;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ringchart extends ApplicationFrame {

	//Variablen

	public static List<Company> companies;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

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

	//GIF file name
	private static final String GIF_FILE = "ringchart-example.gif";

	//PDF file name
	private static final String PDF_FILE = "ringchart-example.pdf";

	public Ringchart(String title) throws FileNotFoundException, DocumentException {
		super(title);
		JPanel panel = createDemoPanel();
		panel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		setContentPane(panel);
	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return a sample dataset.
	 */
	private static PieDataset createDataset(List<Company> cl) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Company c : cl) {
			dataset.setValue(c.getSecurity(), c.getWeighting());
		}

		return dataset;
	}

	/**
	 * Creates a chart.
	 *
	 * @param dataset the dataset.
	 * @return a chart.
	 */
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createRingChart("",  // chart title
				dataset,             // data
				true,               // include legend
				true, false);

		// Ringplot
		RingPlot plot = (RingPlot) chart.getPlot();

		// Schriftart ändern
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");

		plot.setSectionDepth(0.35);
		plot.setCircular(false);

		// Hintergrund (entfernt den grauen Standard Hintergrund)
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

		// Farbliste
		colorlist.add(new Color(0x7CCB92));
		colorlist.add(new Color(0x299B87));
		colorlist.add(new Color(0x1990D2));
		colorlist.add(new Color(0x395B85));
		colorlist.add(new Color(0x5F5519));
		colorlist.add(new Color(0x165A3F));

		int k = 0;

		for (Company c : companies) {
			plot.setSectionPaint(c.getSecurity(), colorlist.get(k++));
		}

		// Sektion Outline unsichtbar machen
		//plot.setSectionOutlinesVisible(false);

		// Sektion Outline färben
		plot.setSectionOutlinePaint(new Color(0x808080));

		// Seperator Linie unsichtbar machen
		plot.setSeparatorsVisible(false);

		plot.setLabelGap(0.02);

		// Tiefe des Graphens
		plot.setSectionDepth(0.6);

		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 *
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() throws FileNotFoundException, DocumentException {
		JFreeChart chart = createChart(createDataset(companies));

		// Als GIF exportieren
		exportGIF(new File(GIF_FILE), chart, WIDTH, HEIGHT);

		// Als PDF exportieren
		exportPDF(new File(PDF_FILE), chart, WIDTH, HEIGHT);

		return new ChartPanel(chart);
	}

	//CSV Datei einlesen
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

	// Methode zum Exportieren als PNG
	public static void exportPNG(File name, JFreeChart chart, int x, int y) {

		try {
			ChartUtilities.saveChartAsPNG(name, chart, x, y);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

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

	// Methode zum Exportieren als PDF
	public static void exportPDF(File name, JFreeChart chart, int x, int y)
			throws FileNotFoundException, DocumentException {

		com.itextpdf.text.Rectangle pagesize = new com.itextpdf.text.Rectangle(x, y);
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

		// Globale Variable
		companies =  companiesDB.stream().sorted(Comparator.comparing(Company::getWeighting).reversed()).collect(
				Collectors.toList());

		double hundred_check = 0.0;

		for (Company c : companies) {
			System.out.println(c.getSecurity());
			hundred_check += c.getWeighting();
		}

		System.out.println(hundred_check);

		// check if everything adds up to 100%
		if (hundred_check == 1.00) {
			System.out.println("sum = 100% : " + true);
		}

		// Ringchart erstellen
		Ringchart demo = new Ringchart("Companies");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
