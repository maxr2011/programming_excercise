package springconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"exercise.spring", "database"})
public class ComponentConfig {

	@Bean
	public JdbcOperations jdbcOperations(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSource dataSourceJDBC() {
		return new DataSourceConfiguration().dataSourceJDBC();
	}

}
