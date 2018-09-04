package spring_hibernate_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "exercise.spring_hibernate")
public class JPAConfig {

	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalEntityManagerFactoryBean emfb = new LocalEntityManagerFactoryBean();
		emfb.setPersistenceUnitName("exercisePU");
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager(LocalEntityManagerFactoryBean entityManagerFactory) {

		return new JpaTransactionManager(entityManagerFactory.getObject());

	}

	/* Macht nur in Serverumgebung Sinn
	@Bean
	public JndiObjectFactoryBean entityManagerFactory() {
		JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
		jndiObjectFB.setJndiName("jdbc/exercise");
		return jndiObjectFB;
	}
	*/

}
