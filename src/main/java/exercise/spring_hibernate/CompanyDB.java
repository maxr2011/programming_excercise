package exercise.spring_hibernate;

import exercise.objects.Company;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CompanyDB {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addCompany(Company c) {
		em.persist(c);
	}

	@Transactional
	public void writeDataToDatabase(List<Company> cl) {
		for(Company c : cl) {
			addCompany(c);
		}
	}

	@Transactional
	public List<Company> readFromDatabase() {
		return em.createQuery("SELECT c FROM Company c", Company.class).getResultList();
	}

}
