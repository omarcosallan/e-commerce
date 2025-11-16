package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.entity.Product;
import dev.marcos.ecommerce.model.dto.product.ProductCreateRequest;
import dev.marcos.ecommerce.model.dto.product.ProductDTO;

import java.math.BigDecimal;

public class ProductMapper {

    public static Product toEntity(ProductCreateRequest p) {
        return new Product(
                null,
                p.name(),
                p.description(),
                p.price(),
                p.stockQuantity(),
                null
        );
    }

    public static ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStockQuantity(),
                p.getPrice().multiply(BigDecimal.valueOf(p.getStockQuantity())),
                p.getCategory().getName(),
                p.getCreatedBy(),
                p.getCreatedDate(),
                p.getLastModifiedBy(),
                p.getLastModifiedDate()
        );
    }
}
