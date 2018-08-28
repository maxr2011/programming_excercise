package charts;

import charts.objects.Company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import streams.Fund;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ringchart {

    //CSV file path
    private static final String SAMPLE_CSV_FILE_PATH = "Ring Chart Data.csv";

    //CSV file header
    private static final String[] FILE_HEADER_MAPPING = {"Date", "Security", "Weighting"};

    public static ArrayList<Company> readCsvFile(String fileName) {

        ArrayList<Company> companies = new ArrayList<Company>();

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

                Company Com = new Company(c.get("Date"), c.get("Security"), Double.parseDouble(c.get("Weighting")));
                companies.add(Com);

            }


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

        return companies;

    }

    public static void main(String[] args) {

        /** TODO
        * CSV einlesen
        * Datenbank schreiben
        * Datenbank lesen
        * Als Chart exportieren -> PDF
        */

        ArrayList<Company> companies = readCsvFile(SAMPLE_CSV_FILE_PATH);

        for(Company c : companies){
            System.out.println(c.getSecurity());
        }

    }

}
