package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.model.dto.UserCreateRequest;
import dev.marcos.ecommerce.model.dto.UserUpdateRequest;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size,
                                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                                               @RequestParam(required = false, defaultValue = "lastModifiedDate") String sortField) {
        return ResponseEntity.ok().body(userService.findAll(page, size, direction, sortField));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest dto) {
        UserDTO user = userService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest dto) {
        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }
}