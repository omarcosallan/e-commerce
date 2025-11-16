package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.entity.Category;
import dev.marcos.ecommerce.model.dto.category.CategoryRequest;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest c) {
        return new Category(
                null,
                c.name()
        );
    }
}
