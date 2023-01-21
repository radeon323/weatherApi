package com.olshevchenko.weatherapi.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMainInfo implements Serializable {
    private int id;
    private String city;
    private double temperature;
    private double humidity;
    private double wind_speed;
    private int clouds;
    private UserSettings userSettings;
}