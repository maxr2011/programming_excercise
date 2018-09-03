package exercise.Main;

import database.JDBC;
import exercise.chart.Piechart;
import exercise.chart.Ringchart;
import exercise.input.ReadCSV;
import exercise.input.ReadXLS;
import exercise.jdbc.CompanyDB;
import exercise.jdbc.CountryDB;
import exercise.objects.Company;
import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import org.jfree.ui.RefineryUtilities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Main {

	private static final int HEIGHT = 750;
	private static final int WIDTH = 537;

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Pfad der PDF-Datei
	private static final String PDF_FILE = "piechart-example.pdf";

	//CSV file path
	private static final String SAMPLE_CSV_FILE_PATH = "Ring Chart Data.csv";

	//CSV file header
	private static final String[] FILE_HEADER_MAPPING = {"Date", "Security", "Weighting"};


	//Mainmethode
	public static void main(String[] args) {


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

		// Tabelle leeren
		JDBC.clearTable("company_table");

		// CSV einlesen -> Companies
		List<Company> companiesCSV = ReadCSV.readCsvFile(SAMPLE_CSV_FILE_PATH, FILE_HEADER_MAPPING);

		// Daten in die Datenbank schreiben
		CompanyDB.writeDataToDatabase(companiesCSV);

		// Daten von der Datenbank übergeben
		List<Company> companiesDB = CompanyDB.readFromDatabase();

		// Liste sortieren
		List<Company> companies = companiesDB.stream()
											 .sorted(Comparator.comparing(Company::getWeighting).reversed())
											 .collect(Collectors.toList());

		double hundred_check = 0.0;

		for (Company c : companies) {
			System.out.println(c.getSecurity());
			hundred_check += c.getWeighting();
		}

		System.out.println();

		System.out.println(hundred_check);

		// check if everything adds up to 100%
		if (hundred_check == 1.00) {
			System.out.println("sum = 100% : " + true);
		}

		// Ringchart erstellen
		Ringchart demoa = new Ringchart("Companies", companies);
		demoa.pack();
		RefineryUtilities.centerFrameOnScreen(demoa);
		demoa.setVisible(true);

		System.out.println();
		System.out.println();

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

		// Tabelle leeren
		JDBC.clearTable("country_table");

		// XLS einlesen -> Countries
		List<Country> countriesXLS = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		// Daten in die Datenbank schrieben
		CountryDB.writeDataToDatabase(countriesXLS);

		// Globale Variable
		// Daten von der Datenbank übergeben
		//Variablen
		List<Country> countries = CountryDB.readFromDatabase();

//		// Daten sortieren
//		countries = countries.stream()
//							 .sorted(Comparator.comparing(Country::getWeight).reversed())
//							 .collect(Collectors.toList());

		hundred_check = 0.0;

		// Ausgabe
		for (Country c : countries) {
			System.out.println(c.getName() + " " + c.getWeight());
			hundred_check += c.getWeight();
		}

		System.out.println();

		System.out.println(hundred_check);

		// Check if everything adds up to 100%
		if (hundred_check > 99 && hundred_check < 101) {
			System.out.println("sum = 100% : " + true);
		}

		// Piechart erstellen
		Piechart demob = new Piechart("Countries", countries);
		demob.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demob);
		demob.setVisible(true);

		System.out.println();

		// 2 PDFs zusammenfügen
		GenerateOutputFiles.mergePDF(PDF_FILE, "ringchart-example.pdf", "piechart-ringchart-example.pdf");

	}


}
