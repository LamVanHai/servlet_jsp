package com.lamvanhai.repository.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    public static Connection getConnection() {
        try {
            return ConnectionManager.getDs().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
