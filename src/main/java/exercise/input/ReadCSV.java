package exercise.input;

import exercise.objects.Company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import streams.BasicCSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {


	//CSV Datei einlesen
	public static ArrayList<Company> readCsvFile(String fileName, String[] FILE_HEADER_MAPPING) {

		// Liste erstellen
		ArrayList<Company> companies = new ArrayList<>();

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		//header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING).withSkipHeaderRecord();

		try {

			//FileReader initialisieren
			fileReader = new FileReader(fileName);

			//CSVParser initialisieren
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			//Liste der csvRecords holen
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			for (CSVRecord c : csvRecords) {

				Company Com = new Company(c.get("Date"), c.get("Security"), Double.parseDouble(c.get("Weighting")));
				companies.add(Com);

			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			BasicCSVReader.closeReaderParser(fileReader, csvFileParser);
		}

		return companies;

	}


}
