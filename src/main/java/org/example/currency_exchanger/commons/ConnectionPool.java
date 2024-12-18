package org.example.currency_exchanger.commons;

import org.example.currency_exchanger.commons.exceptions.NoConnectionPoolFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool {
    private static ConnectionPool instance;
    private Vector<Connection> availableConnections = new Vector<>();
    private Vector<Connection> usedConnections = new Vector<>();
    private String url;
    private String user;
    private String password;

    private ConnectionPool(String url, String user, String password, int initialConnections) {
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            for (int i = 0; i < initialConnections; i++) {
                availableConnections.add(createConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            throw new NoConnectionPoolFoundException();
        }
        return instance;
    }

    public static void createPool(String url, String user, String password, int initialConnections) {
        if (instance == null) {
            instance = new ConnectionPool(url, user, password, initialConnections);
        }
    }

    private Connection createConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(this.url, this.user, this.password);
    }

    public synchronized Connection getConnection() throws SQLException {
        if (availableConnections.isEmpty()) {
            return null; // Или выбросить исключение
        }
        Connection connection = availableConnections.firstElement();
        availableConnections.remove(connection);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }
}
