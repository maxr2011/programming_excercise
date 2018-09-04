package exercise.hibernate;

import exercise.objects.Country;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
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

		CriteriaQuery<Country> cq = s.getCriteriaBuilder().createQuery(Country.class);
		cq.from(Country.class);
		List<Country> countries = s.createQuery(cq).getResultList();

		transaction.commit();
		s.close();
		return countries;

	}

}
