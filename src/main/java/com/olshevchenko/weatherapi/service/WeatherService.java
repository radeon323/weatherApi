package com.olshevchenko.weatherapi.service;

import com.olshevchenko.weatherapi.model.UserSettings;
import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.service.api.WeatherProvider;
import com.olshevchenko.weatherapi.utils.CacheStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.olshevchenko.weatherapi.service.enumService.Services.OPENWEATHERMAP;
import static com.olshevchenko.weatherapi.service.enumService.Services.WEATHERAPI;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final CacheStore<WeatherMainInfo> weatherMainInfoCache;
    private final WeatherProvider openWeatherMapProvider;
    private final WeatherProvider weatherApiProvider;

    public WeatherMainInfo fetchWeatherDataFromSourceOrCache(UserSettings settings, String email) {
        WeatherMainInfo weatherMainInfo = weatherMainInfoCache.get(email);
        if ((weatherMainInfo == null && settings != null) ||
                !Objects.equals(Objects.requireNonNull(weatherMainInfo).getUserSettings(), settings)) {
            weatherMainInfo = getWeatherData(settings);
            weatherMainInfoCache.add(email, weatherMainInfo);
        }
        return weatherMainInfo;
    }

    public WeatherMainInfo fetchWeatherDataFromSourceOrCache(String email) {
        return weatherMainInfoCache.get(email);
    }

    WeatherMainInfo getWeatherData(UserSettings settings) {
        WeatherProvider weatherProvider = selectWeatherProvider(settings);
        WeatherMainInfo weatherMainInfo = weatherProvider.jsonToWeatherPOJO(settings.getCity());
        weatherMainInfo.setUserSettings(settings);
        return weatherMainInfo;
    }

    WeatherProvider selectWeatherProvider(UserSettings settings) {
        WeatherProvider weatherProvider = null;
        if (settings != null && Objects.equals(settings.getWeatherProviderName(), OPENWEATHERMAP.name())) {
            weatherProvider = openWeatherMapProvider;
        } else if (settings != null && Objects.equals(settings.getWeatherProviderName(), WEATHERAPI.name())) {
            weatherProvider = weatherApiProvider;
        }
        return weatherProvider;
    }

}
