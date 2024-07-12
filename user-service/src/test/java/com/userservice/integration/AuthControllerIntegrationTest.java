package com.userservice.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.container.TestContainerOwner;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.UserDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest extends TestContainerOwner {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRegisterUser() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "email@email.com", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(registerUserDto), headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/register"),
                HttpMethod.POST,
                request,
                UserDto.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseEntity.getBody());
        assertEquals("username", responseEntity.getBody().name());
    }

    @Test
    public void testRegisterUserInvalidEmail() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "email", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(registerUserDto), headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/register"),
                HttpMethod.POST,
                request,
                UserDto.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
