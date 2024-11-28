package org.example.currency_exchange.currencies;

public class CurrenciesMapper {
    public CurrencyResponseDTO currenciesToResponseDTO(Currency currency) {
        return new CurrencyResponseDTO(currency.getCode(), currency.getFullName(), currency.sign);
    }
}
