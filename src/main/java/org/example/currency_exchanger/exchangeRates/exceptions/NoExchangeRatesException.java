package org.example.currency_exchanger.exchangeRates.exceptions;

public class NoExchangeRatesException extends RuntimeException {
    public NoExchangeRatesException() {
        super("There is no exchange rates available in the database");
    }
}