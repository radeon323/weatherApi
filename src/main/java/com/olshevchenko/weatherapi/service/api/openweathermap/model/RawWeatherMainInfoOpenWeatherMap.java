package com.olshevchenko.weatherapi.service.api.openweathermap.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
@ToString
public class RawWeatherMainInfoOpenWeatherMap implements Serializable {
    public CoordOpenWeatherMap coord;
    public List<WeatherOpenWeatherMap> weather;
    public String base;
    public MainOpenWeatherMap main;
    public int visibility;
    public WindOpenWeatherMap wind;
    public CloudsOpenWeatherMap clouds;
    public int dt;
    public SysOpenWeatherMap sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
