package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.dto.LoginRequest;
import dev.marcos.ecommerce.dto.RegisterRequest;
import dev.marcos.ecommerce.dto.user.UserDTO;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.repository.UserRepository;
import dev.marcos.ecommerce.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserDTO save(RegisterRequest dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail inválido");
        }
        if (repository.existsByUsername(dto.username())) {
            throw new RuntimeException("Username inválido");
        }

        User user = new User(null, dto.username(), dto.email(), null, dto.firstName(), dto.lastName(), null);
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        user.setPasswordHash(encryptedPassword);

        repository.save(user);

        return UserMapper.toDTO(user);
    }

    public String login(LoginRequest dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken(dto.email());
    }
}
