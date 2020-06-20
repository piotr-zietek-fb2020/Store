package com.fb2020.Store.exception.order;

public class OrderNotFoundException extends OrderException{
    public OrderNotFoundException(Long id) {
        super("No order in database with id: " + id);
    }
}
