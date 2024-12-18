package org.example.currency_exchanger.exchangeRate.exceptions;

public class ExistingExchangeRateException extends RuntimeException {
    public ExistingExchangeRateException() {
        super("The exchange rate already exists.");
    }
}