package com.orderservice.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.orderservice.dto.OrderDto;
import com.orderservice.dto.UpdateOrderDto;
import com.orderservice.entity.Order;
import com.orderservice.entity.User;
import com.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    public OrderDto addOrder(Long userId, OrderDto orderDto) {
        User user = userService.getUserById(userId);
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setUser(user);
        order.setStatus(Order.Status.IN_PROGRESS);
        return OrderDto.of(orderRepository.save(order));
    }

    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::of)
                .toList();
    }

    public OrderDto updateUserOrder(Long userId, UpdateOrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.id()).orElseThrow();
        BeanUtils.copyProperties(orderDto, order);
        return OrderDto.of(order);
    }

    public void removeUserOrder(Long userId, Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
