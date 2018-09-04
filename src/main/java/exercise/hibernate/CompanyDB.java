package exercise.hibernate;

import exercise.objects.Company;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDB {

	// Schreibt Daten in die Datenbank
	public void writeDataToDatabase(List<Company> cl, Session s){

		final Transaction transaction = s.beginTransaction();
		for(Company c : cl) {
			s.save(c);
		}
		transaction.commit();
		s.close();

	}

	// Liest Daten aus der Datenbank
	public List<Company> readFromDatabase(Session s){

		final Transaction transaction = s.beginTransaction();
		final Criteria criteria = s.createCriteria(Company.class);
		List<Company> cl = criteria.list();
		transaction.commit();
		s.close();
		return cl;

	}

}
