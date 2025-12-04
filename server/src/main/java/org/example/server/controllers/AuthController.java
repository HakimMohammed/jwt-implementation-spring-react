package org.example.server.controllers;

import org.example.server.dto.AuthRequest;
import org.example.server.dto.AuthResponse;
import org.example.server.services.JwtService;
import org.example.server.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        var auth = new UsernamePasswordAuthenticationToken(req.username(), req.password());
        authenticationManager.authenticate(auth);
        UserDetails user = userService.loadUserByUsername(req.username());
        String token = jwtService.generateToken(user.getUsername(), Map.of("roles", user.getAuthorities()));
        return new AuthResponse(token);
    }
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Bonjour, endpoint protégé OK ");
    }
}
