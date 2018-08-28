package streams;

import static java.util.stream.Collectors.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicCSVReader {

	//CSV file path
	private static final String SAMPLE_CSV_FILE_PATH = "exercise.csv";

	//CSV file header
	private static final String[] FILE_HEADER_MAPPING = {"name", "country", "nav", "volume"};

	public static void readCsvFile(String fileName) {

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

				Fund f = new Fund(c.get("name"), c.get("country"), Double.parseDouble(c.get("nav")),
						Double.parseDouble(c.get("volume")));
				funds.add(f);

			}

			ArrayList<Double> navs = new ArrayList<>();
			ArrayList<Double> volumes = new ArrayList<>();

			// Test mit Streams
			List<Fund> nfunds = funds.stream().limit(100).collect(toList());

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

			System.out.println("Durchschnitt nav foreach: " + avg(100, d));

			System.out.println();

			// Summe der Volumes
			double totalVolumes = funds.stream().collect(summingDouble(Fund::getVolume));
			System.out.println("Summe volume Streams: " + totalVolumes);

			// Summe mit foreach und eigener Methode
			double[] e = new double[100];
			int k = 0;

			for (Fund f : funds) {
				e[k] = f.getVolume();
				volumes.add(e[k++]);
			}

			System.out.println("Summe volume foreach: " + sum(e));

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

	}

	//Eigene Methoden um Ergebnisse zu vergleichen

	public static double avg(int n, double[] values) {

		double v = sum(values);
		v /= n;
		return v;

	}

	public static double sum(double[] values) {

		double v = 0.0;

		for (int i = 0; i < values.length; i++) {
			v += values[i];
		}

		return v;

	}

	public static void main(String[] args) throws IOException {

		readCsvFile(SAMPLE_CSV_FILE_PATH);

	}

}
