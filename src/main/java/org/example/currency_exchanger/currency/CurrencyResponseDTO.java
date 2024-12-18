package org.example.currency_exchanger.currency;

public record CurrencyResponseDTO(
        Long id,
        String name,
        String code,
        String sign
) {
}
