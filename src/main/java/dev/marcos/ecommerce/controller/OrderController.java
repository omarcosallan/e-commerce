package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.model.dto.order.OrderCreateRequest;
import dev.marcos.ecommerce.model.dto.order.OrderDTO;
import dev.marcos.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.findById(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody OrderCreateRequest dto) {
        OrderDTO order = service.save(userDetails, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.id()).toUri();
        return ResponseEntity.created(uri).body(order);
    }
}
