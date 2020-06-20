package com.fb2020.Store.exception.order;

public class OrderNotOwnedByClientException extends OrderException{
    public OrderNotOwnedByClientException(Long idClient, Long idOrder) {
        super("Order id: " + idOrder + " doesnt belong to Client id: " + idClient);
    }
}
