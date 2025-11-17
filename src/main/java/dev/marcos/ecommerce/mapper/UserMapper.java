package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

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

    public static User toEntity(UserDetails userDetails) {
        return (User) userDetails;
    }
}
