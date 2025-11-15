package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.model.dto.LoginRequest;
import dev.marcos.ecommerce.model.dto.RegisterRequest;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest dto) {
        UserDTO user = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
