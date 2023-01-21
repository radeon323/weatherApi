package com.olshevchenko.weatherapi.exception;

/**
 * @author Oleksandr Shevchenko
 */
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
