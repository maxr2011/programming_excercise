package exercise.spring_hibernate;

import exercise.objects.Company;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_hibernate_config.JPAConfig;

class Main {

	// Testdaten
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(JPAConfig.class);

		CompanyDB c = (CompanyDB) ap.getBean("companyDB");
		c.addCompany(new Company("2018-09-03", "anevis Solutions" , 0.5));


		ap.close();

	}

}
