package exercise.spring;

import java.util.List;

public interface DBInterface {

	// Tabelle leeren
	void clearTable();

	// Daten von Datenbank auslesen
	List<Object> readFromDatabase();

	// Daten in die Datenbank schreiben
	void writeDataToDatabase(List<Object> l);

}
