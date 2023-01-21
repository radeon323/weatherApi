package com.olshevchenko.weatherapi.service.api.weatherapi;

import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.service.api.weatherapi.model.RawWeatherMainInfoWeatherApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Oleksandr Shevchenko
 */
@Mapper
public interface WeatherApiMapper {
    @Mapping(target="city", source="location.name")
    @Mapping(target="temperature", source="current.temp_c")
    @Mapping(target="humidity", source="current.humidity")
    @Mapping(target="wind_speed", source="current.wind_kph")
    @Mapping(target="clouds", source="current.cloud")
    WeatherMainInfo rawToWeatherMainInfo(RawWeatherMainInfoWeatherApi info);
}
