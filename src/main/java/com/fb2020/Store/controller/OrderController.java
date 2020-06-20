package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Order;
import com.fb2020.Store.exception.order.OrderException;
import com.fb2020.Store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/orders/{id}")
    public Optional<Order> getOrderById(@PathVariable("id") Long id) {
        try {
            return orderService.getOrderById(id);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrderById(@PathVariable("id") Long id) {
        try {
            orderService.deleteOrderById(id);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
