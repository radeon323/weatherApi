package com.olshevchenko.weatherapi.web;

import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.model.UserSettings;
import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import com.olshevchenko.weatherapi.security.model.ApiUser;
import com.olshevchenko.weatherapi.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    protected String getWeatherPage(@AuthenticationPrincipal ApiUser currentUser, ModelMap model) {

        if (currentUser == null) {
            return "weather_main";
        }

        WeatherMainInfo weatherMainInfo = weatherService.fetchWeatherDataFromSourceOrCache(currentUser.getEmail());
        model.addAttribute("user", currentUser);

        if (weatherMainInfo == null || weatherMainInfo.getUserSettings() == null ) {
            model.addAttribute("errorMsg", "Please make your choice!");
            return "weather_main";
        }
        model.addAttribute("weather", weatherMainInfo);

        return "weather_main";
    }

    @PostMapping("/weather")
    protected String getResultWeatherPage(@AuthenticationPrincipal ApiUser currentUser, ModelMap model,
                                          HttpSession session, UserSettings settings) {

        model.addAttribute("user", currentUser);
        if (settings.getWeatherProviderName() == null) {
            model.addAttribute("errorMsg", "Choose the weather API!");
            return "weather_main";
        }

        try {
            WeatherMainInfo weatherMainInfo = weatherService.fetchWeatherDataFromSourceOrCache(settings, currentUser.getEmail());

            model.addAttribute("weather", weatherMainInfo);
            session.setAttribute("weather", weatherMainInfo);
        } catch (CityNotFoundException e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return "weather_main";
    }


}
