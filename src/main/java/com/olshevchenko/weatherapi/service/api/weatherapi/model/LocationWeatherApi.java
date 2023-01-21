package com.olshevchenko.weatherapi.service.api.weatherapi.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@ToString
public class LocationWeatherApi {
    public String name;
    public String region;
    public String country;
    public double lat;
    public double lon;
    public String tz_id;
    public int localtime_epoch;
    public String localtime;
}
