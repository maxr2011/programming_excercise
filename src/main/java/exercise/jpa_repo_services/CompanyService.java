package exercise.jpa_repo_services;

import exercise.objects.Company;
import jpa_repos.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Repository
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Transactional
	public void writeDataToDatabase(List<Company> companyList){
		for(Company c : companyList) companyRepository.saveAndFlush(c);
	}

	@Transactional
	public List<Company> readFromDatabase(){
		return companyRepository.findAll();
	}

}
