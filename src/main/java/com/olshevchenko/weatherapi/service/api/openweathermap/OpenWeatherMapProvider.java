package com.olshevchenko.weatherapi.service.api.openweathermap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import com.olshevchenko.weatherapi.config.ParseConfig;
import com.olshevchenko.weatherapi.service.api.WeatherProvider;
import com.olshevchenko.weatherapi.service.api.openweathermap.model.RawWeatherMainInfoOpenWeatherMap;
import com.olshevchenko.weatherapi.utils.UrlFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Oleksandr Shevchenko
 */
@Component
@RequiredArgsConstructor
public class OpenWeatherMapProvider implements WeatherProvider {

    private final ParseConfig config;
    private final OpenWeatherMapMapper openWeatherMapMapper;
    private final UrlFileReader reader;

    public WeatherMainInfo jsonToWeatherPOJO(String city) {
        String path = String.format(config.getOpenWeatherMapPath(), city, config.getOpenWeatherMapApiKey());
        String content = reader.readFromUrl(path, city);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RawWeatherMainInfoOpenWeatherMap rawWeatherMainInfoOpenWeatherMap = mapper.readValue(content, RawWeatherMainInfoOpenWeatherMap.class);
            return openWeatherMapMapper.rawToWeatherMainInfo(rawWeatherMainInfoOpenWeatherMap);
        } catch (JsonProcessingException e) {
            throw new CityNotFoundException("City " + city + " not found");
        }
    }
}
