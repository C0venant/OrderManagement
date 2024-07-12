package com.orderservice.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.orderservice.dto.OrderDto;
import com.orderservice.entity.Order;
import com.orderservice.entity.User;
import com.orderservice.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        OrderDto orderDto = new OrderDto(null, null, "prod", 1, null);
        Order order = new Order();
        order.setUser(new User());
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        OrderDto result = orderService.addOrder(1L, orderDto);
        assertNotNull(result);
    }


}