package streams;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

	//Variablen
	private static List<Fund> funds;

	//CSV file path
	private static final String SAMPLE_CSV_FILE_PATH = "exercise.csv";

	//CSV file header
	private static final String[] FILE_HEADER_MAPPING = {"name", "country", "nav", "volume"};

	//Java Logger
	private static final Logger LOGGER = Logger.getLogger( BasicCSVReader.class.getName() );

	// Mainmethode
	public static void main(String[] args) {

		funds = BasicCSVReader.readCsvFile(SAMPLE_CSV_FILE_PATH, FILE_HEADER_MAPPING);

		ArrayList<Double> navs = new ArrayList<>();
		ArrayList<Double> volumes = new ArrayList<>();

		// Test mit Streams
		//List<Fund> nfunds = funds.stream().limit(100).collect(toList());

		// Liste nach Namen sortieren
		List<String> al = funds.stream()
							   .sorted(Comparator.comparing(Fund::getName))
							   .map(Fund::getName)
							   .collect(Collectors.toList());

		for(String a : al){
			LOGGER.log(Level.FINE, "Fund name: "+a);
		}

		// Durchschnitt mit Streams
		double avgFunds = funds.stream().collect(averagingDouble(Fund::getNav));
		System.out.println("Durchschnitt nav Streams: " + avgFunds);

		// Durchschnitt mit foreach und eigener Methode
		double[] d = new double[100];
		int j = 0;

		for (Fund f : funds) {
			d[j] = f.getNav();
			navs.add(d[j++]);
		}

		System.out.println("Durchschnitt nav foreach: " + Math.avg(100, d));

		System.out.println();

		// Summe der Volumes
		double totalVolumes = funds.stream().mapToDouble(Fund::getVolume).sum();
		System.out.println("Summe volume Streams: " + totalVolumes);

		// Summe mit foreach und eigener Methode
		double[] e = new double[100];
		int k = 0;

		for (Fund f : funds) {
			e[k] = f.getVolume();
			volumes.add(e[k++]);
		}

		System.out.println("Summe volume foreach: " + Math.sum(e));

		System.out.println();

		// Maximum Streams
		double maxNav = navs.stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getMax();

		double maxVolumes = volumes.stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getMax();

		System.out.println("Maximum Nav Streams: " + maxNav);
		System.out.println("Maximum Volumes Streams: " + maxVolumes);

		// Minimum Streams

		double minNav = navs.stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getMin();

		double minVolumes = volumes.stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getMin();

		System.out.println("Minimum Nav Streams: " + minNav);
		System.out.println("Minimum Volumes Streams: " + minVolumes);

		// map with country -> average nav
		Map<String, Double> countryMap1 =
				funds.stream().collect(groupingBy(Fund::getCountry, averagingDouble(Fund::getNav)));

		System.out.println();

		for (Map.Entry g : countryMap1.entrySet()) {
			System.out.println(g.getKey() + " = " + g.getValue());
		}

		System.out.println();

		// map with country -> volume sum
		Map<String, Double> countryMap2 =
				funds.stream().collect(groupingBy(Fund::getCountry, Collectors.summingDouble(Fund::getVolume)));

		for (Map.Entry g : countryMap2.entrySet()) {
			System.out.println(g.getKey() + " = " + g.getValue());
		}

		//Map<String, Double> countryMap3 = funds.stream()
		//									   .collect(groupingBy(Fund::getCountry, Collectors.maxBy(Fund::getNav)
		//																					   .summarizingDouble(
		//																							   Double::doubleValue)));

		// Ansatz: Vorher Comparator Objekt erstellen und dann in die Methode maxBy mit übergeben

	}

}
