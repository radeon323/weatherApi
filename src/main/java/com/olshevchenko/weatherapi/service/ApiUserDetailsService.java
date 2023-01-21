package com.olshevchenko.weatherapi.service;

import com.olshevchenko.weatherapi.entity.User;
import com.olshevchenko.weatherapi.repository.UserRepository;
import com.olshevchenko.weatherapi.security.model.ApiUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Oleksandr Shevchenko
 */
@Service
@RequiredArgsConstructor
public class ApiUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", email)));
        return ApiUser.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
