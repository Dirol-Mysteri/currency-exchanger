package org.example.currency_exchanger.exchangeRates;

import org.example.currency_exchanger.exchangeRate.exceptions.InvalidExchangeRateCode;
import org.example.currency_exchanger.exchangeRates.exceptions.CurrenciesValuesWerentProvidedException;
import org.example.currency_exchanger.exchangeRates.exceptions.FormFieldNotFoundException;

import java.math.BigDecimal;

public class ExchangeRatesUtils {
    public static void checkExchangeRateCodeForCorrectness(String exchangeRateCode) throws InvalidExchangeRateCode {
        if (exchangeRateCode != null) {
            if (exchangeRateCode.contains(" ") && exchangeRateCode.length() > 6) {
                throw new InvalidExchangeRateCode();
            }
        } else {
            throw new RuntimeException("ExchangeRate code not provided");
        }
    }

    public static void checkNewExchangeParams(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        if (baseCurrencyCode == null) {
            throw new FormFieldNotFoundException(baseCurrencyCode);
        }

        if (targetCurrencyCode == null) {
            throw new FormFieldNotFoundException(targetCurrencyCode);
        }

        if (rate == null) {
            throw new FormFieldNotFoundException("rate");
        }


        if (baseCurrencyCode.isEmpty()) {
            throw new CurrenciesValuesWerentProvidedException("baseCurrencyCode");
        }

        if (targetCurrencyCode.isEmpty()) {
            throw new CurrenciesValuesWerentProvidedException("targetCurrencyCode");
        }

        if (rate.isEmpty()) {
            throw new CurrenciesValuesWerentProvidedException("rate");
        }

        try {
            BigDecimal bigDecimalRate = new BigDecimal(rate);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid rate value. Please provide a valid rate value.");
        }
    }

    public static String[] getCurrencyCodesFromExchangeRate(String exchangeRateCode) throws InvalidExchangeRateCode {
        checkExchangeRateCodeForCorrectness(exchangeRateCode);
        String currencyCode1 = exchangeRateCode.substring(0, 3);
        String currencyCode2 = exchangeRateCode.substring(3, 6);

        return new String[]{currencyCode1, currencyCode2};
    }
}
