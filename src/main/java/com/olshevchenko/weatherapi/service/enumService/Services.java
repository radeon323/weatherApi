package com.olshevchenko.weatherapi.service.enumService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
@RequiredArgsConstructor
public enum Services {
    OPENWEATHERMAP(),
    WEATHERAPI()
}
