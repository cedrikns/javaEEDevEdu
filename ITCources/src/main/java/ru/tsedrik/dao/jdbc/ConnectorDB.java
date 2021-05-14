package ru.tsedrik.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String driver = resource.getString("db.driver");
        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        String url = resource.getString("db.url");
        String user = resource.getString("db.username");
        String pass = resource.getString("db.password");

        return DriverManager.getConnection(url, user, pass); }
}
