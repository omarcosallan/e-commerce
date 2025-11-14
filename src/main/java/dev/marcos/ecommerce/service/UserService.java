package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(UserMapper::toDTO).toList();
    }
}
