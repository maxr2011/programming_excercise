package exercise.spring;

import exercise.objects.Company;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDB {

	//Variablen

	//Tabellenname
	private static final String table = "company_table";

	private final JdbcOperations jdbcTemplate;

	public CompanyDB(JdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//Liest Countries von der Datenbank aus
	public List<Company> readFromDatabase() {
		return this.jdbcTemplate.query("SELECT * FROM " + table + ";", (rs, i) -> new Company(rs.getString("Date"), rs.getString("Security"), rs.getDouble("Weighting")));
	}

	//Schreibt ein Land in die Datenbank
	public int writeCompany(String date, String security, double weighting){
		return jdbcTemplate.update("INSERT INTO " + table + "(date, security, weighting) VALUES (?,?,?);", date, security, weighting);
	}

	//Schreibt eine Liste an Ländern in die Datenbank
	public void writeDataToDatabase(List<Company> cl){
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS " + table + " (date varchar(40), security varchar(100), weighting float(53));");
		for(Company c : cl){
			this.writeCompany(c.getDate(), c.getSecurity() ,c.getWeighting());
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
