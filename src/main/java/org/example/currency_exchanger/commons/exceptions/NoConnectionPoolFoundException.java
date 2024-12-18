package org.example.currency_exchanger.commons.exceptions;

public class NoConnectionPoolFoundException extends RuntimeException {
    public NoConnectionPoolFoundException() {
        super("There is no connection pool found");
    }
}