package dev.marcos.ecommerce.model.dto.order;

import dev.marcos.ecommerce.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(Long id,
                       OrderStatus status,
                       BigDecimal totalAmount,
                       Set<OrderItemDTO> items,
                       Long customerId,
                       Long paymentId,
                       Long addressId,
                       Long createdBy,
                       LocalDateTime createdDate,
                       Long lastModifiedBy,
                       LocalDateTime lastModifiedDate) {
}
