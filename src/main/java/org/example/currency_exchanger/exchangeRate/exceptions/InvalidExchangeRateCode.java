package org.example.currency_exchanger.exchangeRate.exceptions;

public class InvalidExchangeRateCode extends RuntimeException {
    public InvalidExchangeRateCode() {
        super("Invalid exchange rate code.");
    }
}