package org.example.currency_exchanger.currency.exceptions;

public class CurrencyAlreadyExistsException extends RuntimeException {
    public CurrencyAlreadyExistsException() {
        super("Currency already exists.");
    }
}