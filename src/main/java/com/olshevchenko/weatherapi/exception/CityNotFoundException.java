package com.olshevchenko.weatherapi.exception;

/**
 * @author Oleksandr Shevchenko
 */
public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message) {
        super(message);
    }
}
