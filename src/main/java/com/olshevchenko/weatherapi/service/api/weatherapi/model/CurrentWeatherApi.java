package com.olshevchenko.weatherapi.service.api.weatherapi.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
public class CurrentWeatherApi {
    public double temp_c;
    public ConditionWeatherApi condition;
    public double wind_kph;
    public int humidity;
    public int cloud;
    public double uv;
}
