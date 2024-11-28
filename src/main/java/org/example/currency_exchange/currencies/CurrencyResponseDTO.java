package org.example.currency_exchange.currencies;

public record CurrencyResponseDTO(
        String code,
        String fullName,
        String sign
) {
}
