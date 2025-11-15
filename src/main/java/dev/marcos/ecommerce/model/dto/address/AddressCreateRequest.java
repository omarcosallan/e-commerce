package dev.marcos.ecommerce.model.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressCreateRequest(@NotBlank String street,
                                   @NotNull Integer number,
                                   @NotBlank String city,
                                   @NotBlank String state,
                                   String complement,
                                   @NotBlank
                                   @Pattern(regexp = "^\\d{5}-\\d{3}$",
                                           message = "Formato inválido")
                                   String postalCode) {
}
