package org.example.currency_exchanger.exchange;

import org.example.currency_exchanger.currency.Currency;

import java.math.BigDecimal;

public record ExchangeResponseDTO(
        Currency baseCurrency,
        Currency targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount
) {
}
