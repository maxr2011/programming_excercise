package spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

	// Liest alle Beans aus
	public static void main(String[] args) {

		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
		ConfigurableApplicationContext cc = new AnnotationConfigApplicationContext(ComponentConfig.class);

		// Alle Beans die jemals erstellt werden
		Arrays.stream(ac.getBeanDefinitionNames()).forEach(System.out::println);
		Arrays.stream(cc.getBeanDefinitionNames()).forEach(System.out::println);

	}

}
