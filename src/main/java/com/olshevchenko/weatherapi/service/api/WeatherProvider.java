package com.olshevchenko.weatherapi.service.api;

import com.olshevchenko.weatherapi.model.WeatherMainInfo;

/**
 * @author Oleksandr Shevchenko
 */
public interface WeatherProvider {
    WeatherMainInfo jsonToWeatherPOJO(String city);
}
