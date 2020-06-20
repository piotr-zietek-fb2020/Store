package com.fb2020.Store.exception.orderDetails;

public class OrderDetailsNotFoundException extends OrderDetailsException{
    public OrderDetailsNotFoundException(Long id) {
        super("No orderDetails in database with id: " + id);
    }
}
