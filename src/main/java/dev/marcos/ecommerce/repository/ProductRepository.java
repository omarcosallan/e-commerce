package dev.marcos.ecommerce.repository;

import dev.marcos.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCreatedDateBetween(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
