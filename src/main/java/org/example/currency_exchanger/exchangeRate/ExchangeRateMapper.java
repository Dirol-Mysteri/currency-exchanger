package org.example.currency_exchanger.exchangeRate;

import org.example.currency_exchanger.currency.Currency;
import org.example.currency_exchanger.currency.CurrencyRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ExchangeRateMapper {
    CurrencyRepository currencyRepository = new CurrencyRepository();

    public ExchangeRateResponseDTO exchangeRateToResponseDTO(ExchangeRate exchangeRate) throws SQLException {

        int id = exchangeRate.getId();
        Currency baseCurrency = currencyRepository.getCurrencyById(exchangeRate.getBaseCurrencyId());
        Currency targetCurrency = currencyRepository.getCurrencyById(exchangeRate.getTargetCurrencyId());
        BigDecimal rate = exchangeRate.getRate();

        return new ExchangeRateResponseDTO(id, baseCurrency, targetCurrency, rate);
    }
}
