package exercise.Main;

import exercise.chart.Piechart;
import exercise.chart.Ringchart;
import exercise.input.ReadCSV;
import exercise.input.ReadXLS;
import exercise.jms.Sender;
import exercise.jpa_repo_services.CompanyService;
import exercise.jpa_repo_services.CountryService;
import exercise.objects.Company;
import exercise.objects.Country;
import exercise.output.GenerateOutputFiles;
import jms_config.JMSConfig;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_hibernate_config.JPARepoConfig;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SpringHibernateJPARepoMain {

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

		// Application Context
		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JPARepoConfig.class);

		CompanyService cs = (CompanyService) ap.getBean("companyService");

		/* RINGCHART */
		System.out.println("+++ RINGCHART +++");
		System.out.println();

		/* DONE
		 * CSV einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Chart erstellen
		 * Als GIF, PDF exportieren
		 */

		// CSV auslesen
		List<Company> companies = ReadCSV.readCsvFile(SAMPLE_CSV_FILE_PATH, FILE_HEADER_MAPPING);

		// Daten in die Datenbank schreiben
		cs.writeDataToDatabase(companies);

		// JMS Context
		AnnotationConfigApplicationContext jms_ap = new AnnotationConfigApplicationContext(JMSConfig.class);

		// JMS Nachricht verschicken
		Sender sender1 = (Sender) jms_ap.getBean("sender");

		sender1.sendMessage("Data written to company_table");

		// Daten aus der Datenbank lesen
		List<Company> companiesDB = cs.readFromDatabase().stream()
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

		/* DONE
		 * XLS einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Chart erstellen
		 * Als PNG, PDF exportieren
		 */

		// Liste einlesen aus der Datei
		// Variables
		List<Country> countries = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		CountryService countryService = (CountryService) ap.getBean("countryService");

		// Daten in Datenbank schreiben
		countryService.writeDataToDatabase(countries);

		// JMS Nachricht verschicken
		Sender sender2 = (Sender) jms_ap.getBean("sender");

		sender2.sendMessage("Data written to country_table");

		// Daten aus Tabelle auslesen und in Liste speichern (mit Cast zu List<Country)
		List<Country> countriesDB = countryService.readFromDatabase();
		// Würde die Liste sortieren (brauchen wir hier jedoch nicht)
								/*
								.stream()
							 	.sorted(Comparator.comparing(Country::getWeight).reversed())
							 	.collect(Collectors.toList());
								*/

		// Piechart erstellen
		Piechart demob = new Piechart("Countries", countriesDB);
		demob.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demob);
		demob.setVisible(true);

		// 2 PDFs zusammenfügen
		GenerateOutputFiles.mergePDF(PDF_FILE, "ringchart-example.pdf", "piechart-ringchart-example.pdf");

		// Applicaton Context schließen
		ap.close();

	}

}
