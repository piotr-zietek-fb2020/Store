package com.fb2020.Store.service;

import com.fb2020.Store.entity.Order;
import com.fb2020.Store.exception.order.OrderException;
import com.fb2020.Store.exception.order.OrderNotFoundException;
import com.fb2020.Store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order>getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) throws OrderException {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        return order;
    }

    public void deleteOrderById(Long id) throws OrderException {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

}
