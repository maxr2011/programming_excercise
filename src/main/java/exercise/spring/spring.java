package exercise.spring;

import exercise.input.ReadXLS;
import exercise.objects.Country;

import java.util.ArrayList;

class spring {

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

	}

}
