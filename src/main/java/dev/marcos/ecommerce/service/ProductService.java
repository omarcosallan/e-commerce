package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.Category;
import dev.marcos.ecommerce.entity.Product;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.mapper.ProductMapper;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.model.dto.product.ProductCreateRequest;
import dev.marcos.ecommerce.model.dto.product.ProductDTO;
import dev.marcos.ecommerce.model.dto.product.ProductUpdateRequest;
import dev.marcos.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public PaginatedResponse<ProductDTO> findAll(int currentPage, int size, Sort.Direction direction, String sortField) {
        Pageable pageable = PageRequest.of(currentPage, size, direction, sortField);
        Page<Product> page = repository.findAll(pageable);
        List<ProductDTO> data = page.getContent().stream().map(ProductMapper::toDTO).toList();
        return new PaginatedResponse<>(
                data,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                size,
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public ProductDTO findById(Long productId) {
        return ProductMapper.toDTO(getProduct(productId));
    }

    @Transactional
    public ProductDTO save(ProductCreateRequest dto) {
        Product product = ProductMapper.toEntity(dto);
        Category category = getCategory(dto.categoryId());
        product.setCategory(category);
        repository.save(product);
        return ProductMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO update(Long productId, ProductUpdateRequest dto) {
        Product product = getProduct(productId);
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        if (dto.description() != null) {
            product.setDescription(dto.description());
        }
        Category category = getCategory(dto.categoryId());
        product.setCategory(category);
        repository.save(product);
        return ProductMapper.toDTO(product);
    }

    @Transactional
    public void delete(Long productId) {
        if (!repository.existsById(productId)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        repository.deleteById(productId);
    }

    private Product getProduct(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    private Category getCategory(Long productId) {
        return categoryService.findById(productId);
    }
}
