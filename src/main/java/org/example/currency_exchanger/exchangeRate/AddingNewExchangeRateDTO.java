package org.example.currency_exchanger.exchangeRate;

import java.math.BigDecimal;

public record AddingNewExchangeRateDTO(
        String baseCurrencyCode,
        String targetCurrencyCode,
        BigDecimal rate
) {
}
