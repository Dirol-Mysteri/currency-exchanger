package org.example.currency_exchanger.exchangeRate;

import org.example.currency_exchanger.currency.Currency;

import java.math.BigDecimal;

public record ExchangeRateResponseDTO(
        int id,
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate
) {
}
