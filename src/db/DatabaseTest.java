package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    private static final String URL = "jdbc:mysql://localhost:3306/opusdei";
    private static final String USER = "root";
    private static final String PASSWORD = "Mekksrules2002$";

    public static void testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER,PASSWORD )) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println("Connected to the MySQL server version: " + rs.getString(1));
            }
            else{System.out.println("not connected");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

