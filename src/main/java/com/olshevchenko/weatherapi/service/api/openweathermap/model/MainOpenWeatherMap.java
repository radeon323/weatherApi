package com.olshevchenko.weatherapi.service.api.openweathermap.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
public class MainOpenWeatherMap {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;
}

