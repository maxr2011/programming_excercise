package spring_config;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_hibernate_config.JPAConfig;

import java.util.Arrays;

class Main {

	// Liest alle Beans aus
	public static void main(String[] args) {

		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
		ConfigurableApplicationContext cc = new AnnotationConfigApplicationContext(ComponentConfig.class);

		ConfigurableApplicationContext cd = new AnnotationConfigApplicationContext(JPAConfig.class);

		// Alle Beans die jemals erstellt werden
		Arrays.stream(ac.getBeanDefinitionNames()).forEach(System.out::println);
		Arrays.stream(cc.getBeanDefinitionNames()).forEach(System.out::println);

		Arrays.stream(cd.getBeanDefinitionNames()).forEach(System.out::println);

	}

}
