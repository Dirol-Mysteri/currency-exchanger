package org.example.currency_exchanger.currency.exceptions;

public class CurrencyCodeWasntProvided extends RuntimeException {
    public CurrencyCodeWasntProvided() {
        super("Currency code wasn't provided");
    }
}