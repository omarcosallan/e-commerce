package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.entity.Category;
import dev.marcos.ecommerce.model.dto.category.CategoryRequest;
import dev.marcos.ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getById(@PathVariable long categoryId) {
        return ResponseEntity.ok(service.findById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryRequest dto) {
        Category category = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> update(@PathVariable Long categoryId, @RequestBody CategoryRequest dto) {
        return ResponseEntity.ok(service.update(categoryId, dto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
        service.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
