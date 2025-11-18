package dev.marcos.ecommerce.model.dto.address;

import jakarta.validation.constraints.Pattern;

public record AddressUpdateRequest(String street,
                                   Integer number,
                                   String city,
                                   String state,
                                   String complement,
                                   @Pattern(regexp = "^\\d{5}-\\d{3}$",
                                           message = "Formato inválido")
                                   String postalCode) {
}
