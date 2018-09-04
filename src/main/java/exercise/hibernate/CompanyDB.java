package exercise.hibernate;

import exercise.objects.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
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

		CriteriaQuery<Company> cq = s.getCriteriaBuilder().createQuery(Company.class);
		cq.from(Company.class);
		List<Company> companies = s.createQuery(cq).getResultList();

		transaction.commit();
		s.close();
		return companies;

	}

}
