package org.example.currency_exchanger.currency.exceptions;

public class ProvidedCurrencyDoesntExistException extends RuntimeException {
    public ProvidedCurrencyDoesntExistException(String currencyCode) {
        super("Provided currency " + currencyCode + " does not exist in the database.");
    }
}