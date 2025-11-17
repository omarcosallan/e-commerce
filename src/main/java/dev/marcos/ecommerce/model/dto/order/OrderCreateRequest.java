package dev.marcos.ecommerce.model.dto.order;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record OrderCreateRequest(Long addressId,
                                 @NotBlank String paymentMethod,
                                 Set<OrderItemRequest> items) {
}
