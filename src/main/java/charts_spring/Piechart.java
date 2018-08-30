package charts_spring;

import charts.objects.Country;

import java.util.ArrayList;

class Piechart {

	//Pfad zur xls Datei
	private static final String EXAMPLE_XLS_FILE = "piechart-data.xls";

	// Mainmethode
	public static void main(String[] args) {

		// Liste einlesen aus der Datei
		// Variables
		ArrayList<Country> countries = charts.Piechart.readXLSFile(EXAMPLE_XLS_FILE);

		for(Country c : countries){
			System.out.println(c.getName());
		}

	}

}
