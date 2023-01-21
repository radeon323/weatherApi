package com.olshevchenko.weatherapi.utils;

import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Shevchenko
 */
class UrlFileReaderTest {

    @Test
    void testReadFromUrlMethodReturnsRawTextOfJsonData() {
        String apiKey = "c17e1a53647108b9d61b824daeee71e0";
        String city = "Kyiv";
        String path = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        UrlFileReader reader = new UrlFileReader();
        String content = reader.readFromUrl(path, city);
        assertTrue(content.contains("Kyiv"));
        assertTrue(content.contains("UA"));
        assertTrue(content.contains("weather"));
        assertTrue(content.contains("temp"));
        assertTrue(content.contains("pressure"));
        assertTrue(content.contains("visibility"));
        assertTrue(content.contains("wind"));
        assertTrue(content.contains("clouds"));
        assertTrue(content.contains("humidity"));
    }

    @Test
    void testReadFromUrlMethodThrowCityNotFoundException() {
        String apiKey = "c17e1a53647108b9d61b824daeee71e0";
        String city = "abrakadabra";
        String path = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        UrlFileReader reader = new UrlFileReader();
        assertThrows(CityNotFoundException.class,
                () -> reader.readFromUrl(path, city),
                "City abrakadabra not found");
    }

    @Test
    void testWeatherAPIConnectionMethodResponseCode200() throws IOException {
        String apiKey = "c17e1a53647108b9d61b824daeee71e0";
        String city = "Kyiv";
        String path = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        UrlFileReader reader = new UrlFileReader();
        HttpURLConnection connection = reader.weatherAPIConnection(path);
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    @Test
    void testWeatherAPIConnectionMethodResponseCode404() throws IOException {
        String apiKey = "c17e1a53647108b9d61b824daeee71e0";
        String city = "abrakadabra";
        String path = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        UrlFileReader reader = new UrlFileReader();
        HttpURLConnection connection = reader.weatherAPIConnection(path);
        int responseCode = connection.getResponseCode();
        assertEquals(404, responseCode);
    }

    @Test
    void testWeatherAPIConnectionMethodThrowIOException() {
        String path = "abrakadabra";
        UrlFileReader reader = new UrlFileReader();
        assertThrows(IOException.class,
                () -> reader.weatherAPIConnection(path));
    }
}