package org.example.currency_exchanger.currencies;

import org.example.currency_exchanger.commons.ConnectionPool;
import org.example.currency_exchanger.currency.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesRepository {
    private final ConnectionPool connectionPool;

    public CurrenciesRepository() {
        connectionPool = ConnectionPool.getInstance();
    }
    public List<Currency> getCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Currencies";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet response = statement.executeQuery();
        ) {
            while (response.next()) {
                Currency currency = new Currency();
                currency.setId((long) response.getInt("id"));
                currency.setName(response.getString("fullName"));
                currency.setCode(response.getString("code"));
                currency.setSign(response.getString("sign"));
                currencies.add(currency);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return currencies;
    }
}
