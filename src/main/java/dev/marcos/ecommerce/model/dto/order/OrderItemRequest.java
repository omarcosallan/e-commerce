package dev.marcos.ecommerce.model.dto.order;

import jakarta.validation.constraints.Min;

public record OrderItemRequest(Long productId,
                               @Min(1) Integer quantity) {
}
