package com.fb2020.Store.exception.orderDetails;

public class OrderDetailsNotOwnedByOrderException extends OrderDetailsException{
    public OrderDetailsNotOwnedByOrderException(Long idOrder, Long idOrderDetails) {
        super("OrderDetails id: " + idOrderDetails + " doesnt belong to Order id: " + idOrder);
    }
}
