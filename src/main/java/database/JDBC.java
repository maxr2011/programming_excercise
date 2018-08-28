package database;

import java.sql.*;

public class JDBC {

    private static final String url = "jdbc:postgresql://localhost/exercise";
    private static final String user = "mare";
    private static final String password = "toor";

    public static void main(String[] args) {

        String SQL = "SELECT * FROM test";

        try(
        Connection testconn = connect();
        Statement stmt = testconn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL)
        ){

            while(rs.next()){
                //gibt alle Namen von Tabelle "test" aus
                System.out.println(rs.getString("name"));
            }

        } catch (SQLException e) {

        }

    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}
