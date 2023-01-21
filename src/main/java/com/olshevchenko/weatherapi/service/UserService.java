package com.olshevchenko.weatherapi.service;

import com.olshevchenko.weatherapi.entity.User;
import com.olshevchenko.weatherapi.exception.UserAlreadyExistException;
import com.olshevchenko.weatherapi.exception.UserNotFoundException;
import com.olshevchenko.weatherapi.repository.UserRepository;
import com.olshevchenko.weatherapi.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Oleksandr Shevchenko
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by id: " + id));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + email));
    }

    public void save(User user) {
        String email = user.getEmail();
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException("User with email: " + email + " already exists!");
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        repository.save(user);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
