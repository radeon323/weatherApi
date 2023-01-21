package com.olshevchenko.weatherapi.service.api.openweathermap.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
public class SysOpenWeatherMap {
    public int type;
    public int id;
    public String country;
    public int sunrise;
    public int sunset;
}
