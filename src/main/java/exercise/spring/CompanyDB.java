package exercise.spring;

import exercise.objects.Company;
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
public class CompanyDB {

	//Variablen

	//Tabellenname
	private static final String table = "company_table";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

	//Liest Countries von der Datenbank aus
	public List<Company> readFromDatabase() {
		List<Company> cl = new ArrayList<>();
		cl = this.jdbcTemplate.query("SELECT * FROM " + table + ";", new RowMapper<Company>() {
			@Override
			public Company mapRow(ResultSet rs, int i) throws SQLException {
				Company c = new Company(rs.getString("Date"), rs.getString("Security"), rs.getDouble("Weighting"));
				return c;
			}
		});
		return cl;
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
