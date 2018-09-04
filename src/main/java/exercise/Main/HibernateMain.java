package exercise.Main;

import exercise.chart.Piechart;
import exercise.chart.Ringchart;
import exercise.hibernate.CompanyDB;
import exercise.hibernate.CountryDB;
import exercise.input.ReadCSV;
import exercise.input.ReadXLS;
import exercise.objects.Company;
import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jfree.ui.RefineryUtilities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class HibernateMain {

	//Variablen
	private static final int HEIGHT = 750;
	private static final int WIDTH = 537;

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//CSV file path
	private static final String SAMPLE_CSV_FILE_PATH = "Ring Chart Data.csv";

	//CSV file header
	private static final String[] FILE_HEADER_MAPPING = {"Date", "Security", "Weighting"};

	//Pfad der PDF-Datei
	private static final String PDF_FILE = "piechart-example.pdf";

	public static void main(String[] args) {

		SessionFactory sessionFactory = new MetadataSources(
				new StandardServiceRegistryBuilder()
						.configure("hibernate.cfg.xml").build()
		).buildMetadata().buildSessionFactory();

		/* RINGCHART */
		System.out.println("+++ RINGCHART +++");
		System.out.println();

		/* TODO
		 * CSV einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Chart erstellen
		 * Als GIF, PDF exportieren
		 */

		// Daten aus CSV Datei auslesen
		List<Company> companies = ReadCSV.readCsvFile(SAMPLE_CSV_FILE_PATH, FILE_HEADER_MAPPING);

		CompanyDB companyDB = new CompanyDB();

		// Daten in die Datenbank schreiben
		companyDB.writeDataToDatabase(companies, sessionFactory.getCurrentSession());

		// Daten aus der Datenbank auslesen
		List<Company> companiesDB = companyDB.readFromDatabase(sessionFactory.getCurrentSession()).stream()
											 .sorted(Comparator.comparing(Company::getWeighting).reversed())
											 .collect(Collectors.toList());

		// Ringchart erstellen
		Ringchart demoa = new Ringchart("Companies", companiesDB);
		demoa.pack();
		RefineryUtilities.centerFrameOnScreen(demoa);
		demoa.setVisible(true);

		/* PIECHART */
		System.out.println("+++ PIECHART +++");
		System.out.println();

		/* TODO
		 * XLS einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Chart erstellen
		 * Als PNG, PDF exportieren
		 */

		// Liste einlesen aus der Datei
		// Variables
		List<Country> countries = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		CountryDB countryDB = new CountryDB();

		// Daten in die Datenbank schreiben
		countryDB.writeDataToDatabase(countries, sessionFactory.getCurrentSession());

		// Daten aus Datenbank auslesen
		List<Country> countriesDB = countryDB.readFromDatabase(sessionFactory.getCurrentSession());

		// Piechart erstellen
		Piechart demob = new Piechart("Countries", countriesDB);
		demob.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demob);
		demob.setVisible(true);

		// 2 PDFs zusammenfügen
		GenerateOutputFiles.mergePDF(PDF_FILE, "ringchart-example.pdf", "piechart-ringchart-example.pdf");

		// Session Factory schließen
		sessionFactory.close();

	}

}
