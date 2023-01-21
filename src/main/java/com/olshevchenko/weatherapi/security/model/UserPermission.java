package com.olshevchenko.weatherapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@AllArgsConstructor
public enum UserPermission {
    USER_READ("users:read"),
    USER_WRITE("users:write");

    private final String permission;
}
