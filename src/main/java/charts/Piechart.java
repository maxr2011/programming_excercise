package charts;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Piechart {

	//Pfad zur xls Datei
	public static String EXAMPLE_XLS_FILE = "piechart-data.xls";

	//Methode um xls Datei einzulesen
	public static void readXLSFile() throws IOException {
		InputStream ExcelFileToRead = new FileInputStream(EXAMPLE_XLS_FILE);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + " ");
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					System.out.print(cell.getNumericCellValue() + " ");
				} else {
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}

	}

	public static void main(String[] args) throws IOException {

		/** TODO
		 // XLS einlesen
		 // Datenbank schreiben
		 // Datenbank lesen
		 // Als Chart exportieren -> PDF
		 */

		readXLSFile();

	}
}
