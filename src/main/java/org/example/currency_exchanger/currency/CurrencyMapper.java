package org.example.currency_exchanger.currency;

public class CurrencyMapper {
    public CurrencyResponseDTO currencyToResponseDTO(Currency currency) {
        return new CurrencyResponseDTO(currency.getId(), currency.getName(), currency.getCode(), currency.getSign());
    }
}
