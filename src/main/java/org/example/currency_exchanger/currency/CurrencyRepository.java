package org.example.currency_exchanger.currency;

import org.example.currency_exchanger.commons.ConnectionPool;
import org.example.currency_exchanger.currency.exceptions.CurrencyAlreadyExistsException;
import org.example.currency_exchanger.currency.exceptions.ProvidedCurrencyDoesntExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRepository {
    private final ConnectionPool connectionPool;

    public CurrencyRepository() {
        connectionPool = ConnectionPool.getInstance();
    }

    public Currency addCurrency(Currency currency) throws SQLException {
        String createQuery = "INSERT INTO Currencies (code, fullName, sign) VALUES(?, ?, ?)";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement createStatement = connection.prepareStatement(createQuery);
        ) {
            createStatement.setString(1, currency.getCode());
            createStatement.setString(2, currency.getName());
            createStatement.setString(3, currency.getSign());

            int creationResult = createStatement.executeUpdate();

            if (creationResult == 1) {
                return getCurrencyByCode(currency.getCode());
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new CurrencyAlreadyExistsException();
            }
            throw new SQLException();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return new Currency();
    }

    public Currency getCurrencyByCode(String currencyCode) throws SQLException {
        Currency currency = null;
        String query = "SELECT * FROM Currencies WHERE code=?";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, currencyCode);
            ResultSet response = statement.executeQuery();

            while (response.next()) {
                currency = new Currency();
                currency.setId(response.getLong("id"));
                currency.setName(response.getString("fullName"));
                currency.setCode(response.getString("code"));
                currency.setSign(response.getString("sign"));
            }
            response.close();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        if (currency == null) {
            throw new ProvidedCurrencyDoesntExistException(currencyCode);
        }
        return currency;
    }

    public Currency getCurrencyById(int currencyId) throws SQLException {
        Currency currency = null;
        String query = "SELECT * FROM Currencies WHERE id=?";
        Connection connection = connectionPool.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, currencyId);
            ResultSet response = statement.executeQuery();

            while (response.next()) {
                currency = new Currency();
                currency.setId(response.getLong("id"));
                currency.setName(response.getString("fullName"));
                currency.setCode(response.getString("code"));
                currency.setSign(response.getString("sign"));
            }
            response.close();
            return currency;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
