package exercise.Main;

import exercise.chart.Piechart;
import exercise.chart.Ringchart;
import exercise.input.ReadCSV;
import exercise.input.ReadXLS;
import exercise.objects.Company;
import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import exercise.spring.CompanyDB;
import exercise.spring.CountryDB;
import exercise.spring.DBInterface;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ComponentConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SpringMain {

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

	// Mainmethode
	public static void main(String[] args) {

		// Application context
		ApplicationContext context = new AnnotationConfigApplicationContext(ComponentConfig.class);

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

		List<Object> companies = ReadCSV.readCsvFile(SAMPLE_CSV_FILE_PATH, FILE_HEADER_MAPPING);

		DBInterface companyDB = (CompanyDB) context.getBean("companyDB");

		// Tabelle leeren
		companyDB.clearTable();

		// Daten in Datenbank schreiben
		companyDB.writeDataToDatabase(companies);

		// Daten aus der Datenbank auslesen
		List<Object> companiesDB = companyDB.readFromDatabase();

		// Liste casten
		List<Company> companyCastList = new ArrayList<>();

		for(Object o : companiesDB) {
			companyCastList.add((Company) o);
		}

		companyCastList = companyCastList
											 .stream()
											 .sorted(Comparator.comparing(Company::getWeighting).reversed())
											 .collect(Collectors.toList());

		// Ringchart erstellen
		Ringchart demoa = new Ringchart("Companies", companyCastList);
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
		List<Object> countries = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		DBInterface countryDB = (CountryDB) context.getBean("countryDB");

		// Tabelle leeren
		countryDB.clearTable();

		// Daten in die Tabelle schreiben
		countryDB.writeDataToDatabase(countries);

		// Daten aus Tabelle auslesen und in Liste speichern
		List<Object> countriesDB = countryDB.readFromDatabase();
								// Würde die Liste sortieren (brauchen wir hier jedoch nicht)
								/*
								.stream()
							 	.sorted(Comparator.comparing(Country::getWeight).reversed())
							 	.collect(Collectors.toList());
								*/


		// Liste casten
		List<Country> countryCastList = new ArrayList<>();

		for(Object o : countriesDB) {
			countryCastList.add((Country) o);
		}

		// Piechart erstellen
		Piechart demob = new Piechart("Countries", countryCastList);
		demob.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demob);
		demob.setVisible(true);

		// 2 PDFs zusammenfügen
		GenerateOutputFiles.mergePDF(PDF_FILE, "ringchart-example.pdf", "piechart-ringchart-example.pdf");

	}

}
