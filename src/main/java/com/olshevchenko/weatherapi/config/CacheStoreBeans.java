package com.olshevchenko.weatherapi.config;

import com.olshevchenko.weatherapi.model.WeatherMainInfo;
import com.olshevchenko.weatherapi.utils.CacheStore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Oleksandr Shevchenko
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheStoreBeans {

    private int weatherMainInfoCacheTimeout;

    @Bean
    public CacheStore<WeatherMainInfo> weatherMainInfoCache() {
        return new CacheStore<>(weatherMainInfoCacheTimeout, TimeUnit.SECONDS);
    }

}
