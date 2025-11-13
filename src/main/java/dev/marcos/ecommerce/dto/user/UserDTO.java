package dev.marcos.ecommerce.dto.user;

import java.time.LocalDateTime;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String username,
                      LocalDateTime createdAt) {
}
