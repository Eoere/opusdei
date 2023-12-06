package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/opusdei";
    private static final String USER = "root";
    private static final String PASSWORD = "Mekksrules2002$";

    public static Connection getConnection() throws SQLException {
        System.out.println(DriverManager.getConnection(URL, USER, PASSWORD));
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
