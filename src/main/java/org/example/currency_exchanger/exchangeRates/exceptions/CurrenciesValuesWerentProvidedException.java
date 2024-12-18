package org.example.currency_exchanger.exchangeRates.exceptions;

public class CurrenciesValuesWerentProvidedException extends RuntimeException {
    public CurrenciesValuesWerentProvidedException(String fieldName) {
        super("Values of field" + fieldName + "was not provided.");
    }
}