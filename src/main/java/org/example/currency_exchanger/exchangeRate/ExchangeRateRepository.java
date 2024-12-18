package org.example.currency_exchanger.exchangeRate;

import org.example.currency_exchanger.commons.ConnectionPool;
import org.example.currency_exchanger.exchangeRate.exceptions.ExchangeRateNotFoundException;
import org.example.currency_exchanger.exchangeRate.exceptions.ExistingExchangeRateException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateRepository {
    private final ConnectionPool connectionPool;

    public ExchangeRateRepository() {
        connectionPool = ConnectionPool.getInstance();
    }

    public ExchangeRate getExchangeRate(int baseCurrencyId, int targetCurrencyId) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        String query = "SELECT * FROM ExchangeRates WHERE baseCurrencyId = ? and targetCurrencyId = ?";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, baseCurrencyId);
            statement.setInt(2, targetCurrencyId);
            try (ResultSet response = statement.executeQuery()) {
                if (response.next()) {
                    exchangeRate = new ExchangeRate();
                    exchangeRate.setId(response.getInt("id"));
                    exchangeRate.setBaseCurrencyId(response.getInt("baseCurrencyId"));
                    exchangeRate.setTargetCurrencyId(response.getInt("targetCurrencyId"));
                    exchangeRate.setRate(response.getBigDecimal("rate"));
                    return exchangeRate;
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
        }
        throw new ExchangeRateNotFoundException();
    }

    public void addExchangeRate(int baseCurrencyId, int targetCurrencyId, BigDecimal rate) throws SQLException {
        String query = "INSERT INTO ExchangeRates(baseCurrencyId, targetCurrencyId, rate) VALUES(?, ?, ?);";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, baseCurrencyId);
            statement.setInt(2, targetCurrencyId);
            statement.setBigDecimal(3, rate);
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // Unique Constraint Code
                throw new ExistingExchangeRateException();
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public ExchangeRate updateExchangeRate(int baseCurrencyId, int targetCurrencyId, BigDecimal newRate) throws SQLException {
        String query = "UPDATE ExchangeRates SET rate = ? WHERE baseCurrencyId = ? and targetCurrencyId = ?";

        Connection connection = connectionPool.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setBigDecimal(1, newRate);
            statement.setInt(2, baseCurrencyId);
            statement.setInt(3, targetCurrencyId);
            statement.executeUpdate();
            return getExchangeRate(baseCurrencyId, targetCurrencyId);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
