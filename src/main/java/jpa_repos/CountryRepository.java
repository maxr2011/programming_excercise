package jpa_repos;

import exercise.objects.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CountryRepository extends JpaRepository<Country, Long> {
	// --Commented out by Inspection (04.09.18 17:22):Country findByName(String name);
}
