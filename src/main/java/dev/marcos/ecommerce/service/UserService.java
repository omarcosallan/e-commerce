package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.enums.Role;
import dev.marcos.ecommerce.exception.ResourceAlreadyExistsException;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.model.dto.RegisterRequest;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public PaginatedResponse<UserDTO> findAll(int currentPage, int size, Sort.Direction direction, String sortField) {
        Pageable pageable = PageRequest.of(currentPage, size, direction, sortField);
        Page<User> page = repository.findAll(pageable);
        List<UserDTO> data = page.getContent().stream().map(UserMapper::toDTO).toList();
        return new PaginatedResponse<>(
                data,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                size,
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public UserDTO findById(long id) {
        return UserMapper.toDTO(getUser(id));
    }

    public UserDTO save(@Valid RegisterRequest dto) {
        if (repository.existsByEmail(dto.email()) || repository.existsByUsername(dto.username())) {
            throw new ResourceAlreadyExistsException("Dados ausentes ou inválidos");
        }

        Role role = dto.role() != null ? dto.role() : Role.USER;
        User user = new User(null, dto.username(), dto.email(), null, dto.firstName(), dto.lastName(), null, role);
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        user.setPasswordHash(encryptedPassword);

        repository.save(user);

        return UserMapper.toDTO(user);
    }

    private User getUser(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
