package spring_hibernate_config;

import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

class Main {

	public static void main(String[] args) {

		LocalEntityManagerFactoryBean emfb = new LocalEntityManagerFactoryBean();
		emfb.setPersistenceUnitName("exercisePU");


	}

}
