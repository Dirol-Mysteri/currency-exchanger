package org.example.currency_exchanger.exchangeRate.exceptions;

public class ExchangeRateNotFoundException extends RuntimeException {
    public ExchangeRateNotFoundException() {
        super("Exchange rate for the pair was not found.");
    }
}