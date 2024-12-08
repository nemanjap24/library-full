package com.polo.librarybackend.controller;

import com.polo.librarybackend.dto.AuthenticationResponse;
import com.polo.librarybackend.dto.LoginRequest;
import com.polo.librarybackend.entity.User;
import com.polo.librarybackend.services.JwtService;
import com.polo.librarybackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/login")
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;

    public LoginController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtService.generateToken(user);
            AuthenticationResponse response = new AuthenticationResponse(token, user.getUsername(), user.getRole());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}