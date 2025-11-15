package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size,
                                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                                               @RequestParam(required = false, defaultValue = "lastModifiedDate") String sortField) {
        return ResponseEntity.ok().body(service.findAll(page, size, direction, sortField));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}