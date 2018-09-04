package jpa_repos;

import exercise.objects.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@SuppressWarnings("unused")
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {
	// --Commented out by Inspection (04.09.18 17:22):Company findByDate(String date);
	// --Commented out by Inspection (04.09.18 17:22):Company findBySecurity(String security);
}
