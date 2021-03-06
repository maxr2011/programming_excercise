package exercise.spring;

import exercise.interfaces.DBInterface;
import exercise.objects.Country;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CountryDB implements DBInterface<Country> {

	//Variablen

	//Tabellenname
	private static final String table = "country_table";

	private final JdbcOperations jdbcTemplate;

	public CountryDB(JdbcOperations jdbcOperations) {
		this.jdbcTemplate = jdbcOperations;
	}

	//Liest Countries von der Datenbank aus
	public List<Country> readFromDatabase() {
		return this.jdbcTemplate.query("SELECT * FROM " + table + ";",
				(rs, i) -> new Country(rs.getString(1), rs.getDouble(2)));
	}

	//Schreibt ein Land in die Datenbank
	private void writeCountry(String name, double weight){
		jdbcTemplate.update("INSERT INTO " + table + "(name, weight) VALUES (?,?);", name, weight);
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
