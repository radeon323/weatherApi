package com.olshevchenko.weatherapi.service.api.openweathermap;

import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.service.api.openweathermap.model.RawWeatherMainInfoOpenWeatherMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Oleksandr Shevchenko
 */
@Mapper
public interface OpenWeatherMapMapper {
    @Mapping(target="city", source="name")
    @Mapping(target="temperature", source="main.temp")
    @Mapping(target="humidity", source="main.humidity")
    @Mapping(target="wind_speed", source="wind.speed")
    @Mapping(target="clouds", source="clouds.all")
    WeatherMainInfo rawToWeatherMainInfo(RawWeatherMainInfoOpenWeatherMap info);
}
