package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User u) {
        return new UserDTO(u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getUsername(),
                u.getRole(),
                u.getCreatedBy(),
                u.getCreatedDate(),
                u.getLastModifiedBy(),
                u.getLastModifiedDate());
    }

    public static User toEntity(UserDTO u) {
        return new User(
                u.id(),
                u.username(),
                u.email(),
                null,
                u.firstName(),
                u.lastName(),
                null,
                u.role()
        );
    }
}
