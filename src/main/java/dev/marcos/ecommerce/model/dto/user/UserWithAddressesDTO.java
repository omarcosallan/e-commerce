package dev.marcos.ecommerce.model.dto.user;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import dev.marcos.ecommerce.entity.Address;

import java.util.List;

public record UserWithAddressesDTO(@JsonUnwrapped UserDTO user,
                                   List<Address> address) {
}
