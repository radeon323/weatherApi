package com.olshevchenko.weatherapi.service;

import com.olshevchenko.weatherapi.entity.User;
import com.olshevchenko.weatherapi.repository.UserRepository;
import com.olshevchenko.weatherapi.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    private UserService userService;

    private User expectedUser;

    @BeforeEach
    void init() {
        userService = new UserService(userRepositoryMock);

        expectedUser = User.builder()
                .id(1)
                .email("darthvader@gmail.com")
                .password("$2a$10$hHINZ1WVjQltKiMiFYyp9OsxnblocMA7yBoCXEn6yarNG.53RR9DK")
                .role(Role.USER)
                .build();
    }

    @Test
    void testFindById() {
        when(userRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(expectedUser));
        User actualUser = userService.findById(1);
        assertEquals(expectedUser, actualUser);
        verify(userRepositoryMock, times(1)).findById(1);
    }

    @Test
    void testFindByEmail() {
        when(userRepositoryMock.findByEmail("darthvader@gmail.com")).thenReturn(Optional.ofNullable(expectedUser));
        User actualUser = userService.findByEmail("darthvader@gmail.com");
        assertEquals(expectedUser, actualUser);
        verify(userRepositoryMock, times(1)).findByEmail("darthvader@gmail.com");
    }

    @Test
    void testAdd() {
        when(userRepositoryMock.save(expectedUser)).thenReturn(expectedUser);
        userRepositoryMock.save(expectedUser);
        verify(userRepositoryMock, times(1)).save(expectedUser);
    }

    @Test
    void testRemove() {
        doNothing().when(userRepositoryMock).deleteById(isA(Integer.class));
        userRepositoryMock.deleteById(1);
        verify(userRepositoryMock, times(1)).deleteById(1);
    }


}