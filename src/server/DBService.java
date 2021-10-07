package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {

    private static DBService instance;

    public static DBService gI() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/dragon_boy";
            String user = "root";
            String password = "luanhugu207202";

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public static int resultSize(ResultSet rs){
        int count = 0;
        try {
            while (rs.next()) {
                count++;
            }
            rs.beforeFirst();
        } catch (SQLException e) {
        }
        return count;
    }
}
