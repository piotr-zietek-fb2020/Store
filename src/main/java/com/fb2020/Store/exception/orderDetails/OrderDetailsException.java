package com.fb2020.Store.exception.orderDetails;

import com.fb2020.Store.entity.OrderDetails;
import com.fb2020.Store.exception.order.OrderException;

public class OrderDetailsException extends Exception{
    public OrderDetailsException(String message) {
        super(message);
    }
}
