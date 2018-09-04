package exercise.spring_hibernate;

import exercise.objects.Company;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_hibernate_config.JPAConfig;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ap = new AnnotationConfigApplicationContext(JPAConfig.class);

		CompanyDB c = (CompanyDB) ap.getBean("companyDB");
		c.addCompany(new Company("2018-09-03", "anevis Solutions" , 0.5));


		((AnnotationConfigApplicationContext) ap).close();

	}

}
