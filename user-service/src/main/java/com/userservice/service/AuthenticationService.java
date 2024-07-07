package com.userservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.userservice.dto.AuthenticateUserDto;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.TokenDto;
import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.security.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserDto register(RegisterUserDto registerUserDto) {
        return UserDto.of(userService.registerUser(registerUserDto));
    }

    public TokenDto authenticate(AuthenticateUserDto authenticateUserDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateUserDto.email(),
                authenticateUserDto.password()
        ));
        User user = userService.getUserByEmail(authenticateUserDto.email());
        String jwt = jwtService.generateToken(user);
        return new TokenDto(jwt);
    }
}
