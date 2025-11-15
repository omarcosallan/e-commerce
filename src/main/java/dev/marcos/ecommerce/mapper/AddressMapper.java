package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.entity.Address;
import dev.marcos.ecommerce.model.dto.address.AddressCreateRequest;

public class AddressMapper {

    public static Address toEntity(AddressCreateRequest u) {
        return new Address(
                null,
                u.street(),
                u.number(),
                u.city(),
                u.state(),
                u.complement(),
                u.postalCode()
        );
    }
}
