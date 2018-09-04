package exercise.hibernate;

import exercise.objects.Country;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CountryDB {

	// Schreibt Daten in die Datenbank
	public void writeDataToDatabase(List<Country> cl, Session s){

		final Transaction transaction = s.beginTransaction();
		for(Country c : cl) {
			s.save(c);
		}
		transaction.commit();
		s.close();

	}

	// Liest Daten aus der Datenbank
	public List<Country> readFromDatabase(Session s){

		final Transaction transaction = s.beginTransaction();
		final Criteria criteria = s.createCriteria(Country.class);
		List<Country> cl = criteria.list();
		transaction.commit();
		s.close();
		return cl;

	}

}
