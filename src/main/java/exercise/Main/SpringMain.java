package exercise.main;

import exercise.chart.Piechart;
import exercise.input.ReadXLS;
import exercise.objects.Country;
import exercise.spring.CountryDB;
import org.jfree.ui.RefineryUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ComponentConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Main {

	//Variablen
	private static final int HEIGHT = 750;
	private static final int WIDTH = 537;

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	// Mainmethode
	public static void main(String[] args) {

		// Liste einlesen aus der Datei
		// Variables
		ArrayList<Country> countries = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		ApplicationContext context = new AnnotationConfigApplicationContext(ComponentConfig.class);
		CountryDB countryDB = (CountryDB) context.getBean("countryDB");

		// Tabelle leeren
		countryDB.clearTable();

		// Daten in die Tabelle schreiben
		countryDB.writeDataToDatabase(countries);

		// Daten aus Tabelle auslesen und in Liste speichern
		List<Country> countriesDB = countryDB.readFromDatabase();

		// Daten ausgeben
		for(Country c : countriesDB){
			System.out.println(c.getName());
		}

		// Daten sortieren
		countriesDB = countriesDB.stream()
							 .sorted(Comparator.comparing(Country::getWeight).reversed())
							 .collect(Collectors.toList());

		// Piechart erstellen
		Piechart demob = new Piechart("Countries", countries);
		demob.setSize(WIDTH, HEIGHT);
		RefineryUtilities.centerFrameOnScreen(demob);
		demob.setVisible(true);


	}

}
