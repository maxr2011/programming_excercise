package spring;

import exercise.spring.CountryDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ComponentConfig {

	@Bean
	public CountryDB countryDB() {
		return new CountryDB();
	}

	@Bean
	public DataSource dataSourceJDBC() {
		return new DataSourceConfiguration().dataSourceJDBC();
	}

}
