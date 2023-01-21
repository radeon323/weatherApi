package com.olshevchenko.weatherapi.service.api.weatherapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import com.olshevchenko.weatherapi.config.ParseConfig;
import com.olshevchenko.weatherapi.service.api.WeatherProvider;
import com.olshevchenko.weatherapi.utils.UrlFileReader;
import com.olshevchenko.weatherapi.service.api.weatherapi.model.RawWeatherMainInfoWeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Oleksandr Shevchenko
 */
@Component
@RequiredArgsConstructor
public class WeatherApiProvider implements WeatherProvider {

    private final ParseConfig config;
    private final WeatherApiMapper weatherApiMapper;
    private final UrlFileReader reader;

    public WeatherMainInfo jsonToWeatherPOJO(String city) {
        String path = String.format(config.getWeatherApiPath(), config.getWeatherApiKey(), city);
        String content = reader.readFromUrl(path, city);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RawWeatherMainInfoWeatherApi rawWeatherMainInfoWeatherApi = mapper.readValue(content, RawWeatherMainInfoWeatherApi.class);
            WeatherMainInfo weatherMainInfo = weatherApiMapper.rawToWeatherMainInfo(rawWeatherMainInfoWeatherApi);
            double windSpeed = weatherMainInfo.getWind_speed();
            weatherMainInfo.setWind_speed(windSpeed/3.6);
            return weatherMainInfo;
        } catch (JsonProcessingException e) {
            throw new CityNotFoundException("City " + city + " not found");
        }
    }
}
