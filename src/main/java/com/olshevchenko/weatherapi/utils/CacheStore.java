package com.olshevchenko.weatherapi.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
public class CacheStore<T> {
    private final Cache<String, T> cache;

    public CacheStore(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public T get(String key) {
        log.info("Record fetched from cache with Key = {}", key);
        return cache.getIfPresent(key);
    }

    public void add(String key, T value) {
        if(key != null && value != null) {
            cache.put(key, value);
            log.info("Record stored in {}; Cache with Key = {}", value.getClass().getSimpleName(), key);
        }
    }
}
