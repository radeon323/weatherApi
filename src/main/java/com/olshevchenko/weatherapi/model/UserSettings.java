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
public class UserSettings implements Serializable {
    private int id;
    private String weatherProviderName;
    private String city;
    private boolean temperature;
    private boolean humidity;
    private boolean windSpeed;
    private boolean clouds;
}
