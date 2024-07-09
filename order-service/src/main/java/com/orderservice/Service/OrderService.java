package com.orderservice.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderservice.dto.OrderDto;
import com.orderservice.dto.UpdateOrderDto;
import com.orderservice.entity.Order;
import com.orderservice.entity.User;
import com.orderservice.exception.OrderNotFoundException;
import com.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    @Transactional
    public OrderDto addOrder(Long userId, OrderDto orderDto) {
        log.info("Add order: {}", orderDto);
        User user = userService.getUserById(userId);
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setUser(user);
        order.setStatus(Order.Status.IN_PROGRESS);
        return OrderDto.of(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDto getUserOrder(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .map(OrderDto::of)
                .orElseThrow(() -> new OrderNotFoundException(orderId, userId));
    }

    @Transactional
    public OrderDto updateUserOrder(Long userId, UpdateOrderDto orderDto) {
        log.info("Update order: {}", orderDto);
        Order order = orderRepository.findByIdAndUserId(orderDto.id(), userId).orElseThrow();
        BeanUtils.copyProperties(orderDto, order);
        return OrderDto.of(order);
    }

    @Transactional
    public void removeUserOrder(Long userId, Long orderId) {
        log.info("Remove order: {}", orderId);
        orderRepository.deleteByIdAndUserId(orderId, userId);
        log.info("Removed order: {}", orderId);
    }
}
