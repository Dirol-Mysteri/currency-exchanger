package org.example.currency_exchanger.currency;

import java.sql.SQLException;

public class CurrencyService {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();

    public Currency getCurrencyByCode(String userInput) throws SQLException {
        return currencyRepository.getCurrencyByCode(userInput);
    }

    public Currency addCurrency(String code, String name, String sign) throws SQLException {

        Currency currency = new Currency();

        currency.setName(name);
        currency.setCode(code);
        currency.setSign(sign);

        return currencyRepository.addCurrency(currency);
    }
}
