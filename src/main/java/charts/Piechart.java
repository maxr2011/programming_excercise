package charts;

import charts.objects.Country;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Piechart {

	//Pfad zur xls Datei
	public static String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Methode um xls Datei einzulesen
	public static ArrayList<Country> readXLSFile() throws IOException {

		ArrayList<Country> countries = new ArrayList<Country>();

		InputStream ExcelFileToRead = new FileInputStream(EXAMPLE_XLS_FILE);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			//Country Objekt erstellen
			Country c = new Country();

			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + " ");

					//funktioniert nur in diesem Fall
					c.setName(cell.getStringCellValue());
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					System.out.print(cell.getNumericCellValue() + " ");

					//funktioniert nur in diesem Fall
					c.setWeight(cell.getNumericCellValue());

				} else {
					//U Can Handel Boolean, Formula, Errors
				}
			}

			countries.add(c);

			System.out.println();
		}

		return countries;

	}

	public static void main(String[] args) throws IOException {

		/** TODO
		 * XLS einlesen
		 * Datenbank schreiben
		 * Datenbank lesen
		 * Als Chart exportieren -> PDF
		 */

		ArrayList<Country> countries = readXLSFile();

		for(Country c : countries){
			System.out.println(c.getName() + " - " + c.getWeight());
		}

		/** TODO
		 * 1. und letzte Zeile fixen
		 */

	}
}
