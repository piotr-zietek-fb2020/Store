package com.fb2020.Store.service;

import com.fb2020.Store.entity.OrderDetails;
import com.fb2020.Store.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public void deleteOrderDetails(OrderDetails orderDetails) {
        orderDetailsRepository.delete(orderDetails);
    }

    public List<OrderDetails> getAllByOrderId(Long orderId) {
        return orderDetailsRepository.findAllByOrderId(orderId);
    }
}
