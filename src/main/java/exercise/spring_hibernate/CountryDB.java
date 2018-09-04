package exercise.spring_hibernate;

import exercise.objects.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CountryDB {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addCountry(Country c) {
			em.persist(c);
		}

	@Transactional
	public void writeDataToDatabase(List<Country> cl) {
		for(Country c : cl) {
			addCountry(c);
		}
	}

	@Transactional
	public List<Country> readFromDatabase() {
		String sql = "SELECT c FROM Country c";
		return em.createQuery(sql).getResultList();
	}

	@Transactional
	public void clearTable() {
			em.createQuery("DELETE FROM Country c");
		}

}
