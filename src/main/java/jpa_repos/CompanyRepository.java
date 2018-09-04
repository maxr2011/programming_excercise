package jpa_repos;

import exercise.objects.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByDate(String date);
	Company findBySecurity(String security);
}
