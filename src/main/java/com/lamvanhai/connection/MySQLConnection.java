package com.lamvanhai.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static Connection getConnection(ConnectionInfo connectionInfo) {
        try {
            Class.forName(connectionInfo.getDriverClassName());
            return DriverManager.getConnection(connectionInfo.getUrl(), connectionInfo.getUser(), connectionInfo.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
