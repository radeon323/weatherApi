package com.olshevchenko.weatherapi.service;

import com.olshevchenko.weatherapi.model.UserSettings;
import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.service.api.WeatherProvider;
import com.olshevchenko.weatherapi.service.api.openweathermap.OpenWeatherMapProvider;
import com.olshevchenko.weatherapi.service.api.weatherapi.WeatherApiProvider;
import com.olshevchenko.weatherapi.utils.CacheStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private OpenWeatherMapProvider openWeatherMapProvider;

    @Mock
    private WeatherApiProvider weatherApiProvider;

    @Mock
    private CacheStore<WeatherMainInfo> weatherMainInfoCache;

    private UserSettings settings;
    private WeatherService weatherService;
    private WeatherMainInfo weatherMainInfo;

    @BeforeEach
    void init() {
        settings = new UserSettings(1, "WEATHERAPI", "Kyiv", true, true, true, true);
        weatherService = new WeatherService(weatherMainInfoCache, openWeatherMapProvider, weatherApiProvider);
        weatherMainInfo = new WeatherMainInfo(1, "Kyiv", 5.6, 75, 4, 100, settings);
    }

    @Test
    void testIfWeTakeInfoFromCache() {
        assertNotNull(weatherMainInfoCache);

        when(weatherMainInfoCache.get("some@email.com")).thenReturn(weatherMainInfo);

        WeatherMainInfo actualInfo = weatherService.fetchWeatherDataFromSourceOrCache(settings, "some@email.com");

        assertEquals(weatherMainInfo, actualInfo);
        verify(weatherMainInfoCache, times(1)).get("some@email.com");
    }

    @Test
    void testIfWeTakeInfoFromSourceBecauseCacheReturnsNull() {
        assertNotNull(weatherMainInfoCache);
        assertNotNull(weatherApiProvider);

        when(weatherMainInfoCache.get("some@email.com")).thenReturn(null);
        when(weatherApiProvider.jsonToWeatherPOJO(settings.getCity())).thenReturn(weatherMainInfo);
        doNothing().when(weatherMainInfoCache).add("some@email.com", weatherMainInfo);

        WeatherMainInfo actualInfo = weatherService.fetchWeatherDataFromSourceOrCache(settings, "some@email.com");

        assertEquals(weatherMainInfo, actualInfo);
        verify(weatherMainInfoCache, times(1)).get("some@email.com");
        verify(weatherMainInfoCache, times(1)).add("some@email.com", weatherMainInfo);
    }

    @Test
    void testJustTakeInfoFromCacheByKey() {
        assertNotNull(weatherMainInfoCache);

        when(weatherMainInfoCache.get("some@email.com")).thenReturn(weatherMainInfo);

        WeatherMainInfo actualInfo = weatherService.fetchWeatherDataFromSourceOrCache("some@email.com");

        assertEquals(weatherMainInfo, actualInfo);
        verify(weatherMainInfoCache, times(1)).get("some@email.com");
    }

    @Test
    void testGetWeatherData() {
        assertNotNull(weatherApiProvider);

        when(weatherApiProvider.jsonToWeatherPOJO(settings.getCity())).thenReturn(weatherMainInfo);

        WeatherMainInfo actualInfo = weatherService.getWeatherData(settings);

        assertEquals(weatherMainInfo, actualInfo);
        verify(weatherApiProvider, times(1)).jsonToWeatherPOJO(settings.getCity());
    }

    @Test
    void testSelectWeatherProvider() {
        WeatherProvider provider = weatherService.selectWeatherProvider(settings);
        assertNotEquals(openWeatherMapProvider, provider);
        assertEquals(weatherApiProvider, provider);
    }
}