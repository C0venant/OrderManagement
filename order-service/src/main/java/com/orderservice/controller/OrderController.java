package com.orderservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.Service.OrderService;
import com.orderservice.dto.OrderDto;
import com.orderservice.dto.UpdateOrderDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@NotNull @RequestHeader("user-id") Long userId, @Valid @RequestBody OrderDto order) {
        return ResponseEntity.ok(orderService.addOrder(userId, order));
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@NotNull @RequestHeader("user-id") Long userId, @Valid @RequestBody UpdateOrderDto order) {
        return ResponseEntity.ok(orderService.updateUserOrder(userId, order));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(@NotNull @RequestHeader("user-id") Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@NotNull @RequestHeader("user-id") Long userId, @NotNull @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getUserOrder(userId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@NotNull @RequestHeader("user-id") Long userId, @NotNull @PathVariable Long id) {
        orderService.removeUserOrder(userId, id);
        return ResponseEntity.noContent().build();
    }
}
