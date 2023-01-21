package com.olshevchenko.weatherapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.olshevchenko.weatherapi.security.model.UserPermission.USER_READ;
import static com.olshevchenko.weatherapi.security.model.UserPermission.USER_WRITE;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@AllArgsConstructor
public enum Role {
    USER(Set.of(USER_READ, USER_WRITE));
    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
