package org.example.currency_exchange;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final Dotenv dotenv;

    public DatabaseConnection() {
        // Loading .env
        dotenv = Dotenv.configure()
                .directory("/home/aliev008/IdeaProjects/currency_exchange/.env")
                .load();
    }

    public Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found.", e);
        }

        String url = dotenv.get("DATABASE_URL");
        return DriverManager.getConnection(url);
    }
}