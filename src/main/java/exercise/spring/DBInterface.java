package exercise.spring;

import java.util.List;

public interface DBInterface<E> {

	// Tabelle leeren
	void clearTable();

	// Daten von Datenbank auslesen
	List<E> readFromDatabase();

	// Daten in die Datenbank schreiben
	void writeDataToDatabase(List<E> l);

}
