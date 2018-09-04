package exercise.jpa_repo_services;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_hibernate_config.JPARepoConfig;

public class Main {

	public static void main(String[] args) {

		// Application Context
		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JPARepoConfig.class);

		CompanyService cs = (CompanyService) ap.getBean("companyService");

	}

}
