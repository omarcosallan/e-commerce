package dev.marcos.ecommerce.utils;

import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.entity.enums.Role;
import org.springframework.security.authorization.AuthorizationDeniedException;

public class CheckPermission {

    public static void verify(User user, Long userId) {
        if (user.getRole() != Role.ADMIN && !user.getId().equals(userId)) {
            throw new AuthorizationDeniedException("Não é possível acessar este recurso");
        }
    }
}
