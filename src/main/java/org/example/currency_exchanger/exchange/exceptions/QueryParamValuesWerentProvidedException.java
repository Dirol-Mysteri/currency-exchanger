package org.example.currency_exchanger.exchange.exceptions;

public class QueryParamValuesWerentProvidedException extends RuntimeException {
    public QueryParamValuesWerentProvidedException() {
        super("Required query parameters were not provided.");
    }
}