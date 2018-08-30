package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	// Mainmethode
	public static void main(String[] args) {

		String SQL = "SELECT * FROM test";

		try(Connection testconn = JDBC.connect();
			Statement stmt = testconn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL)){

			while(rs.next()){
				//gibt alle Namen von Tabelle "test" aus
				System.out.println(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
