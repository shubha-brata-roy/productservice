package com.scaler.productservice.commons;

import com.scaler.productservice.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationCommons {
    private RestTemplate restTemplate;

    @Autowired
    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Third party API call to validate token
    public UserDto validateToken(String token) {
        ResponseEntity<UserDto> response =
                                    restTemplate.getForEntity(
                                    "http://localhost:8181/users/validate/" + token,
                                    UserDto.class);

        return response.getBody();
    }
}
