package exercise.jpa_repo_services;

import exercise.objects.Country;
import jpa_repos.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Transactional
	public void writeDataToDatabase(List<Country> companyList){
		for(Country c : companyList) countryRepository.saveAndFlush(c);
	}

	@Transactional
	public List<Country> readFromDatabase(){
		return countryRepository.findAll();
	}

}
