package com.fb2020.Store.exception.address;

public class AddressNotFoundException extends AddressException {
    public AddressNotFoundException(Long id) {
        super("No address in database with id: " + id);
    }
}
