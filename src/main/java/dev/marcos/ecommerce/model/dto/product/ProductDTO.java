package dev.marcos.ecommerce.model.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(Long id,
                         String name,
                         String description,
                         BigDecimal price,
                         Integer stockQuantity,
                         BigDecimal stockAmount,
                         String categoryName,
                         Long createdBy,
                         LocalDateTime createdDate,
                         Long lastModifiedBy,
                         LocalDateTime lastModifiedDate) {
}
