package exercise.spring;

import exercise.objects.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDB {

	//Variablen

	//Tabellenname
	private static final String table = "country_table";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

	//Liest Countries von der Datenbank aus
	public List<Country> readFromDatabase() {
		List<Country> cl = new ArrayList<>();
		cl = this.jdbcTemplate.query("SELECT * FROM " + table + ";", new RowMapper<Country>() {
			@Override
			public Country mapRow(ResultSet rs, int i) throws SQLException {
				Country c = new Country(rs.getString(1), rs.getDouble(2));
				return c;
			}
		});
		return cl;
	}

}
