package com.olshevchenko.weatherapi.web;

import com.olshevchenko.weatherapi.exception.CityNotFoundException;
import com.olshevchenko.weatherapi.model.UserSettings;
import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.security.model.ApiUser;
import com.olshevchenko.weatherapi.service.ApiUserDetailsService;
import com.olshevchenko.weatherapi.service.UserService;
import com.olshevchenko.weatherapi.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.olshevchenko.weatherapi.security.model.Role.USER;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@ComponentScan(basePackages = "com.olshevchenko.weatherapi")
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private UserService userService;

    @MockBean
    private ApiUserDetailsService apiUserDetailsService;

    private WeatherMainInfo weatherMainInfo;
    private UserSettings settings;
    private HttpHeaders headers;
    private ApiUser user;

    @BeforeEach
    void init() {
        settings = new UserSettings(1, "WEATHERAPI", "Kyiv", true, true, true, true);
        weatherMainInfo = new WeatherMainInfo(1, "Kyiv", 5.6, 75, 4, 100, settings);

        headers = new HttpHeaders();
        headers.add("id", "1");
        headers.add("weatherProviderName", "WEATHERAPI");
        headers.add("city", "Kyiv");
        headers.add("temperature", "true");
        headers.add("humidity", "true");
        headers.add("windSpeed", "true");
        headers.add("clouds", "true");

        user = ApiUser.builder()
                .email("some@email.com")
                .password("password")
                .role(USER)
                .grantedAuthorities(USER.getGrantedAuthorities())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }

    @Test
    void testShouldReturnViewOfWeatherPageWithoutUserLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather_main"));
    }

    @Test
    void testShouldReturnViewOfWeatherPageWithUserLogin() throws Exception {
        when(weatherService.fetchWeatherDataFromSourceOrCache("some@email.com")).thenReturn(weatherMainInfo);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather")
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists( "user", "weather"))
                .andExpect(view().name("weather_main"));
        verify(weatherService, times(1)).fetchWeatherDataFromSourceOrCache("some@email.com");
    }

    @Test
    void testShouldReturnViewOfWeatherPageWithUserLoginIfWeatherInfoNull() throws Exception {
        when(weatherService.fetchWeatherDataFromSourceOrCache("some@email.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather")
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMsg", "Please make your choice!"))
                .andExpect(model().attributeExists( "user"))
                .andExpect(view().name("weather_main"));
        verify(weatherService, times(1)).fetchWeatherDataFromSourceOrCache("some@email.com");
    }

    @Test
    void testPostRequestOnWeatherPage() throws Exception {

        when(weatherService.fetchWeatherDataFromSourceOrCache(settings, "some@email.com")).thenReturn(weatherMainInfo);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/weather")
                        .params(headers)
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists( "user", "weather"))
                .andExpect(view().name("weather_main"));
        verify(weatherService, times(1)).fetchWeatherDataFromSourceOrCache(settings, "some@email.com");
    }

    @Test
    void testPostRequestOnWeatherPageThrowCityNotFoundException() throws Exception {

        when(weatherService.fetchWeatherDataFromSourceOrCache(settings, "some@email.com")).thenThrow(new CityNotFoundException("message"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/weather")
                        .params(headers)
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMsg", "message"))
                .andExpect(model().attributeExists( "user"))
                .andExpect(view().name("weather_main"));
        verify(weatherService, times(1)).fetchWeatherDataFromSourceOrCache(settings, "some@email.com");
    }

    @Test
    void testPostRequestOnWeatherPageIfWeatherApiNotChosen() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/weather")
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMsg", "Choose the weather API!"))
                .andExpect(model().attributeExists( "user"))
                .andExpect(view().name("weather_main"));
    }


}