package exercise.spring;

import exercise.input.ReadXLS;
import exercise.objects.Country;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ComponentConfig;

import java.util.ArrayList;
import java.util.List;

class Main {

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	// Mainmethode
	public static void main(String[] args) {

		// Liste einlesen aus der Datei
		// Variables
		ArrayList<Country> countries = ReadXLS.readXLSFile(EXAMPLE_XLS_FILE);

		for(Country c : countries){
			System.out.println(c.getName());
		}

		ApplicationContext context = new AnnotationConfigApplicationContext(ComponentConfig.class);
		CountryDB countryDB = (CountryDB) context.getBean("countryDB");
		List<Country> countriesDB = countryDB.readFromDatabase();

		for(Country c : countriesDB){
			System.out.println(c.getName());
		}

	}

}
