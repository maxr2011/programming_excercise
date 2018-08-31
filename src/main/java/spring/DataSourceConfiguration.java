package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

	// Pool Configuration
	/*
	@Profile("Pool")
	@Bean
	public BasicDataSource dataSourcePool() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost/exercise");
		ds.setUsername("mare");
		ds.setPassword("toor");
		ds.setMaxActive(10);
		return ds;
	}

	@Profile("JDBC")
	*/

	@Bean
	public DataSource dataSourceJDBC() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost/exercise");
		ds.setUsername("mare");
		ds.setPassword("toor");
		return ds;
	}

}
