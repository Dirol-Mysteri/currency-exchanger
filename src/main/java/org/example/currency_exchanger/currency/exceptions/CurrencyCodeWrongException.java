package org.example.currency_exchanger.currency.exceptions;

public class CurrencyCodeWrongException extends RuntimeException {
    public CurrencyCodeWrongException(String currencyCode) {
        super("Currency code \"" + currencyCode + "\" is invalid.");
    }
}