package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.*;
import dev.marcos.ecommerce.entity.enums.OrderStatus;
import dev.marcos.ecommerce.entity.enums.PaymentStatus;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.mapper.OrderMapper;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.model.dto.order.OrderCreateRequest;
import dev.marcos.ecommerce.model.dto.order.OrderDTO;
import dev.marcos.ecommerce.model.dto.order.OrderItemRequest;
import dev.marcos.ecommerce.model.dto.product.ProductDTO;
import dev.marcos.ecommerce.repository.OrderRepository;
import dev.marcos.ecommerce.utils.CheckPermission;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final AddressService addressService;

    public OrderService(OrderRepository orderRepository, ProductService productService, AddressService addressService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.addressService = addressService;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

    public List<OrderDTO> findAll(UserDetails userDetails) {
        User user = UserMapper.toEntity(userDetails);
        return orderRepository.findAllByUserId(user.getId()).stream().map(OrderMapper::toDTO).toList();
    }

    public OrderDTO findById(UserDetails userDetails, Long orderId) {
        Order order = getOrder(orderId);
        CheckPermission.verify((User) userDetails, order.getUser().getId());
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO save(UserDetails userDetails, @Valid OrderCreateRequest dto) {
        User user = UserMapper.toEntity(userDetails);
        Address address = addressService.findById(dto.addressId(), userDetails);

        Order order = new Order(null, user, null, OrderStatus.PENDING, null, address);

        Set<OrderItem> items = new HashSet<>();

        for (OrderItemRequest itemDTO : dto.items()) {
            ProductDTO productDTO = productService.findById(itemDTO.productId());

            if (itemDTO.quantity() > productDTO.stockQuantity()) {
                throw new IllegalArgumentException(productDTO.name() + " não disponível em estoque");
            }

            Integer quantity = itemDTO.quantity();
            BigDecimal unitPrice = productDTO.price();
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);

            Product product = new Product(
                    productDTO.id(),
                    productDTO.name(),
                    productDTO.description(),
                    productDTO.price(),
                    productDTO.stockQuantity(),
                    null
            );
            items.add(new OrderItem(order, product, quantity, unitPrice, total));
        }

        order.setItems(items);
        BigDecimal totalAmount = order.getItems().stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        order.setTotalAmount(totalAmount);


        Payment payment = new Payment(null, order, dto.paymentMethod(), totalAmount, PaymentStatus.PENDING);
        order.setPayment(payment);

        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }

    public OrderDTO cancel(UserDetails userDetails, Long orderId) {
        Order order = getOrder(orderId);
        User user = UserMapper.toEntity(userDetails);
        CheckPermission.verify(user, order.getUser().getId());
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }
}
