package com.olshevchenko.weatherapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Oleksandr Shevchenko
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "weather")
public class ParseConfig {
    private String openWeatherMapPath;
    private String openWeatherMapApiKey;

    private String weatherApiPath;
    private String weatherApiKey;

}
