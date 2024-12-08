// New file: RegisterService.java
package com.polo.librarybackend.services;

import com.polo.librarybackend.dto.RegisterRequest;
import com.polo.librarybackend.entity.Role;
import com.polo.librarybackend.entity.User;
import com.polo.librarybackend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegisterService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final UserService userService;
    private final RoleRepository roleRepository;

    public RegisterService(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public User registerUser(RegisterRequest request) {
        validateRegistrationRequest(request);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        Role userRole = roleRepository.findByRoleName("user")
                .orElseThrow(() -> new RuntimeException("User role not found"));
        user.setRole(userRole);

        return userService.createUser(user);
    }

    private void validateRegistrationRequest(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        if (!Pattern.matches(EMAIL_REGEX, request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (userService.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}