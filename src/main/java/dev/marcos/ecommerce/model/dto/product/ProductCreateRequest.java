package dev.marcos.ecommerce.model.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(@NotBlank(message = "Nome do produto é obrigatório")
                             @Size(max = 100)
                             String name,
                             String description,
                             @NotNull
                             @DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que zero")
                             BigDecimal price,
                             @Min(value = 1, message = "Quantidade deve ser maior que zero")
                             Integer stockQuantity,
                             @NotNull Long categoryId) {
}
