package exercise.input;

import exercise.objects.Country;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadXLS {

	//Methode um xls Datei einzulesen
	public static List<Object> readXLSFile(String xls) {

		List<Object> countries = new ArrayList<>();

		try (InputStream ExcelFileToRead = new FileInputStream(xls);
			 HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead)) {

			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			Iterator rows = sheet.rowIterator();
			int j = 0;

			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				// Header überspringen (erste Zeile exel)
				j++;
				if (j == 1) {
					continue;
				}

				//Country Objekt erstellen
				Country c = new Country();

				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();

					if (cell.getCellTypeEnum() == CellType.STRING) {

						//funktioniert nur in diesem Fall
						String stringcell = "";
						stringcell += cell.getStringCellValue();
						c.setName(stringcell);

					} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {

						//funktioniert nur in diesem Fall
						c.setWeight(cell.getNumericCellValue());

					} else {
						//U Can Handel Boolean, Formula, Errors
						throw new InvalidParameterException("Hier sollte man nicht hingelangen!");
					}

				}

				//letzte Zeile (null-Zeile überspringen)
				if (!c.nameIsNull()) {
					countries.add(c);
				}

			}

		} catch (IOException e) {
			// IOException abfangen
			e.printStackTrace();
		}

		return countries;

	}

}
