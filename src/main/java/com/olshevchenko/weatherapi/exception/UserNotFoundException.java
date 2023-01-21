package com.olshevchenko.weatherapi.exception;

/**
 * @author Oleksandr Shevchenko
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
