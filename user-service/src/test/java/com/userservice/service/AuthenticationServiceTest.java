package com.userservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.userservice.dto.AuthenticateUserDto;
import com.userservice.entity.User;
import com.userservice.security.service.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testValidateToken() {
        User user = new User();
        user.setEmail("email@email.com");
        Mockito.when(jwtService.extractUsername(Mockito.anyString())).thenReturn("userName");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(new User());
        Mockito.when(jwtService.isTokenValid(Mockito.anyString(), Mockito.any())).thenReturn(true);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(user);
        assertEquals(user.getEmail(), authenticationService.validateToken("email@email.com").email());
    }

    @Test
    void testValidateTokenInvalidToken() {
        User user = new User();
        user.setEmail("email@email.com");
        Mockito.when(jwtService.extractUsername(Mockito.anyString())).thenReturn("userName");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(new User());
        Mockito.when(jwtService.isTokenValid(Mockito.anyString(), Mockito.any())).thenReturn(false);
        assertThrows(RuntimeException.class, () -> authenticationService.validateToken("email@email.com"));
    }

    @Test
    void testAuthenticate() {
        AuthenticateUserDto authenticateUserDto = new AuthenticateUserDto("email@email.com", "password");
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(new User());
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("token");
        assertEquals("token", authenticationService.authenticate(authenticateUserDto).token());
    }
}