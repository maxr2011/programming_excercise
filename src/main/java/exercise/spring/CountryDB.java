package exercise.spring;

import exercise.objects.Country;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDB {

	//Variablen

	//Tabellenname
	private static final String table = "country_table";

	private final JdbcOperations jdbcTemplate;

	public CountryDB(JdbcOperations jdbcOperations) {
		this.jdbcTemplate = jdbcOperations;
	}

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

	//Schreibt ein Land in die Datenbank
	public int writeCountry(String name, double weight){
		return jdbcTemplate.update("INSERT INTO " + table + "(name, weight) VALUES (?,?);", name, weight);
	}

	//Schreibt eine Liste an Ländern in die Datenbank
	public void writeDataToDatabase(List<Country> cl){
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS " + table + " (name varchar(40), weight float(53));");
		for(Country c : cl){
			this.writeCountry(c.getName(), c.getWeight());
		}
	}

	//Leert die Tabelle
	public void clearTable(){
		jdbcTemplate.update("DELETE FROM " + table + ";");
	}

	//Löscht die Tabelle
	public void dropTable(){
		jdbcTemplate.update("DROP TABLE " + table + ";");
	}

}
