package org.example.currency_exchange.currencies;

import org.example.currency_exchange.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesService {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final CurrenciesMapper currenciesMapper = new CurrenciesMapper();

    public List<CurrencyResponseDTO> getCurrencies() {
        List<CurrencyResponseDTO> result = new ArrayList<>();
        String query = "SELECT * FROM Currencies";
        try (Connection connection = databaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet response = statement.executeQuery()) {
            while (response.next()) {
                Currency currency = new Currency();
                currency.setId(response.getInt("id"));
                currency.setCode(response.getString("code"));
                currency.setSign(response.getString("sign"));
                currency.setFullName(response.getString("fullName"));
                result.add(currenciesMapper.currenciesToResponseDTO(currency));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
