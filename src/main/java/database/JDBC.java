package database;

import exercise.chart.Piechart;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("JDBC")
public class JDBC {

    // Datenbankverbindung
    private static final String url = "jdbc:postgresql://localhost/exercise";
    private static final String user = "mare";
    private static final String password = "toor";

    // Java Logger
    private static final Logger LOGGER = Logger.getLogger(Piechart.class.getName());

    // Datenbankverbindung aufbauen Allgemein
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    // Hilfsmethode um eine einfache Abfrage zu machen ohne Rückgabewert
    public static void minimalQuery(String SQL) {

        try (Connection testconn = connect();
             Statement stmt = testconn.createStatement();
             // we want to execute the query but not use its return
             @SuppressWarnings("unused") ResultSet rs = stmt.executeQuery(SQL)) {

            // nothing to do
            LOGGER.log(Level.FINE, "Connection successful!");

        } catch (SQLException e) {

            // nothing to do
            LOGGER.log(Level.FINE, e.getMessage());

        }

    }

    // Methode um Tabelle zu leeren
    public static void clearTable(String ctable) {
        database.JDBC.minimalQuery("DELETE FROM "+ctable+";");
    }

    // Methode um Tabelle zu droppen
    public static void dropTable(String dtable) {
        database.JDBC.minimalQuery("DROP TABLE "+dtable+";");
    }

}
