package org.example.currency_exchanger.exchangeRates.exceptions;

public class FormFieldNotFoundException extends RuntimeException {
    public FormFieldNotFoundException(String fieldName) {
        super(fieldName);
    }
}