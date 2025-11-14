package dev.marcos.ecommerce.model.dto.user;

import dev.marcos.ecommerce.entity.enums.Role;

import java.time.LocalDateTime;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String username,
                      LocalDateTime createdAt,
                      Role role) {
}
