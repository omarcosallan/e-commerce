package dev.marcos.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotNull(message = "E-mail é obrigatório")
                           @Size(min = 1, message = "E-mail é obrigatório")
                           @Email(message = "E-mail deve ter um formato válido")
                           String email,
                           @NotNull(message = "Senha é obrigatória")
                           @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
                                   message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
                           String password) {
}
