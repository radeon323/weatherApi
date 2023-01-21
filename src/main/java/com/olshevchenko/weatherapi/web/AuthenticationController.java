package com.olshevchenko.weatherapi.web;

import com.olshevchenko.weatherapi.entity.User;
import com.olshevchenko.weatherapi.exception.UserAlreadyExistException;
import com.olshevchenko.weatherapi.exception.UserNotFoundException;
import com.olshevchenko.weatherapi.security.jwt.JwtUtils;
import com.olshevchenko.weatherapi.service.ApiUserDetailsService;
import com.olshevchenko.weatherapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Oleksandr Shevchenko
 */
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ApiUserDetailsService apiUserDetailsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String email,
                               @RequestParam String password,
                               HttpServletResponse response,
                               ModelMap model) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            final UserDetails user = apiUserDetailsService.loadUserByUsername(email);
            String token = jwtUtils.generateToken(user);
            Cookie cookie = new Cookie("user-token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "redirect:/weather";
        } catch (UserNotFoundException e) {
            String errorMsg = "User not found. Please enter correct email or <a href='/register'>register</a>.";
            model.addAttribute("errorMsg", errorMsg);
            return "login";
        }
    }


    @GetMapping("/register")
    protected String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    protected String register(@RequestParam String email,
                              @RequestParam String password,
                              ModelMap model) {

        if (email.isEmpty() || password.isEmpty()) {
            String errorMsg = "Please fill up all necessary fields!";
            model.addAttribute("errorMsg", errorMsg);
            return "register";
        }
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        try {
            userService.save(user);
            String msgSuccess = String.format("User <i>%s</i> was successfully registered!", email);
            model.addAttribute("msgSuccess", msgSuccess);
            return "login";
        } catch (UserAlreadyExistException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "login";
        }
    }


}
