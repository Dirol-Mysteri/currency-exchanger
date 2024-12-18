package org.example.currency_exchanger.exchangeRates;

import org.example.currency_exchanger.commons.ConnectionPool;
import org.example.currency_exchanger.exchangeRate.ExchangeRate;
import org.example.currency_exchanger.exchangeRates.exceptions.NoExchangeRatesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesRepository {
    private final ConnectionPool connectionPool;

    public ExchangeRatesRepository() {
        connectionPool = ConnectionPool.getInstance();
    }

    public List<ExchangeRate> getExchangeRates() throws SQLException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        String query = "SELECT * FROM ExchangeRates";

        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet response = statement.executeQuery();
        ) {
            while (response.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setId(response.getInt("id"));
                exchangeRate.setBaseCurrencyId(response.getInt("baseCurrencyId"));
                exchangeRate.setTargetCurrencyId(response.getInt("targetCurrencyId"));
                exchangeRate.setRate(response.getBigDecimal("rate"));
                exchangeRates.add(exchangeRate);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        if (exchangeRates.isEmpty()) {
            throw new NoExchangeRatesException();
        }

        return exchangeRates;
    }


}
