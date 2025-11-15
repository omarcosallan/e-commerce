package dev.marcos.ecommerce.model.dto;

import dev.marcos.ecommerce.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(String username,
                                @Email String email,
                                @NotNull(message = "Senha é obrigatória")
                                @Size(max = 50) String firstName,
                                @Size(max = 50) String lastName,
                                Role role) {
}
