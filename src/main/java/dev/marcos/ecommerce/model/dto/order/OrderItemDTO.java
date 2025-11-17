package dev.marcos.ecommerce.model.dto.order;

import java.math.BigDecimal;

public record OrderItemDTO(Long productId,
                           String productName,
                           Integer quantity,
                           BigDecimal unitPrice,
                           BigDecimal subTotal) {
}
