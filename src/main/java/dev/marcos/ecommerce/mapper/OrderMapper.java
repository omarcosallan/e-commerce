package dev.marcos.ecommerce.mapper;

import dev.marcos.ecommerce.entity.Order;
import dev.marcos.ecommerce.entity.OrderItem;
import dev.marcos.ecommerce.model.dto.order.OrderDTO;
import dev.marcos.ecommerce.model.dto.order.OrderItemDTO;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order o) {
        return new OrderDTO(
                o.getId(),
                o.getStatus(),
                o.getTotalAmount(),
                o.getItems().stream().map(OrderMapper::toDTO).collect(Collectors.toSet()),
                o.getUser().getId(),
                o.getPayment().getId(),
                o.getAddress().getId(),
                o.getCreatedBy(),
                o.getCreatedDate(),
                o.getLastModifiedBy(),
                o.getLastModifiedDate()
        );
    }

    public static OrderItemDTO toDTO(OrderItem o) {
        return new OrderItemDTO(
                o.getProduct().getId(),
                o.getProduct().getName(),
                o.getQuantity(),
                o.getPrice(),
                o.getSubTotal()
        );
    }
}
