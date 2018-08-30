package streams;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class BasicCSVReader {

	//Liest CSV Datei ein
	public static List<Fund> readCsvFile(String fileName, String[] headerMapping) {

		List<Fund> funds = null;

		FileReader fileReader = null;
		CSVParser csvFileParser = null;

		//header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headerMapping).withSkipHeaderRecord();

		try {

			//Liste erstellen
			funds = new ArrayList<>();

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

			return funds;

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			closeReaderParser(fileReader, csvFileParser);
		}

		return funds;

	}

	// FileReader und CSVParser schließen
	public static void closeReaderParser(FileReader fileReader, CSVParser csvFileParser) {
		try {
			fileReader.close();
			csvFileParser.close();
		} catch (IOException | NullPointerException e) {
			System.out.println("Error while closing fileReader/csvFileParser !!!");
			e.printStackTrace();
		}
	}

}
