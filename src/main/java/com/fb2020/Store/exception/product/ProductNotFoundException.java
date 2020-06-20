package com.fb2020.Store.exception.product;

public class ProductNotFoundException extends ProductException{
    public ProductNotFoundException(Long id) {
        super("No product in database with id: " + id);
    }
}
