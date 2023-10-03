package com.capitole.hotel.core.exception;

public class SearchNotFoundException extends RuntimeException{
    public SearchNotFoundException(final String message) {
        super(message);
    }
}
