package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.model.dto.product.ProductCreateRequest;
import dev.marcos.ecommerce.model.dto.product.ProductDTO;
import dev.marcos.ecommerce.model.dto.product.ProductUpdateRequest;
import dev.marcos.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ProductDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok().body(service.findAll(page, size, startDate, endDate));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long productId) {
        return ResponseEntity.ok().body(service.findById(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductCreateRequest dto) {
        ProductDTO product = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.id()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> update(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequest dto) {
        return ResponseEntity.ok().body(service.update(productId, dto));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        service.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
