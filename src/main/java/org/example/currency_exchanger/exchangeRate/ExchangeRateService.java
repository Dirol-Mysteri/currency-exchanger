package org.example.currency_exchanger.exchangeRate;

import org.example.currency_exchanger.currency.CurrencyRepository;
import org.example.currency_exchanger.exchangeRate.exceptions.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ExchangeRateService {
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private final CurrencyRepository currencyRepository = new CurrencyRepository();


    public ExchangeRateResponseDTO getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {

        int[] currenciesIDs = extractCurrenciesIDsFromUserInput(baseCurrencyCode, targetCurrencyCode);

        ExchangeRate exchangeRate = exchangeRateRepository.getExchangeRate(currenciesIDs[0], currenciesIDs[1]);

        return exchangeRateMapper.exchangeRateToResponseDTO(exchangeRate);
    }

    public ExchangeRateResponseDTO addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) throws SQLException {

        int[] currenciesIDs = extractCurrenciesIDsFromUserInput(baseCurrencyCode, targetCurrencyCode);

        exchangeRateRepository.addExchangeRate(currenciesIDs[0], currenciesIDs[1], rate);
        ExchangeRate newExchangeRate = exchangeRateRepository.getExchangeRate(currenciesIDs[0], currenciesIDs[1]);

        return exchangeRateMapper.exchangeRateToResponseDTO(newExchangeRate);
    }

    public ExchangeRateResponseDTO updateExchangeRate(ExchangeRateResponseDTO currentExchangeRate, BigDecimal newRate) throws SQLException {

        int baseCurrencyId = currentExchangeRate.baseCurrency().getId().intValue();
        int targetCurrencyId = currentExchangeRate.targetCurrency().getId().intValue();

        ExchangeRate exchangeRate = exchangeRateRepository.updateExchangeRate(baseCurrencyId, targetCurrencyId, newRate);

        return exchangeRateMapper.exchangeRateToResponseDTO(exchangeRate);
    }

    public boolean isThereAnExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        int[] currenciesIDs = extractCurrenciesIDsFromUserInput(baseCurrencyCode, targetCurrencyCode);
        try {
            exchangeRateRepository.getExchangeRate(currenciesIDs[0], currenciesIDs[1]);
            return true;
        } catch (ExchangeRateNotFoundException e) {
            return false;
        }
    }

    private int[] extractCurrenciesIDsFromUserInput(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        int[] currenciesCodes = new int[2];

        currenciesCodes[0] = currencyRepository.getCurrencyByCode(baseCurrencyCode).getId().intValue();
        currenciesCodes[1] = currencyRepository.getCurrencyByCode(targetCurrencyCode).getId().intValue();

        return currenciesCodes;
    }
}
