package org.example.currency_exchanger.exchange;

import org.example.currency_exchanger.currency.Currency;
import org.example.currency_exchanger.currency.CurrencyService;
import org.example.currency_exchanger.exchangeRate.ExchangeRateResponseDTO;
import org.example.currency_exchanger.exchangeRate.ExchangeRateService;
import org.example.currency_exchanger.exchangeRate.exceptions.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class ExchangeService {

    private static final String TRANSITIVE_CURRENCY = "USD";
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final CurrencyService currencyService = new CurrencyService();

    public ExchangeResponseDTO getExchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) throws SQLException {

        Currency baseCurrency = currencyService.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyService.getCurrencyByCode(targetCurrencyCode);

        if (exchangeRateService.isThereAnExchangeRate(baseCurrencyCode, targetCurrencyCode)) {
            ExchangeRateResponseDTO exchangeRateResponseDTO = exchangeRateService.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
            BigDecimal convertedAmount = ExchangeUtils.convertAmount(amount, exchangeRateResponseDTO.rate());
            return new ExchangeResponseDTO(baseCurrency, targetCurrency, exchangeRateResponseDTO.rate(), amount, convertedAmount);
        }

        if (exchangeRateService.isThereAnExchangeRate(targetCurrencyCode, baseCurrencyCode)) {
            ExchangeRateResponseDTO exchangeRateResponseDTO = exchangeRateService.getExchangeRate(targetCurrencyCode, baseCurrencyCode);
            BigDecimal convertedAmount = ExchangeUtils.reverseConvertAmount(amount, exchangeRateResponseDTO.rate());
            return new ExchangeResponseDTO(baseCurrency, targetCurrency, exchangeRateResponseDTO.rate(), amount, convertedAmount);
        }

        if (exchangeRateService.isThereAnExchangeRate(TRANSITIVE_CURRENCY, baseCurrencyCode)
                && exchangeRateService.isThereAnExchangeRate(TRANSITIVE_CURRENCY, targetCurrencyCode)) {
            ExchangeRateResponseDTO exchangeRateResponseDTO1 = exchangeRateService.getExchangeRate(TRANSITIVE_CURRENCY, targetCurrencyCode);
            ExchangeRateResponseDTO exchangeRateResponseDTO2 = exchangeRateService.getExchangeRate(TRANSITIVE_CURRENCY, baseCurrencyCode);
            BigDecimal syntheticRate = exchangeRateResponseDTO1.rate().divide(exchangeRateResponseDTO2.rate(), RoundingMode.UP);
            BigDecimal convertedAmount = ExchangeUtils.convertAmount(amount, syntheticRate);
            return new ExchangeResponseDTO(baseCurrency, targetCurrency, syntheticRate, amount, convertedAmount);
        }
        throw new ExchangeRateNotFoundException();
    }
}
