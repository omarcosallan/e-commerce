package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.Category;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.mapper.CategoryMapper;
import dev.marcos.ecommerce.model.dto.category.CategoryRequest;
import dev.marcos.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long categoryId) {
        return getCategory(categoryId);
    }

    @Transactional
    public Category save(CategoryRequest dto) {
        return repository.save(CategoryMapper.toEntity(dto));
    }

    @Transactional
    public Category update(Long categoryId, CategoryRequest dto) {
        Category category = getCategory(categoryId);
        category.setName(dto.name());
        return repository.save(category);
    }

    @Transactional
    public void delete(Long categoryId) {
        if (!repository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        repository.deleteById(categoryId);
    }

    private Category getCategory(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }
}
