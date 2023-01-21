package com.olshevchenko.weatherapi.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Shevchenko
 */
class CacheStoreTest {

    @Test
    void testCacheAddAndGetAfterTimeoutShouldClear() throws InterruptedException {
        CacheStore<String> cacheStore = new CacheStore<>(2, TimeUnit.SECONDS);
        cacheStore.add("testKey", "testValue");
        assertEquals("testValue", cacheStore.get("testKey"));
        Thread.sleep(1000);
        assertEquals("testValue", cacheStore.get("testKey"));
        Thread.sleep(1000);
        assertNull(cacheStore.get("testKey"));
    }
}