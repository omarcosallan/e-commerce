package dev.marcos.ecommerce.repository;

import dev.marcos.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        SELECT o FROM Order o WHERE o.user.id = :userId AND o.status <> 'CANCELED'
    """)
    List<Order> findAllByUserId(Long userId);
}
