package com.olshevchenko.weatherapi.service.api.openweathermap.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
public class WeatherOpenWeatherMap {
    public int id;
    public String main;
    public String description;
    public String icon;
}
