package com.fiap.security_system.controller;

import com.fiap.security_system.auth.JwtService;
import com.fiap.security_system.dto.UserDTO;
import com.fiap.security_system.model.User;
import com.fiap.security_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtService jwtService,UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        User user = userService.findByUsername(userDTO.username());

        if (user == null || !passwordEncoder.matches(userDTO.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if (userService.findByUsername(userDTO.username()) != null) {
            return ResponseEntity.status(400).body("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        userService.saveUser(newUser);

        String token = jwtService.generateToken(newUser.getUsername());

        return ResponseEntity.ok(token);
    }
}
