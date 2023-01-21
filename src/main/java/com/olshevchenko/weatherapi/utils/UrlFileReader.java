package com.olshevchenko.weatherapi.utils;

import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Oleksandr Shevchenko
 */
@Component
@NoArgsConstructor
public class UrlFileReader {

    public String readFromUrl(String path, String city) {
        HttpURLConnection connection = weatherAPIConnection(path);
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.append(inputLine);
            }
            return String.valueOf(content);
        } catch (IOException e) {
            throw new CityNotFoundException("City " + city + " not found");
        }
    }

    @SneakyThrows
    HttpURLConnection weatherAPIConnection(String path) {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}
