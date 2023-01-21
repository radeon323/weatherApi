package com.olshevchenko.weatherapi.web;

import com.olshevchenko.weatherapi.entity.User;
import com.olshevchenko.weatherapi.security.model.ApiUser;
import com.olshevchenko.weatherapi.security.jwt.JwtUtils;
import com.olshevchenko.weatherapi.service.ApiUserDetailsService;
import com.olshevchenko.weatherapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.olshevchenko.weatherapi.security.model.Role.USER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@ComponentScan(basePackages = "com.olshevchenko.weatherapi")
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private ApiUserDetailsService apiUserDetailsService;

    private final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken( "some@email.com", "password");


    @Test
    void testShouldRedirectToWeatherPageAfterPostRequest() throws Exception {
        ApiUser user = ApiUser.builder()
                .email("some@email.com")
                .password("password")
                .role(USER)
                .grantedAuthorities(USER.getGrantedAuthorities())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
        String token = "YmFhZjNjZTEtNTIyZi00OTNmLWI1OGYtYjVhNjY1NDcxMGNh";
        HttpHeaders headers = new HttpHeaders();
        headers.add("email", "some@email.com");
        headers.add("password", "password");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("some@email.com", "password")))
                .thenReturn(authenticationToken);
        when(apiUserDetailsService.loadUserByUsername("some@email.com")).thenReturn(user);
        when(jwtUtils.generateToken(user)).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .params(headers))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/weather"));
    }

    @Test
    void testShouldRegisterAndShowLoginPage() throws Exception {
        User user = User.builder()
                .email("some@email.com")
                .password("password")
                .role(USER)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("email", "some@email.com");
        headers.add("password", "password");

        doNothing().when(userService).save(user);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .params(headers))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testShouldReturnViewOfLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @Test
    void testShouldReturnViewOfRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }


}