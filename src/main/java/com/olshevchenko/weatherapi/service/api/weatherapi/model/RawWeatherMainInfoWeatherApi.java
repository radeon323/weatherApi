package com.olshevchenko.weatherapi.service.api.weatherapi.model;

import lombok.*;
import java.io.Serializable;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
@ToString
public class RawWeatherMainInfoWeatherApi implements Serializable {
    public LocationWeatherApi location;
    public CurrentWeatherApi current;
}
