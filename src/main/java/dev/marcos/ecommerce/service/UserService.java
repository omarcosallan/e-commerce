


package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private User getUser(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
